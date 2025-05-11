package com.automation.core.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class WebDriverManager {

    public static WebDriver createChromeDriver() {
        return new ChromeDriver();
    }

    public static WebDriver createFirefoxDriver() {
        return new FirefoxDriver();
    }

    public static WebDriver createEdgeDriver() {
        return new EdgeDriver();
    }
}