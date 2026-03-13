package com.solvd.webAutomation;

import com.solvd.webAutomation.config.ConfigReader;
import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

public class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Parameters("browser")
    @BeforeMethod
    public void setUp(Method method, @Optional("CHROME") String browser) {
        DriverRunMode runMode = DriverRunMode.valueOf(ConfigReader.get("run_mode"));
        DriverType driverType = DriverType.valueOf(browser);
        DriverFactory.createDriver(runMode, driverType);
        WebDriver driver = DriverFactory.getDriver();
        //DriverRunMode LOCAL or REMOTE. REMOTE Requires Selenium server standalone.
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("url"));

        String sessionId = "N/A";
        if (driver instanceof RemoteWebDriver remoteDriver) {
            SessionId session = remoteDriver.getSessionId();
            sessionId = (session != null) ? session.toString() : "null";
        }

        logger.info("Starting Test: {} | Thread: {} | Driver hash: {} | Session ID: {}",
                method.getName(),
                Thread.currentThread().getName(),
                driver.hashCode(),
                sessionId
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {

            ScreenshotUtils.takeScreenshot(
                    DriverFactory.getDriver(),
                    result.getName()
            );
        }

        try {
            DriverFactory.quitDriver();
        } catch (Exception e) {
            logger.warn("Driver already closed: " + e.getMessage());
        }
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

}
