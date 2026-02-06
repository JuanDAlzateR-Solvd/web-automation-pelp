package com.solvd.webAutomation;

import com.solvd.webAutomation.actions.NavActions;
import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected HomePage homePage;
    protected TopMenu topMenu;
    protected ProductGrid productGrid;
    protected NavActions navActions;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        //DriverRunMode LOCAL or REMOTE. REMOTE Requires Selenium server standalone.

        homePage = new HomePage(driver);
        topMenu = new TopMenu(driver);
        navActions = new NavActions(driver);
        productGrid = new ProductGrid(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://demoblaze.com/");

        //wait 1 second, just to debug code
        int timePause = 1;
        navActions.pause(timePause);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    //
    protected void click(WebElement element) {
        logger.info("Clicking on element [{}]", element.getTagName());
        navActions.click(element);
    }

    protected void type(WebElement element, String text) {
        logger.info("Typing on element [{}]", element.getTagName());
        navActions.type(element, text);
    }

    protected String getText(WebElement element) {
        logger.info("Getting text from element [{}]", element.getTagName());
        navActions.waitVisible(element);
        return element.getText();
    }

    protected Boolean isVisible(WebElement element) {
        logger.info("Checking if visibility of element [{}]", element.getTagName());
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.info("Element [{}] is visible", element.getTagName());
            return true;
        }catch (TimeoutException e) {
            logger.warn("Element [{}] is not visible", element.getTagName());
            return false;
        }
    }
}
