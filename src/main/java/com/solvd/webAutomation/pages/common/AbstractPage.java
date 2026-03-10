package com.solvd.webAutomation.pages.common;

import com.solvd.webAutomation.config.ConfigReader;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public abstract class AbstractPage extends AbstractUIObject {

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    int waitDuration = Integer.parseInt(ConfigReader.get("wait_duration"));

    public AbstractPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, waitDuration), this);

        logger.info("Page Created | Thread: {} | Driver: {}",
                Thread.currentThread().getId(),
                System.identityHashCode(driver)
        );
    }

    protected abstract WebElement getPageLoadedIndicator();

    public boolean isPageVisible() {
        WebElement element = getPageLoadedIndicator();
        return isVisible(element, this.getClass().getSimpleName() + " Indicator");
    }

    public void waitUntilPageIsReady() {
        String className = this.getClass().getSimpleName();
        logger.info("Waiting for the page [{}] to load", className);

        waitUtil.waitForPageLoad();
        waitUtil.waitForInvisibilityOfElementLocated(LOADER, "Page Loader");
        waitUtil.waitForElementVisible(getPageLoadedIndicator(), className + " Indicator");

        logger.info("The page [{}] is ready", this.getClass().getSimpleName());
    }

    public static HomePage openHomePage(WebDriver driver) {
        HomePage homePage = new HomePage(driver);
        homePage.waitUntilPageIsReady();
        return homePage;
    }

}
