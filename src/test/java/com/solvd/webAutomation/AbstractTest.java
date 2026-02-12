package com.solvd.webAutomation;

import com.solvd.webAutomation.components.ContactModal;
import com.solvd.webAutomation.components.LogInModal;
import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.pages.desktop.CartPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;

public class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    //    protected WebDriver driver;
    protected WebDriverWait wait;

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            DriverFactory.quitDriver();
        } catch (Exception e) {
            logger.warn("Driver already closed: " + e.getMessage());
        }
    }

    public WebDriver initializeDriver() {
        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/");

        return driver;
    }

    public void clickCategory(HomePage homePage, HomePage.MenuItem category, ProductGrid productGrid) {
        homePage.clickBy(category);
        homePage.waitUntilPageIsReady();
        homePage.waitVisible(productGrid.getGrid());
    }

    public WebElement getProductNumber(ProductGrid productGrid, int productNumber) {
        List<WebElement> products = productGrid.getElementsList();
        WebElement product = products.get(productNumber);
        logger.info(productGrid.getTextOf(product));
        //  productGrid.waitVisible(firstProduct);
        return product;
    }

    public void clickCart(TopMenu topMenu, CartPage cartPage) {
        topMenu.clickMenuItem(TopMenu.MenuItem.CART);
        cartPage.waitUntilPageIsReady();
        cartPage.waitVisible(cartPage.getGrid());
    }

    public void clickContact(TopMenu topMenu, ContactModal contactModal) {
        topMenu.clickMenuItem(TopMenu.MenuItem.CONTACT);
        contactModal.waitUntilPageIsReady();
        contactModal.waitVisible(contactModal.getTitle());
    }

    public void clickLogIn(TopMenu topMenu, LogInModal logInModal) {
        topMenu.clickMenuItem(TopMenu.MenuItem.LOG_IN);
        logInModal.waitUntilPageIsReady();
        logInModal.waitVisible(logInModal.getTitle());
    }

    public List<WebElement> getCartProducts(CartPage cartPage) {
        List<WebElement> cartProducts = cartPage.getElementsList();
        logger.info("products in cart:{}", cartProducts.size());
        cartProducts.forEach(p -> {
            logger.info(p.getText());
        });
        return cartProducts;
    }

    public int findProductIndexInCart(List<WebElement> cartProducts, String productName) {
        int productIndex = -1;

        OptionalInt index = IntStream.range(0, cartProducts.size())
                .filter(i -> cartProducts.get(i).getText().contains(productName))
                .findFirst();
        if (index.isPresent()) {
            logger.info("Product is in the cart in position {}", index.getAsInt());
            productIndex = index.getAsInt();
        } else {
            logger.info("Product is not in the cart");
        }
        return productIndex;
    }

    public void deleteProduct(CartPage cartPage, int productIndex) {
        List<WebElement> products = cartPage.getElementsList();
        List<WebElement> deleteButtons = cartPage.getDeleteButtonsList();
        WebElement productToDelete = products.get(productIndex);
        String productName = cartPage.getTextOf(productToDelete);
        logger.info("Deleting product {}", productName);
        cartPage.click(deleteButtons.get(productIndex), "deleteButton" + productIndex);
        cartPage.pause(1000);
        cartPage.waitUntilPageIsReady();
        if (deleteButtons.size() > 1) {
            cartPage.waitVisible(cartPage.getGrid());
        }

    }

    public String addProductToCart(ProductGrid productGrid, int productIndex, ProductPage productPage, TopMenu topMenu) {
        WebElement product = getProductNumber(productGrid, productIndex);
        String productName = productGrid.getProductName(product);
        productGrid.clickProduct(product);

        productPage.clickAddToCartButton();
        productPage.acceptProductAddedAlert();

        topMenu.clickMenuItem(TopMenu.MenuItem.HOME);
        topMenu.waitUntilPageIsReady();

        return productName;
    }

    public String addRandomProductToCart(ProductGrid productGrid, ProductPage productPage, TopMenu topMenu) {
        productGrid.waitVisible(productGrid.getGrid());
        List<WebElement> products = productGrid.getElementsList();
        int size = products.size();

        Random rand = new Random();
        int randomNum = rand.nextInt(size);

        return addProductToCart(productGrid, randomNum, productPage, topMenu);
    }

}
