package com.solvd.webAutomation;


import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;

import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.pages.desktop.HomePage;

import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

public class DemoblazeTest extends AbstractTest {
    private static final Logger logger =
            LoggerFactory.getLogger(DemoblazeTest.class);

    @Test(testName = "Functionality of top menu", description = "verifies that home page loads,top Menu works correctly")
    public void buttonFunctionalityTest() {
        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/");

        TopMenu topMenu = new TopMenu(driver);
        topMenu.waitUntilPageIsReady();



        //wait 1 second, just to debug code
        int timePause = 2000;
        topMenu.pause(timePause);

        topMenu.clickBy(TopMenu.MenuItem.HOME);

        topMenu.pause(timePause);
        topMenu.clickBy(TopMenu.MenuItem.CONTACT);

    }

    @Test(testName = "List of Products - Task1", description = "filters the products by category, then prints in console all the products")
    public void ListOfProductsTest() {
        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/");

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);

        homePage.waitUntilPageIsReady();

        //The navActions pauses are to emulate a little more the behavior of human, not bot
        //Problems with bot navigation detection

        homePage.clickBy(HomePage.MenuItem.LAPTOPS);

        homePage.pause(500);

        List<String> productsList = productGrid.productsList();
        productsList.forEach(logger::info);

//        navActions.pause(500);

        Assert.assertFalse(productsList.isEmpty());

    }

    @Test(testName = "Product Search by Category - Task3 TC-001",
            description = "filters the products by a category, then verifies info from the last product of last page",
            dataProvider = "Category MenuItem Provider")
    public void SearchOfProductByCategoryTest(HomePage.MenuItem category) {
        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/");

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.waitUntilPageIsReady();

        homePage.clickBy(category);

        homePage.waitUntilPageIsReady();

        if (productGrid.nextButtonIsClickable() && category != HomePage.MenuItem.MONITORS) {
            //demoblaze.com has a bug, when click on category monitors it shows the next button, even thought it shouldn't.
            productGrid.clickNextButton();
        }

        homePage.waitVisible(productGrid.getGrid());

        List<WebElement> products = productGrid.getElementsList();
        WebElement lastProduct = products.get(products.size() - 1);

        logger.info(productGrid.getTextOf(lastProduct));
        homePage.click(lastProduct);

        SoftAssert sa = new SoftAssert();

        Arrays.stream(ProductPage.InfoItem.values()).sequential()
                .forEach(info -> {
                    sa.assertTrue(productPage.isVisible(info));
                });

        sa.assertAll();

    }

    @Test(testName = "Add Product to Cart - Task3 TC-002",
            description = "choose the first product from a category and add it to cart, then verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void AddProductToCartTest(HomePage.MenuItem category) {
        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/");

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.waitUntilPageIsReady();

        homePage.clickBy(category);

        homePage.waitUntilPageIsReady();

        homePage.waitVisible(productGrid.getGrid());

        List<WebElement> products = productGrid.getElementsList();
        WebElement firstProduct = products.get(0);

        logger.info(productGrid.getTextOf(firstProduct));
        homePage.click(firstProduct);

        SoftAssert sa = new SoftAssert();

        productPage.clickAddToCartButton2();

       productPage.acceptProductAddedAlert();

        productPage.pause(5000);

        sa.assertAll();

    }



    //Data Providers
    @DataProvider(name = "Category MenuItem Provider")
    public Object[][] HomePageMenuItem() {
        return Arrays.stream(HomePage.MenuItem.values())
                .map(type -> new Object[]{type})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "Category MenuItem Provider2")
    public Object[][] HomePageMenuItem2() {
        return new Object[][]{
                {TopMenu.MenuItem.HOME},
                {TopMenu.MenuItem.CONTACT},
                {TopMenu.MenuItem.CART}


        };
    }

}
