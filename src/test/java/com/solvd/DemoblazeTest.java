package com.solvd;

import com.solvd.webAutomation.actions.NavActions;
import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class DemoblazeTest {
    private static final Logger logger =
            LoggerFactory.getLogger(DemoblazeTest.class);

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
        topMenu.clickButton(TopMenu.MenuItem.ABOUT_US);

        navActions.pause(timePause);
        topMenu.clickButton(TopMenu.MenuItem.CART);

        navActions.pause(timePause);
        topMenu.clickButton(TopMenu.MenuItem.LOG_IN);

        navActions.pause(timePause);
        topMenu.clickButton(TopMenu.MenuItem.SIGN_UP);

        driver.quit();
    }

    @Test (testName = "List of Products", description = "filters the products by category, then prints in console all the products")
    public void ListOfProductsTest() {

        WebDriver driver = DriverFactory.createDriver(DriverRunMode.REMOTE, DriverType.CHROME);
        //DriverRunMode LOCAL or REMOTE. REMOTE Requieres Selenium server standalone.

        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);
        NavActions navActions = new NavActions(driver);
        ProductGrid productGrid = new ProductGrid(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://demoblaze.com/");

        //The navActions pauses are to emulate a little more the behavior of human, not bot
        //Problems with bot navigation detection
        navActions.pause(1);
        homePage.clickLaptopsButton();

        navActions.pause(1);

        List<String> productsList=productGrid.productsList();
        productsList.forEach(logger::info);

        navActions.pause(3);

        Assert.assertFalse(productsList.isEmpty());

        driver.quit();

    }
}

