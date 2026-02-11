package com.solvd.webAutomation;

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
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;

public class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    //    protected WebDriver driver;
    protected WebDriverWait wait;


    @BeforeMethod
    public void setUp() {
//        WebDriver driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        //DriverRunMode LOCAL or REMOTE. REMOTE Requires Selenium server standalone.

//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1)); //not mix implicitly wait with explicitly wait
//        driver.get("https://demoblaze.com/");

        //IMPORTANT: never initialize Page Objects, before driver.get
//        homePage = new HomePage(driver);
//        productPage = new ProductPage(driver);
//        topMenu = new TopMenu(driver);
//        productGrid = new ProductGrid(driver);
//
//        homePage.waitUntilPageLoads();

        //wait 1 second, just to debug code
//        int timePause = 1000;
//        navActions.pause(timePause);

//        logger.info("Thread: {} | Driver: {}",
//                Thread.currentThread().getName(),
//                System.identityHashCode(driver));

    }

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

    public void clickCategory(HomePage homePage,HomePage.MenuItem category, ProductGrid productGrid) {
        homePage.clickBy(category);
        homePage.waitUntilPageIsReady();
        homePage.waitVisible(productGrid.getGrid());
    }

    public WebElement getProductNumber(ProductGrid productGrid,int productNumber) {
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

    public void clickContact(TopMenu topMenu, CartPage cartPage) {
        topMenu.clickMenuItem(TopMenu.MenuItem.CONTACT);
        cartPage.waitUntilPageIsReady();
        cartPage.waitVisible(cartPage.getGrid());
    }

    public List<WebElement> getCartProducts(CartPage cartPage) {
        List<WebElement> cartProducts = cartPage.getElementsList();
        logger.info("products in cart:{}", cartProducts.size());
        cartProducts.forEach(p -> {
            logger.info(p.getText());
        });
        return cartProducts;
    }

    public int findProductIndexInCart(List<WebElement> cartProducts,String productName) {
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

    public void deleteProduct(CartPage cartPage,int productIndex) {
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

    public String addProductToCart(ProductGrid productGrid,int productIndex,ProductPage productPage, TopMenu topMenu) {
        WebElement product = getProductNumber(productGrid, productIndex);
        String productName = productGrid.getProductName(product);
        productGrid.clickProduct(product);

        productPage.clickAddToCartButton();
        productPage.acceptProductAddedAlert();

        topMenu.clickMenuItem(TopMenu.MenuItem.HOME);
        topMenu.waitUntilPageIsReady();

        return productName;
    }

    public String addRandomProductToCart(ProductGrid productGrid,ProductPage productPage, TopMenu topMenu) {
        productGrid.waitVisible(productGrid.getGrid());
        List<WebElement> products = productGrid.getElementsList();
        int size = products.size();

        Random rand = new Random();
        int randomNum = rand.nextInt(size);

        return addProductToCart(productGrid,randomNum,productPage,topMenu);
    }

}
