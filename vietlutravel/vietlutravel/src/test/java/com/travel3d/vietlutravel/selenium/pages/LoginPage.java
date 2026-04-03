package com.travel3d.vietlutravel.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField    = By.id("email");
    private final By passwordField = By.id("password");
    private final By submitButton  = By.cssSelector("form button[type='submit']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    public void open() {
        driver.get("http://localhost:8080/login");
    }

    public void login(String email, String password) {
        WebElement emailEl = driver.findElement(emailField);
        emailEl.clear();
        emailEl.sendKeys(email);

        WebElement passEl = driver.findElement(passwordField);
        passEl.clear();
        passEl.sendKeys(password);

        driver.findElement(submitButton).click();
    }

    /**
     * Cách fix mạnh nhất hiện tại: 
     * - Chờ một lúc sau khi submit
     * - Kiểm tra URL vẫn ở /login 
     * - Tìm element lỗi bằng nhiều selector phổ biến
     */
    public boolean isErrorDisplayed() {
        try {
            // Chờ sau khi submit form (để trang load lại hoặc render lỗi)
            Thread.sleep(1500); // chờ nhẹ để Thymeleaf render lỗi

            wait.withTimeout(Duration.ofSeconds(8))
                .until(ExpectedConditions.urlContains("/login"));

            String currentUrl = driver.getCurrentUrl();
            System.out.println("[LoginPage] Current URL after login attempt: " + currentUrl);

            // Thử tìm element chứa lỗi
            String[] selectors = {
                ".alert-danger", ".text-danger", "#error", ".error-message", 
                ".invalid-feedback", "div.error", "p.error", "[class*='alert']",
                "[class*='danger']", "div[th\\:if*='error']"
            };

            for (String selector : selectors) {
                try {
                    WebElement element = driver.findElement(By.cssSelector(selector));
                    String text = element.getText().trim();
                    if (element.isDisplayed() && !text.isEmpty()) {
                        System.out.println("[LoginPage] ✓ TÌM THẤY LỖI với selector: " + selector);
                        System.out.println("[LoginPage] Nội dung lỗi: " + text);
                        return true;
                    }
                } catch (Exception ignored) {}
            }

            System.out.println("[LoginPage] Không tìm thấy element lỗi nào");
            return false;

        } catch (Exception e) {
            System.out.println("[LoginPage] Exception: " + e.getMessage());
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}