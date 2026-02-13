package com.solvd.webAutomation;


import com.solvd.webAutomation.components.*;

import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.flows.ShoppingFlow;
import com.solvd.webAutomation.pages.desktop.CartPage;
import com.solvd.webAutomation.pages.desktop.HomePage;

import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

public class DemoblazeTest extends AbstractTest {
    private static final Logger logger =
            LoggerFactory.getLogger(DemoblazeTest.class);

    @Test(testName = "Functionality of top menu", description = "verifies that home page loads,top Menu works correctly")
    public void verifyTopMenuNavigation() {
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);

        homePage.waitUntilPageIsReady();

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
        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/");

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);

        homePage.waitUntilPageIsReady();

        homePage.clickBy(HomePage.MenuItem.LAPTOPS);

        homePage.waitUntilPageIsLoaded();

        List<String> productsList = productGrid.getProductTitles();
        productsList.forEach(logger::info);

        Assert.assertFalse(productsList.isEmpty(), "There are no products in the grid");

    }

    @Test(testName = "Product Search by Category - Task3 TC-001",
            description = "filters the products by a category, then verifies info from the last product of last page",
            dataProvider = "Category MenuItem Provider")
    public void verifyInfoOfLastProductOfACategory(HomePage.MenuItem category) {
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
    public void verifyAddFirstProductOfCategoryToCart(HomePage.MenuItem category) {
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);
        ProductPage productPage = new ProductPage(driver);
        TopMenu topMenu = new TopMenu(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.waitUntilPageIsReady();

        clickCategory(homePage, category, productGrid);

        WebElement firstProduct = productGrid.getProductNumber(0);

        String firstProductName = productGrid.getProductName(firstProduct);

        productGrid.clickProduct(firstProduct);

        SoftAssert sa = new SoftAssert();

        productPage.clickAddToCartButton();
        sa.assertTrue(productPage.isProductAddedAlertPresent());
        productPage.acceptProductAddedAlert();

        clickCart(topMenu, cartPage);

        List<WebElement> cartProducts = cartPage.getCartProducts();

        boolean productInCart = cartProducts.stream()
                .map(WebElement::getText)
                .anyMatch(s -> s.contains(firstProductName));

        sa.assertTrue(productInCart, "The product is not in the cart");
        sa.assertFalse(cartPage.getTotalPrice().isEmpty(), "Total price is empty");

        sa.assertAll();

    }

    @Test(testName = "Delete Product from Cart - Task3 TC-003",
            description = "choose the first product from a category and add it to cart, then delete it, verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void verifyDeleteProductOfCategoryFromCart(HomePage.MenuItem category) {
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);
        ProductPage productPage = new ProductPage(driver);
        TopMenu topMenu = new TopMenu(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.waitUntilPageIsReady();

        clickCategory(homePage, category, productGrid);

        WebElement firstProduct = productGrid.getProductNumber(0);
        String firstProductName = productGrid.getProductName(firstProduct);
        productGrid.clickProduct(firstProduct);

        SoftAssert sa = new SoftAssert();

        productPage.clickAddToCartButton();
        sa.assertTrue(productPage.isProductAddedAlertPresent());
        productPage.acceptProductAddedAlert();

        clickCart(topMenu, cartPage);

        List<WebElement> cartProducts = cartPage.getCartProducts();

        sa.assertFalse(cartProducts.isEmpty(), "the shopping cart is empty");

        int productIndex = cartPage.findProductIndexInCart(cartProducts, firstProductName);

        sa.assertTrue(productIndex != -1, "Product not found in the cart");

        if (productIndex != -1) {
            cartPage.deleteProduct(productIndex);
        }

        cartPage.waitUntilPageIsLoaded();

        List<WebElement> newCartProducts = cartPage.getElementsList();

        sa.assertTrue(newCartProducts.size() == cartProducts.size() - 1, "The product was not deleted");

        sa.assertAll();

    }

    @Test(testName = "Empty Shopping Cart - Task3 TC-004",
            description = "add random products to the shopping cart, then empties the cart")
    public void verifyAllDeleteButtonsToEmptyShoppingCart() {
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);
        ProductPage productPage = new ProductPage(driver);
        TopMenu topMenu = new TopMenu(driver);
        CartPage cartPage = new CartPage(driver);

        ShoppingFlow shoppingFlow=new ShoppingFlow(productGrid,productPage,topMenu);

        homePage.waitUntilPageIsReady();

        String productName = "";
        for (int i = 0; i < 5; i++) {
            productName = shoppingFlow.addRandomProductToCart();
        }

        SoftAssert sa = new SoftAssert();
        clickCart(topMenu, cartPage);

        List<WebElement> cartProducts = cartPage.getCartProducts();

        sa.assertFalse(cartProducts.isEmpty(), "the shopping cart is empty");

        while (!cartPage.isCartEmpty()) {
            cartPage.deleteProduct(0);
        }

        List<WebElement> newCartProducts = cartPage.getElementsList();

        sa.assertTrue(newCartProducts.isEmpty(), "The shooping cart is not empty");

        sa.assertAll();

    }

    @Test(testName = "Fill Contact Form - Task3 TC-005",
            description = "click on contact, then fills the form and sends it")
    public void verifyFillInfoInContactFormAndSend() {
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);
        ContactModal contactModal = new ContactModal(driver);

        homePage.waitUntilPageIsReady();

        clickContact(topMenu, contactModal);
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(contactModal.isContactModalVisible(), "Contact modal is not visible");

        contactModal.type(ContactModal.MenuItem.EMAIL, "example@email.com");
        contactModal.type(ContactModal.MenuItem.NAME, "Example Name");
        contactModal.type(ContactModal.MenuItem.MESSAGE, "This is a test message");

        contactModal.clickSendButton();
        sa.assertTrue(contactModal.isAlertPresent());
        contactModal.acceptMessageAlert();

        sa.assertAll();

    }

    @Test(testName = "Log In with wrong credentials - Task3 TC-006",
            description = "click on log in, then fills the form and click log in button")
    public void verifyLogInAttemptWithWrongCredentials() {
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);
        LogInModal logInModal = new LogInModal(driver);

        homePage.waitUntilPageIsReady();

        clickLogIn(topMenu, logInModal);
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(logInModal.isLogInModalVisible(), "Log In modal is not visible");

        logInModal.type(LogInModal.MenuItem.USERNAME, "example@email.com");
        logInModal.type(LogInModal.MenuItem.PASSWORD, "Example Password");

        logInModal.clickLogInButton();
        sa.assertTrue(logInModal.isAlertPresent());
        logInModal.acceptWrongPasswordAlert();

        sa.assertAll();

    }

    @Test(testName = "VerifyFooterInfo- Task3 TC-007",
            description = "click on log in, then fills the form and click log in button")
    public void verifyFooterVisibilityAndInfo() {
        WebDriver driver = initializeDriver();

        HomePage homePage = new HomePage(driver);

        Footer footer = new Footer(driver);

        homePage.waitUntilPageIsReady();

        footer.getGetInTouchText();

        SoftAssert sa = new SoftAssert();

        sa.assertTrue(footer.getAddress().length()>5);
        sa.assertTrue(footer.getPhone().length()>5);
        sa.assertTrue(footer.getEmail().length()>5);

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
