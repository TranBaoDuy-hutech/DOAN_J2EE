package com.travel3d.vietlutravel.selenium.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseSeleniumTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final String BASE_URL =
            System.getProperty("base.url", "http://localhost:8080");

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

    //    options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (driver != null) {
            try {
                takeScreenshot(testInfo.getDisplayName());
            } catch (Exception e) {
                System.out.println("Screenshot error: " + e.getMessage());
            }
            driver.quit();
        }
    }

    private void takeScreenshot(String testName) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("target/screenshots/" + testName + ".png");
        dest.getParentFile().mkdirs();
        Files.copy(src.toPath(), dest.toPath());
    }
}