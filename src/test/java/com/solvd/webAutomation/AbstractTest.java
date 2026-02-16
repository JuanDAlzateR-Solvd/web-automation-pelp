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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;


public class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        //DriverRunMode LOCAL or REMOTE. REMOTE Requires Selenium server standalone.
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/");
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
        WebDriver driver = DriverFactory.createDriver(DriverRunMode.REMOTE, DriverType.CHROME);
        driver.manage().window().maximize();
        driver.get("https://demoblaze.com/");

        return driver;
    }

    public void clickCategory(HomePage homePage, HomePage.MenuItem category, ProductGrid productGrid) {
        homePage.clickBy(category);
        homePage.waitUntilPageIsReady();
        homePage.waitVisible(productGrid.getGrid());
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


}
