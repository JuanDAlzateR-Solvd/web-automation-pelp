package com.solvd.webAutomation.utils;

import com.solvd.webAutomation.driver.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String testName) {

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String browser= ((RemoteWebDriver) driver).getCapabilities().getBrowserName();

        String threadID = ((RemoteWebDriver) driver).getSessionId().toString();

        String screenshotName = testName + "_" + timestamp + "_" + browser + "_" + threadID;

        File src = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        File dest = new File(
                "screenshots/" + screenshotName+ ".png"
        );

        dest.getParentFile().mkdirs();

        try {
            Files.copy(src.toPath(), dest.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
