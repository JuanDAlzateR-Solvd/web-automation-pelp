package com.solvd.webAutomation.driver;

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

    public static WebDriver createDriver(DriverRunMode runMode, DriverType driverType) {

        switch (runMode) {
            case LOCAL:
                return createLocalDriver(driverType);

            case REMOTE:
                return createRemoteDriver(driverType);

            default:
                throw new IllegalArgumentException("Unsupported run mode: " + runMode);
        }
    }

    private static WebDriver createLocalDriver(DriverType driverType) {
        switch (driverType) {
            case CHROME:
                WebDriver driver = new ChromeDriver();
                threadDriver.set(driver);
                return driver;

            default:
                throw new IllegalArgumentException("Unsupported driver type: " + driverType);
        }
    }

    private static WebDriver createRemoteDriver(DriverType driverType) {
        try {
            URL url = new URL("http://localhost:4444");
            switch (driverType) {
                case CHROME:
                    ChromeOptions options = new ChromeOptions();
                    RemoteWebDriver driver = new RemoteWebDriver(url, options);
                    threadDriver.set(driver);
                    return driver;

                case FIREFOX:
                    FirefoxOptions options2 = new FirefoxOptions();
                    RemoteWebDriver driver2 = new RemoteWebDriver(url, options2);
                    threadDriver.set(driver2);
                    return driver2;

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
