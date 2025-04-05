package com.automation.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

    public static void initDriver(DriverType type) {
        WebDriver driver;

        switch (type) {
            case CHROME -> {
                driver = new ChromeDriver();
            }
            case FIREFOX -> {
                driver = new FirefoxDriver();
            }
            case SAFARI -> {
                driver = new SafariDriver();
            }
            case EDGE -> {
                driver = new EdgeDriver();
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + type);
        }

        driver.manage().window().maximize();
        DriverManager.setDriver(driver);
    }
}
