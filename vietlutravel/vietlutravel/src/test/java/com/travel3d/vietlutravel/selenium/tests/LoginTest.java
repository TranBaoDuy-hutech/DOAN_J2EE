package com.travel3d.vietlutravel.selenium.tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.travel3d.vietlutravel.selenium.base.BaseSeleniumTest;
import com.travel3d.vietlutravel.selenium.data.TestDataFactory;
import com.travel3d.vietlutravel.selenium.pages.LoginPage;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class LoginTest extends BaseSeleniumTest {

    private static final String BASE_URL = "http://localhost:8080";

    private LoginPage loginPage;

    @BeforeAll
    static void setupTestData() {
        System.out.println("[LoginTest] Đang tạo dữ liệu test...");
        TestDataFactory.setupLoginTestData();
        System.out.println("[LoginTest] ✅ Dữ liệu test sẵn sàng: " 
                           + TestDataFactory.EXISTING_EMAIL);
    }

    @BeforeEach
    void initPage() {
        loginPage = new LoginPage(driver, wait);
        loginPage.open();
    }

    @Test
    @DisplayName("TC06 - Đăng nhập đúng thông tin → vào được hệ thống")
    void TC06_loginSuccess_shouldLeaveLoginPage() {
        loginPage.login(
            TestDataFactory.EXISTING_EMAIL,
            TestDataFactory.EXISTING_PASSWORD
        );

        wait.until(ExpectedConditions.not(
            ExpectedConditions.urlContains("/login")
        ));

        assertThat(loginPage.getCurrentUrl())
            .as("TC06: Phải rời khỏi /login sau khi đăng nhập thành công")
            .doesNotContain("/login");
    }

    @Test
    @DisplayName("TC07 - Đúng email, sai mật khẩu → hiện lỗi")
    void TC07_wrongPassword_shouldShowError() {
        loginPage.login(
            TestDataFactory.EXISTING_EMAIL,
            "SaiMatKhau@000"
        );

        // Chờ trang login (có thể không có ?error)
        wait.until(ExpectedConditions.urlContains("/login"));

        boolean errorDisplayed = loginPage.isErrorDisplayed();

        assertThat(errorDisplayed)
            .as("TC07: Phải hiển thị thông báo lỗi khi sai mật khẩu")
            .isTrue();
    }

    @Test
    @DisplayName("TC08 - Email không tồn tại → hiện lỗi")
    void TC08_unknownEmail_shouldShowError() {
        String randomEmail = "khongtontai_" + System.currentTimeMillis() + "@vietlu.com";

        loginPage.login(randomEmail, "Abcd@1234");

        wait.until(ExpectedConditions.urlContains("/login"));

        boolean errorDisplayed = loginPage.isErrorDisplayed();

        assertThat(errorDisplayed)
            .as("TC08: Phải hiển thị thông báo lỗi khi email không tồn tại")
            .isTrue();
    }

    @Test
    @DisplayName("TC09 - Để trống email và mật khẩu → không submit được")
    void TC09_emptyCredentials_shouldNotSubmit() {
        loginPage.login("", "");

        assertThat(loginPage.getCurrentUrl())
            .as("TC09: Phải ở lại /login khi để trống thông tin")
            .contains("/login");
    }

    @Test
    @DisplayName("TC10 - Đăng xuất → truy cập trang bảo vệ → redirect /login")
    void TC10_afterLogout_shouldRedirectToLogin() {
        // Đăng nhập trước
        loginPage.login(
            TestDataFactory.EXISTING_EMAIL,
            TestDataFactory.EXISTING_PASSWORD
        );
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

        // Logout
        try {
            driver.findElement(By.cssSelector("form[action*='logout'], form[action='/logout']"))
                  .submit();
        } catch (Exception e1) {
            try {
                driver.findElement(By.cssSelector("form[action*='logout'] button[type='submit']")).click();
            } catch (Exception e2) {
                System.out.println("[TC10] Không tìm thấy form logout, dùng GET fallback");
                driver.get(BASE_URL + "/logout");
            }
        }

        // Chờ logout xong
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("logout")));

        // Thử vào trang bảo vệ
        driver.get(BASE_URL + "/profile");
        wait.until(ExpectedConditions.urlContains("/login"));

        assertThat(loginPage.getCurrentUrl())
            .as("TC10: Sau logout phải bị redirect về /login")
            .contains("/login");
    }
}