package com.solvd.webAutomation;

import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;

import com.solvd.webAutomation.components.*;

import com.solvd.webAutomation.flows.ShoppingFlow;
import com.solvd.webAutomation.pages.desktop.CartPage;
import com.solvd.webAutomation.pages.desktop.HomePage;

import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.WebDriver;
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

    @Test(testName = "Functionality of top menu modals", description = "verifies that home page loads, and top Menu modals works correctly")
    public void verifyTopMenuNavigation() {
        WebDriver driver = getDriver();
        HomePage homePage = new HomePage(driver);

        TopMenu topMenu = homePage.getTopMenu();
        SoftAssert sa = new SoftAssert();

        logger.info("Testing Menu item: [Contact Modal]");
        ContactModal contactModal = topMenu.openContactModal();
        sa.assertTrue(contactModal.isModalVisible(), "Contact Modal should be visible");
        contactModal.close();

        //other modals
        sa.assertAll();
    }

    @Test(testName = "List of Products - Task1", description = "filters the products by category, then prints in console all the products")
    public void verifyProductsDisplayedForSelectedCategory() {
        WebDriver driver = getDriver();
        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.selectCategory(HomePage.MenuItem.LAPTOPS);

        List<String> productsList = productGrid.getProductTitles();
        productsList.forEach(logger::info);

        Assert.assertFalse(productsList.isEmpty(), "There are no products in the grid");
    }

    @Test(testName = "Product Search by Category - Task3 TC-001",
            description = "filters the products by a category, then verifies info from the last product of last page",
            dataProvider = "Category MenuItem Provider")
    public void verifyInfoOfLastProductOfACategory(HomePage.MenuItem category) {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.selectCategory(category);

        int productIndex = productGrid.getProductCount() - 1;

        ProductPage productPage = productGrid
                .openProductByIndex(productIndex);

        SoftAssert sa = new SoftAssert();

        sa.assertTrue(productPage.isInfoVisible(), "Product Page should have all info visible");

        sa.assertAll();
    }

    @Test(testName = "Add Product to Cart - Task3 TC-002",
            description = "choose the first product from a category and add it to cart, then verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void verifyAddFirstProductOfCategoryToCart(HomePage.MenuItem category) {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.selectCategory(category);

        String productName = productGrid.getProductNameByIndex(0);

        CartPage cartPage = productGrid
                .openProductByIndex(0)
                .addToCart()
                .getTopMenu()
                .goToCartPage();

        SoftAssert sa = new SoftAssert();

        sa.assertTrue(cartPage.containsProduct(productName),
                "Product was not added to cart");

        sa.assertFalse(cartPage.getTotalPrice().isEmpty(), "Total price is empty");

        sa.assertAll();

    }

    @Test(testName = "Delete Product from Cart - Task3 TC-003",
            description = "choose the first product from a category and add it to cart, then delete it, verifies info in shopping cart",
            dataProvider = "Category MenuItem Provider")
    public void verifyDeleteProductOfCategoryFromCart(HomePage.MenuItem category) {
        WebDriver driver = getDriver();
        HomePage homePage = new HomePage(driver);

        SoftAssert sa = new SoftAssert();

        ProductGrid productGrid = homePage.selectCategory(category);

        String productName = productGrid.getProductNameByIndex(0);

        CartPage cartPage = productGrid
                .openProductByIndex(0)
                .addToCart()
                .getTopMenu()
                .goToCartPage();

        sa.assertTrue(cartPage.containsProduct(productName),
                "Product was not added to cart");

        int initialSize = cartPage.getProductCount();

        cartPage.deleteProduct(productName);

        sa.assertEquals(
                cartPage.getProductCount(),
                initialSize - 1,
                "Product was not deleted"
        );

        sa.assertAll();

    }

    @Test(testName = "Empty Shopping Cart - Task3 TC-004",
            description = "add random products to the shopping cart, then empties the cart")
    public void verifyAllDeleteButtonsToEmptyShoppingCart() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        ShoppingFlow shoppingFlow = new ShoppingFlow(driver);

        String productName = "";
        for (int i = 0; i < 5; i++) {
            productName = shoppingFlow.addRandomProductToCart();
        }

        SoftAssert sa = new SoftAssert();
        CartPage cartPage = homePage.getTopMenu().goToCartPage();

        cartPage.waitUntilCartShowsProducts(); //change later

        int initialSize = cartPage.getProductCount();

        sa.assertFalse(initialSize == 0, "The shopping cart is empty");

        while (!cartPage.isCartEmpty()) {
            cartPage.deleteProduct(0);
        }

        logger.debug("finished empty shopping cart");
        int finalSize = cartPage.getProductCount();
        sa.assertTrue(finalSize == 0, "The shopping cart is not empty");
        logger.debug("finished checking shopping cart");

        sa.assertAll();

    }

    @Test(testName = "Fill Contact Form - Task3 TC-005",
            description = "click on contact, then fills the form and sends it")
    public void verifyFillInfoInContactFormAndSend2() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);

        ContactModal contactModal = homePage.getTopMenu().openContactModal();
        SoftAssert sa = new SoftAssert();

        sa.assertTrue(contactModal.isContactModalVisible(), "Contact modal is not visible");

        contactModal.submitContactForm("example@email.com",
                "Example Name",
                "This is a test message");

        sa.assertTrue(contactModal.isAlertPresent());
        contactModal.acceptMessageAlert();

        sa.assertAll();
    }

    @Test(testName = "Log In with wrong credentials - Task3 TC-006",
            description = "click on log in, then fills the form and click log in button")
    public void verifyLogInAttemptWithWrongCredentials() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);

        LogInModal logInModal = homePage.getTopMenu().openLogInModal();

        SoftAssert sa = new SoftAssert();
        sa.assertTrue(logInModal.isLogInModalVisible(), "Log In modal is not visible");

        logInModal.logInWith("example@email.com", "Example Password");
        sa.assertTrue(logInModal.isAlertPresent());
        logInModal.acceptWrongPasswordAlert();

        sa.assertAll();
    }

    @Test(testName = "VerifyFooterInfo- Task3 TC-007",
            description = "click on log in, then fills the form and click log in button")
    public void verifyFooterVisibilityAndInfo() {
        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);

        Footer footer = homePage.getFooter();

        SoftAssert sa = new SoftAssert();

        sa.assertFalse(footer.isVisibleInScreen(), "Footer is visible in screen after load home page");

        footer.ensureVisible();

        sa.assertTrue(footer.isVisibleInScreen(), "Footer is not visible in screen at bottom of page");
        sa.assertTrue(footer.verifyFooterInfo(), "Footer info is not completely visible");

        sa.assertAll();
    }

    //Data Providers
    @DataProvider(name = "Category MenuItem Provider")
    public Object[][] HomePageMenuItem() {
        return Arrays.stream(HomePage.MenuItem.values())
                .map(type -> new Object[]{type})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "TopMenu Modal MenuItem Provider")
    public Object[][] ModalMenuItem() {
        return new Object[][]{
                {TopMenu.MenuItem.CONTACT},
                {TopMenu.MenuItem.ABOUT_US},
                {TopMenu.MenuItem.LOG_IN},
                {TopMenu.MenuItem.SIGN_UP}
        };
    }

}
