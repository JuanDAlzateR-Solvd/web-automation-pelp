package com.solvd.webAutomation;

import com.solvd.webAutomation.actions.NavActions;
import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.driver.DriverFactory;
import com.solvd.webAutomation.driver.DriverRunMode;
import com.solvd.webAutomation.driver.DriverType;
import com.solvd.webAutomation.pages.desktop.HomePage;
import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.WebDriver;
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
    protected HomePage homePage;
    protected ProductPage productPage;
    protected TopMenu topMenu;
    protected ProductGrid productGrid;
    protected NavActions navActions;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver(DriverRunMode.LOCAL, DriverType.CHROME);
        //DriverRunMode LOCAL or REMOTE. REMOTE Requires Selenium server standalone.

        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1)); //not mix implicitly wait with explicitly wait
        driver.get("https://demoblaze.com/");

        //IMPORTANT: never initialize Page Objects, before driver.get
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        topMenu = new TopMenu(driver);
        navActions = new NavActions(driver);
        productGrid = new ProductGrid(driver);

        homePage.waitUntilPageLoads();

        //wait 1 second, just to debug code
//        int timePause = 1000;
//        navActions.pause(timePause);

        logger.info("Thread: {} | Driver: {}",
                Thread.currentThread().getName(),
                System.identityHashCode(driver));

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            DriverFactory.quitDriver();
        }catch (Exception e){
            logger.warn("Driver already closed: "+e.getMessage());
        }
    }

    //

}
