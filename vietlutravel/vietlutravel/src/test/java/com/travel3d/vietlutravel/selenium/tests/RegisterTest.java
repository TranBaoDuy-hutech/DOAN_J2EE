package com.travel3d.vietlutravel.selenium.tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.travel3d.vietlutravel.selenium.base.BaseSeleniumTest;
import com.travel3d.vietlutravel.selenium.data.TestDataFactory;
import com.travel3d.vietlutravel.selenium.pages.RegisterPage;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class RegisterTest extends BaseSeleniumTest {

    private RegisterPage registerPage;
    private static final String VALID_PASS = "Abcd@1234";

    @BeforeAll
    static void setupTestData() {
        System.out.println("[RegisterTest] Đang tạo dữ liệu test...");
        TestDataFactory.setupRegisterTestData(); // tạo duplicate@vietlu.com cho TC05
        System.out.println("[RegisterTest] ✅ Dữ liệu test sẵn sàng!");
    }

    @BeforeEach
    void initPage() {
        registerPage = new RegisterPage(driver, wait);
        registerPage.open();
    }

    // ✅ TC01 — Đăng ký thành công → redirect về /login
    @Test
    @DisplayName("TC01 - Đăng ký thành công → redirect về /login")
    void TC01_registerSuccess_shouldRedirectToLogin() {
        long ts = System.currentTimeMillis();

        registerPage.fillForm(
            "Nguyen Van A",
            "tc01_" + ts + "@vietlu.com",     // unique mỗi lần chạy
            "091" + (ts % 9000000 + 1000000),  // số điện thoại unique
            VALID_PASS,
            VALID_PASS
        );
        registerPage.acceptTerms();

        // ⚠️ Dùng submitViaServer() để bypass JS preventDefault
        registerPage.submitViaServer();

        // Sau đăng ký thành công → redirect về /login
        wait.until(ExpectedConditions.urlContains("/login"));

        assertThat(registerPage.getCurrentUrl())
            .as("TC01: Sau đăng ký phải redirect về /login")
            .contains("/login");
    }

    // ❌ TC02 — Mật khẩu xác nhận không khớp → JS báo lỗi ngay trên form
    @Test
    @DisplayName("TC02 - Mật khẩu xác nhận không khớp → hiện lỗi")
    void TC02_mismatchedPasswords_shouldShowError() {
        registerPage.fillForm(
            "Nguyen Van B",
            "tc02_" + System.currentTimeMillis() + "@vietlu.com",
            "0911000002",
            VALID_PASS,
            "SaiMatKhau@999"  // ← khác password
        );
        registerPage.acceptTerms();
        registerPage.submit(); // JS bắt lỗi trước khi POST → dùng submit() thường

        assertThat(registerPage.isErrorDisplayed())
            .as("TC02: Phải hiện thông báo lỗi mật khẩu không khớp")
            .isTrue();
        assertThat(registerPage.getErrorMessage())
            .as("TC02: Nội dung lỗi phải đề cập 'không khớp'")
            .containsIgnoringCase("không khớp");
    }

    // ❌ TC03 — Để trống tên người dùng → HTML required chặn
    @Test
    @DisplayName("TC03 - Tên người dùng trống → không submit được")
    void TC03_emptyName_shouldNotSubmit() {
        registerPage.fillForm(
            "",  // ← tên trống
            "tc03_" + System.currentTimeMillis() + "@vietlu.com",
            "0911000003",
            VALID_PASS,
            VALID_PASS
        );
        registerPage.acceptTerms();
        registerPage.submit();

        assertThat(registerPage.getCurrentUrl())
            .as("TC03: Phải ở lại /register do tên trống")
            .contains("/register");
    }

    // ❌ TC04 — Email sai định dạng → HTML type="email" chặn
    @Test
    @DisplayName("TC04 - Email sai định dạng → không submit được")
    void TC04_invalidEmail_shouldNotSubmit() {
        registerPage.fillForm(
            "Nguyen Van D",
            "day-khong-phai-email",  // ← sai định dạng
            "0911000004",
            VALID_PASS,
            VALID_PASS
        );
        registerPage.acceptTerms();
        registerPage.submit();

        assertThat(registerPage.getCurrentUrl())
            .as("TC04: Phải ở lại /register do email sai định dạng")
            .contains("/register");
    }

    // ❌ TC05 — Email đã tồn tại → server trả lỗi
    @Test
    @DisplayName("TC05 - Email đã tồn tại → hiện lỗi trùng lặp")
    void TC05_duplicateEmail_shouldStayOnRegister() {
        registerPage.fillForm(
            "New User",
            TestDataFactory.DUPLICATE_EMAIL,  // ← đã tạo trong @BeforeAll
            "0911000005",
            VALID_PASS,
            VALID_PASS
        );
        registerPage.acceptTerms();
        registerPage.submitViaServer(); // POST lên server để nhận lỗi trùng email

        assertThat(registerPage.getCurrentUrl())
            .as("TC05: Phải ở lại /register do email trùng")
            .contains("/register");
    }
}