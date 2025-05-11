package com.automation.core.drivers;

import com.automation.core.enums.BrowserType;
import com.automation.core.enums.PlatformType;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

public class DriverFactory {

    public static Object createDriver(PlatformType platform, BrowserType browser) {
        return switch (platform) {
            case WEB -> createWebDriver(browser);
            case MOBILE -> createMobileDriver();
        };
    }

    private static WebDriver createWebDriver(BrowserType browser) {
        return switch (browser) {
            case CHROME -> WebDriverManager.createChromeDriver();
            case FIREFOX -> WebDriverManager.createFirefoxDriver();
            case EDGE -> WebDriverManager.createEdgeDriver();
        };
    }

    private static AppiumDriver createMobileDriver() {
        // Will be implemented later
        return null;
    }

}