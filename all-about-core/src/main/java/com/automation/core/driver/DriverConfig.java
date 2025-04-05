package com.automation.core.driver;

public class DriverConfig {
    public static DriverType getBrowser() {
        String browser = System.getProperty("browser", "CHROME");
        return DriverType.valueOf(browser.toUpperCase());
    }
}
