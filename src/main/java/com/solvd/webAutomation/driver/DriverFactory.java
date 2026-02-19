package com.solvd.webAutomation.driver;

import com.solvd.webAutomation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    private DriverFactory() {
        // prevent instantiation
    }

    public static void createDriver(DriverRunMode runMode, DriverType driverType) {
        WebDriver driver;

        switch (runMode) {
            case LOCAL -> driver = createLocalDriver(driverType);
            case REMOTE -> driver = createRemoteDriver(driverType);
            default -> throw new IllegalArgumentException("Unsupported run mode");
        }

        threadDriver.set(driver);
    }

    private static WebDriver createLocalDriver(DriverType driverType) {
        switch (driverType) {
            case CHROME:
                return new ChromeDriver();

            default:
                throw new IllegalArgumentException("Unsupported driver type: " + driverType);
        }
    }

    private static WebDriver createRemoteDriver(DriverType driverType) {
        try {
            String gridURL = ConfigReader.get("selenium_url");
            URL url = new URL(gridURL);
            switch (driverType) {
                case CHROME:
                    ChromeOptions options = new ChromeOptions();
                    return new RemoteWebDriver(url, options);

                case FIREFOX:
                    FirefoxOptions options2 = new FirefoxOptions();
                    return new RemoteWebDriver(url, options2);

                default:
                    throw new IllegalArgumentException("Unsupported driver type: " + driverType);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create remote driver", e);
        }
    }

    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    public static void quitDriver() {
        if (threadDriver.get() != null) {
            threadDriver.get().quit();
            threadDriver.remove();
        }
    }
}
