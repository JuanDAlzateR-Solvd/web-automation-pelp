package com.solvd.webAutomation.components;

import com.solvd.webAutomation.actions.ElementActions;
import com.solvd.webAutomation.config.ConfigReader;
import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.wait.WaitService;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractComponent {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;
    protected WebElement root;
    protected WaitService waitService;
    protected ElementActions actions;
    private int waitDuration;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public AbstractComponent(WebDriver driver,WebElement root){
        this.driver=driver;
        this.root=root;
        this.waitService = new WaitService(driver);
        this.actions = new ElementActions(driver);
        this.waitDuration = Integer.parseInt(ConfigReader.get("wait_duration"));

        PageFactory.initElements(
                new DefaultElementLocatorFactory(root),
                this
        );

        logger.info("Page Created | Thread: {} | Driver: {}",
                Thread.currentThread().getId(),
                System.identityHashCode(driver)
        );

        waitUntilComponentIsReady();
    }

    protected abstract WebElement getComponentLoadedIndicator();

    public WebElement getRoot() {
        return root;
    }

    public void click(WebElement element) {
        actions.click(element);
    }

    public void click(WebElement element, String elementName) {
        actions.click(element, elementName);
    }

    public void click(By locator, String elementName) {
        actions.click(locator, elementName);
    }

    protected void type(WebElement element, String elementName, String text) {
        actions.type(element, elementName, text);
    }

    protected String getText(WebElement element) {
        return actions.getText(element);
    }

    protected String getText(WebElement element, String elementName) {
        return actions.getText(element, elementName);
    }

    protected void scrollTo(@NonNull WebElement element) {
        actions.scrollTo(element);
    }

    protected boolean isClickable(WebElement element) {
        return isClickable(element, element.getTagName());
    }

    protected boolean isClickable(WebElement element, String elementName) {

        logger.info("Checking if clickable on element [{}]", elementName);
        try {
            waitUntilClickable(element,elementName);
            logger.info("Element [{}] is clickable", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not clickable", elementName);
            return false;
        }
    }
    protected void waitUntilClickable(WebElement element,String elementName) {
        waitService.waitForElementClickable(element,elementName);
    }
    public void waitUntilComponentIsReady() {
        String className = this.getClass().getSimpleName();
        logger.info("Waiting for the component [{}] to load", className);

        waitService.waitForPageLoad();
//        waitService.waitForInvisibilityOfElementLocated(LOADER, "Component Loader");
        waitService.waitForElementVisible(getComponentLoadedIndicator(),className+" Indicator");

        logger.info("The page [{}] is ready", this.getClass().getSimpleName());
    }
}
