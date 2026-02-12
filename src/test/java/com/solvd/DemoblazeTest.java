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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class DemoblazeTest {
    private static final Logger logger =
            LoggerFactory.getLogger(DemoblazeTest.class);
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        //DriverRunMode LOCAL or REMOTE. REMOTE Requires Selenium server standalone.
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demoblaze.com/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @Test(testName = "Functionality of top menu", description = "verifies that home page loads,top Menu works correctly")
    public void verifyTopMenuNavigation() {

        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);
        NavActions navActions = new NavActions(driver);

        navActions.waitUntilPageIsReady(homePage);

        SoftAssert sa = new SoftAssert();

        Arrays.stream(TopMenu.MenuItem.values())
                .forEach(menuItem -> {
                    topMenu.clickButton(menuItem);
                    sa.assertTrue(topMenu.isVisible(menuItem));
                    topMenu.clickCloseButton(menuItem);
                });

        sa.assertAll();
    }

    @Test(testName = "List of Products - Task1", description = "filters the products by category, then prints in console all the products")
    public void verifyProductsDisplayedForSelectedCategory() {

        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);
        NavActions navActions = new NavActions(driver);
        ProductGrid productGrid = new ProductGrid(driver);

        navActions.waitUntilPageIsReady(homePage);

        homePage.clickLaptopsButton();

        navActions.waitUntilPageIsReady(homePage);
        navActions.waitVisible(productGrid.getGrid());

        List<String> productsList = productGrid.getProductTitles();
        productsList.forEach(logger::info);

        Assert.assertFalse(productsList.isEmpty());

    }
}

