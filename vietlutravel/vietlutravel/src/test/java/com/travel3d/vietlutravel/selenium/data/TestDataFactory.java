package com.travel3d.vietlutravel.selenium.data;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Tạo dữ liệu test bằng cách điền form đăng ký qua Selenium.
 * Không cần REST API, không cần DB trực tiếp.
 */
public class TestDataFactory {

    private static final String BASE_URL = "http://localhost:8080";

    // ── Tài khoản cố định dùng cho test đăng nhập (TC06–TC10) ──
    public static final String EXISTING_NAME     = "User Login Test";
    public static final String EXISTING_EMAIL    = "testuser_login@vietlu.com";
    public static final String EXISTING_PHONE    = "0900000001";
    public static final String EXISTING_PASSWORD = "Abcd@1234";

    // ── Tài khoản cố định dùng cho TC05 (duplicate email) ──
    public static final String DUPLICATE_NAME    = "Duplicate User";
    public static final String DUPLICATE_EMAIL   = "duplicate@vietlu.com";
    public static final String DUPLICATE_PHONE   = "0900000002";
    public static final String DUPLICATE_PASSWORD = "Abcd@1234";

    /**
     * Tạo 1 user bằng cách điền form đăng ký.
     * Dùng driver riêng, tự đóng sau khi xong.
     */
    public static void createUserViaForm(String name, String email,
                                         String phone, String password) {
        WebDriver driver = null;
        try {
            // Khởi động Chrome headless riêng chỉ để setup data
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--no-sandbox",
                                 "--disable-dev-shm-usage", "--window-size=1920,1080");
            driver = new ChromeDriver(options);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Mở trang đăng ký
            driver.get(BASE_URL + "/register");

            // Điền form
            driver.findElement(By.id("userName")).sendKeys(name);
            driver.findElement(By.id("email")).sendKeys(email);
            driver.findElement(By.id("phone")).sendKeys(phone);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("confirmPassword")).sendKeys(password);

            // Tích checkbox điều khoản
            driver.findElement(By.id("terms")).click();

            // Submit
            driver.findElement(
                By.cssSelector("#registerForm button[type='submit']")
            ).click();

            // Chờ rời khỏi trang /register = tạo thành công
            wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/register")
            ));

            System.out.println("[TestData] ✅ Tạo user thành công: " + email);

        } catch (Exception e) {
            // User có thể đã tồn tại từ lần chạy trước → bỏ qua
            System.out.println("[TestData] ⚠️ Bỏ qua (có thể đã tồn tại): "
                               + email + " — " + e.getMessage());
        } finally {
            if (driver != null) driver.quit();
        }
    }

    /** Tạo toàn bộ dữ liệu cần thiết cho RegisterTest */
    public static void setupRegisterTestData() {
        // TC05: cần 1 email đã tồn tại sẵn
        createUserViaForm(DUPLICATE_NAME, DUPLICATE_EMAIL,
                          DUPLICATE_PHONE, DUPLICATE_PASSWORD);
    }

    /** Tạo toàn bộ dữ liệu cần thiết cho LoginTest */
    public static void setupLoginTestData() {
        // TC06–TC10: cần 1 tài khoản hợp lệ để đăng nhập
        createUserViaForm(EXISTING_NAME, EXISTING_EMAIL,
                          EXISTING_PHONE, EXISTING_PASSWORD);
    }
}