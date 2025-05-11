package com.automation.core.drivers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static final ThreadLocal<AppiumDriver> mobileDriver = new ThreadLocal<>();

    private DriverManager() {}

    // WebDriver methods
    public static WebDriver getWebDriver() {
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static void unloadWebDriver() {
        webDriver.remove();
    }

    // AppiumDriver methods
    public static AppiumDriver getMobileDriver() {
        return mobileDriver.get();
    }

    public static void setMobileDriver(AppiumDriver driver) {
        mobileDriver.set(driver);
    }

    public static void unloadMobileDriver() {
        mobileDriver.remove();
    }
}