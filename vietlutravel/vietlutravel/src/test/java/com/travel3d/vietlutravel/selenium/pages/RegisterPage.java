package com.travel3d.vietlutravel.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField    = By.id("userName");
    private final By emailField       = By.id("email");
    private final By phoneField       = By.id("phone");
    private final By passwordField    = By.id("password");
    private final By confirmPassField = By.id("confirmPassword");
    private final By termsCheckbox    = By.id("terms");
    private final By submitButton     = By.cssSelector("#registerForm button[type='submit']");
    private final By alertError       = By.id("alertError");
    private final By alertErrorText   = By.id("alertErrorText");

    public RegisterPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    public void open() {
        driver.get("http://localhost:8080/register");
    }

    public void fillForm(String username, String email, String phone,
                         String password, String confirmPassword) {
        clearAndType(usernameField,    username);
        clearAndType(emailField,       email);
        clearAndType(phoneField,       phone);
        clearAndType(passwordField,    password);
        clearAndType(confirmPassField, confirmPassword);
    }

    public void acceptTerms() {
        WebElement cb = driver.findElement(termsCheckbox);
        if (!cb.isSelected()) cb.click();
    }

    /**
     * Submit form.
     * Form dùng th:action (Thymeleaf) → POST lên server thật.
     * Nếu form chỉ có JS preventDefault → dùng submitViaThymeleaf().
     */
    public void submit() {
        driver.findElement(submitButton).click();
    }

    /**
     * Dùng khi form đăng ký đã được kết nối Thymeleaf th:action.
     * Bỏ qua JS preventDefault, submit thẳng lên server.
     */
    public void submitViaServer() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "document.getElementById('registerForm').submit();"
        );
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(alertError));
        return driver.findElement(alertErrorText).getText().trim();
    }

    public boolean isErrorDisplayed() {
        try {
            WebElement el = driver.findElement(alertError);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    private void clearAndType(By locator, String text) {
        WebElement el = driver.findElement(locator);
        el.clear();
        el.sendKeys(text);
    }
}