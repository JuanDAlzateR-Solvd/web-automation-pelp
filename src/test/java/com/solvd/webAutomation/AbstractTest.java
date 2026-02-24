package com.solvd.webAutomation;

import com.solvd.webAutomation.components.ContactModal;
import com.solvd.webAutomation.components.LogInModal;
import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.config.ConfigReader;
import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.pages.desktop.CartPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeMethod
    public void setUp(Method method) {
        DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        WebDriver driver = DriverFactory.getDriver();
        //DriverRunMode LOCAL or REMOTE. REMOTE Requires Selenium server standalone.
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("url"));

        RemoteWebDriver remoteDriver = (RemoteWebDriver) DriverFactory.getDriver();
        SessionId session = remoteDriver.getSessionId();
        logger.info("Starting Test: " + method.getName() + "| Thread: " + Thread.currentThread().getName()
                + " | Driver hash: " + driver.hashCode() + " | Session ID: " + session.toString());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            DriverFactory.quitDriver();
        } catch (Exception e) {
            logger.warn("Driver already closed: " + e.getMessage());
        }
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public void clickCategory(HomePage homePage, HomePage.MenuItem category, ProductGrid productGrid) {
        homePage.click(category);
        homePage.waitUntilPageIsReady();
        homePage.waitUntilVisible(productGrid.getProductGridContainer());
    }

    public void clickCart(TopMenu topMenu, CartPage cartPage) {
        topMenu.click(TopMenu.MenuItem.CART);
        cartPage.waitUntilPageIsReady();
//        cartPage.waitVisible(cartPage.getProductGridContainer());
    }

    public void clickContact(TopMenu topMenu, ContactModal contactModal) {
        topMenu.click(TopMenu.MenuItem.CONTACT);
        contactModal.waitUntilPageIsReady();
        contactModal.click(contactModal.getTitle());
    }

    public void clickLogIn(TopMenu topMenu, LogInModal logInModal) {
        topMenu.click(TopMenu.MenuItem.LOG_IN);
        logInModal.waitUntilPageIsReady();
        logInModal.waitUntilVisible(logInModal.getTitle());
    }

}
