package com.solvd.webAutomation.pages.common;

import com.solvd.webAutomation.actions.ElementActions;
import com.solvd.webAutomation.wait.WaitService;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUIObject {
    protected final Logger logger= LoggerFactory.getLogger(this.getClass());
    protected final WebDriver driver;
    protected final WaitService waitService;
    protected final ElementActions actions;

    protected AbstractUIObject(WebDriver driver) {
        this.driver = driver;
        this.waitService = new WaitService(driver);
        this.actions = new ElementActions(driver, waitService);
    }

    protected void click(WebElement element) {
        actions.click(element);
    }

    protected void click(WebElement element, String elementName) {
        actions.click(element, elementName);
    }
    protected void click(By locator, String elementName) {
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

    protected boolean isVisible(WebElement element) {
        return isVisible(element, element.getTagName());
    }

    protected boolean isVisible(WebElement element, String elementName) {
        logger.info("Checking if visibility of element [{}]", elementName);
        try {
            waitUntilVisible(element,elementName);
            logger.info("Element [{}] is visible", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not visible", elementName);
            return false;
        }
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

    protected boolean isInViewport(WebElement element, String elementName) {
        logger.info("Checking if element [{}] is in viewport", elementName);

        String script = """
                    var elem = arguments[0],
                        box = elem.getBoundingClientRect();
                    return (
                        box.top >= 0 &&
                        box.left >= 0 &&
                        box.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
                        box.right <= (window.innerWidth || document.documentElement.clientWidth)
                    );
                """;

        boolean isVisible = Boolean.TRUE.equals(
                (Boolean) ((JavascriptExecutor) driver).executeScript(script, element)
        );

        logger.info("Element [{}] is {}in viewport", elementName, isVisible ? "" : "not ");
        return isVisible;
    }

    protected void waitUntilClickable(WebElement element,String elementName) {
        waitService.waitForElementClickable(element,elementName);
    }

    public void waitUntilVisible(WebElement element, String elementName) {
        waitService.waitForElementVisible(element,  elementName);
        scrollTo(element);
    }

    public void debugPause(int milliseconds) {
        actions.debugPause(milliseconds);
    }

}