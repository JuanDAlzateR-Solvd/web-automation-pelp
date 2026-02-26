package com.solvd.webAutomation.components;

import com.solvd.webAutomation.actions.ElementActions;
import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.wait.WaitService;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractComponent {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;
    protected WebElement root;
    protected WaitService waitService;
    protected ElementActions actions;


    public AbstractComponent(WebDriver driver,WebElement root){
        this.driver=driver;
        this.root=root;
        this.waitService = new WaitService(driver);
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
}
