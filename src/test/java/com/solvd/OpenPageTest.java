package com.solvd;

import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class OpenPageTest {

    @Test(testName = "Open Home Page ", description = "verifies that home page loads")
    public void simpleTest() {

        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.amazon.com/");

        //Thread sleep just to debug code
        int timeSleep = 3000;
        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        driver.get("https://www.next.co.uk/");

        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        driver.quit();

    }
}

