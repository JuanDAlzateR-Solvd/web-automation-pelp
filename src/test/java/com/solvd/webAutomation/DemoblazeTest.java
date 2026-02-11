package com.solvd.webAutomation;


import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;

import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.pages.desktop.CartPage;
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

import java.util.*;
import java.util.stream.IntStream;

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

        topMenu.clickMenuItem(TopMenu.MenuItem.HOME);

        topMenu.pause(timePause);
        topMenu.clickMenuItem(TopMenu.MenuItem.CONTACT);

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
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.waitUntilPageIsReady();

        homePage.clickBy(category);

        homePage.waitUntilPageIsReady();

        productGrid.clickNextButtonIfPossible(category);

        homePage.waitVisible(productGrid.getGrid());

        List<WebElement> products = productGrid.getElementsList();
        WebElement lastProduct = products.get(products.size() - 1);

        logger.info(productGrid.getTextOf(lastProduct));

        productGrid.clickProduct(lastProduct);

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
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);
        ProductPage productPage = new ProductPage(driver);
        TopMenu topMenu = new TopMenu(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.waitUntilPageIsReady();

        clickCategory(homePage, category, productGrid);

        WebElement firstProduct = getProductNumber(productGrid, 0);

        String firstProductName = productGrid.getProductName(firstProduct);

//        productGrid.waitVisible(firstProduct);
        productGrid.clickProduct(firstProduct);

        SoftAssert sa = new SoftAssert();

        productPage.clickAddToCartButton();
        sa.assertTrue(productPage.isProductAddedAlertPresent());
        productPage.acceptProductAddedAlert();

        clickCart(topMenu, cartPage);

        List<WebElement> cartProducts = getCartProducts(cartPage);

        boolean productInCart = cartProducts.stream()
                .map(WebElement::getText)
                .anyMatch(s -> s.contains(firstProductName));

        sa.assertTrue(productInCart, "The product is not in the cart");
        sa.assertFalse(cartPage.getTotalPrice().isEmpty(), "Total price is empty");

//        productPage.pause(2000);

        sa.assertAll();

    }

    @Test(testName = "Delete Product from Cart - Task3 TC-003",
            description = "choose the first product from a category and add it to cart, then delete it, verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void DeleteProductFromCartTest(HomePage.MenuItem category) {
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);
        ProductPage productPage = new ProductPage(driver);
        TopMenu topMenu = new TopMenu(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.waitUntilPageIsReady();

        clickCategory(homePage, category, productGrid);

        WebElement firstProduct = getProductNumber(productGrid, 0);
        String firstProductName = productGrid.getProductName(firstProduct);
        productGrid.clickProduct(firstProduct);

        SoftAssert sa = new SoftAssert();

        productPage.clickAddToCartButton();
        sa.assertTrue(productPage.isProductAddedAlertPresent());
        productPage.acceptProductAddedAlert();

        clickCart(topMenu, cartPage);

        List<WebElement> cartProducts = getCartProducts(cartPage);
//        List<WebElement> deleteButtons = cartPage.getDeleteButtonsList();
        sa.assertFalse(cartProducts.isEmpty(), "the shopping cart is empty");

        int productIndex = findProductIndexInCart(cartProducts, firstProductName);

        sa.assertTrue(productIndex != -1, "Product not found in the cart");

        if (productIndex != -1) {
            deleteProduct(cartPage, productIndex);
        }

        cartPage.pause(1000);
        //add wait to reload, and delete pause

        List<WebElement> newCartProducts = cartPage.getElementsList();

        sa.assertTrue(newCartProducts.size() == cartProducts.size() - 1, "The product was not deleted");

        sa.assertAll();

    }

    @Test(testName = "Empty Shopping Cart - Task3 TC-004",
            description = "choose the first product from a category and add it to cart, then delete it, verifies info in shopping cart")
    public void EmptyShoppingCartTest() {
//        WebDriver driver = initializeDriver();
//
//        HomePage homePage = new HomePage(driver);
//        ProductGrid productGrid = new ProductGrid(driver);
//        ProductPage productPage = new ProductPage(driver);
//        TopMenu topMenu = new TopMenu(driver);
//        CartPage cartPage = new CartPage(driver);
//
//        homePage.waitUntilPageIsReady();
//
//        String productName = "";
//        for (int i = 0; i < 5; i++) {
//            productName = addRandomProductToCart(productGrid, productPage, topMenu);
//        }
//
//        SoftAssert sa = new SoftAssert();
//        clickCart(topMenu, cartPage);
//
//        clickCart(topMenu, cartPage);
//
//        List<WebElement> cartProducts = getCartProducts(cartPage);
////        List<WebElement> deleteButtons = cartPage.getDeleteButtonsList();
//        sa.assertFalse(cartProducts.isEmpty(), "the shopping cart is empty");
//
//        int productIndex = findProductIndexInCart(cartProducts, firstProductName);
//
//        sa.assertTrue(productIndex != -1, "Product not found in the cart");
//
//        if (productIndex != -1) {
//            deleteProduct(cartPage, productIndex);
//        }
//
//        cartPage.pause(1000);
//        //add wait to reload, and delete pause
//
//        List<WebElement> newCartProducts = cartPage.getElementsList();
//
//        sa.assertTrue(newCartProducts.size() == cartProducts.size() - 1, "The product was not deleted");
//
//        sa.assertAll();

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
