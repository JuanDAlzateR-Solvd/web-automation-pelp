package com.solvd;

import com.solvd.webAutomation.actions.NavActions;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class TopMenuTest {

    @Test (testName = "Functionality of top menu", description = "verifies that home page loads,top Menu works correctly")
    public void buttonFunctionalityTest() {

        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);

        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);
        NavActions navActions = new NavActions(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://demoblaze.com/");

        //wait 1 second, just to debug code
        int timePause=1;
        navActions.pause(timePause);

        topMenu.clickButton(TopMenu.MenuItem.HOME);

        navActions.pause(timePause);
        topMenu.clickButton(TopMenu.MenuItem.CONTACT);

        navActions.pause(timePause);
        topMenu.clickButton(TopMenu.MenuItem.ABOUTUS);

        navActions.pause(timePause);
        topMenu.clickButton(TopMenu.MenuItem.CART);

        navActions.pause(timePause);
        topMenu.clickButton(TopMenu.MenuItem.LOGIN);

        navActions.pause(timePause);
        topMenu.clickButton(TopMenu.MenuItem.SIGNUP);

        driver.quit();

    }
}

