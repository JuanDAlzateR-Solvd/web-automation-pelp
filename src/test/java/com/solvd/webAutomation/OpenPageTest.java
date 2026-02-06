package com.solvd.webAutomation;

import com.solvd.webAutomation.actions.NavActions;
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
        NavActions navActions = new NavActions(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.amazon.com/");

        int timePause = 1;
        navActions.pause(timePause);

        driver.get("https://www.next.co.uk/");
        navActions.pause(timePause);

        driver.quit();

    }
}

