package com.automation.tests;

import com.automation.core.config.ConfigManager;

public class SampleTest {
    public static void main(String[] args) {
        ConfigManager configManager = ConfigManager.getInstance();
        String browserName = configManager.get("browser");
        System.err.println("Browser Name: " + browserName);
    }

}
