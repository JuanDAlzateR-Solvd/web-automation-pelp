package com.solvd.webAutomation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;

public class DriverFactory {

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
                return new ChromeDriver();

            default:
                throw new IllegalArgumentException("Unsupported driver type: " + driverType);
        }
    }

    private static WebDriver createRemoteDriver(DriverType driverType) {
        try {
            switch (driverType) {
                case CHROME:
                    ChromeOptions options = new ChromeOptions();
                    return new RemoteWebDriver(
                            new URL("http://localhost:4444"),
                            options
                    );

                default:
                    throw new IllegalArgumentException("Unsupported driver type: " + driverType);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create remote driver", e);
        }
    }
}
