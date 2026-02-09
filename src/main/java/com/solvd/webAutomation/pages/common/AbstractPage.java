package com.solvd.webAutomation.pages.common;

import com.solvd.webAutomation.actions.NavActions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractPage {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected NavActions navActions;



    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.navActions = new NavActions(driver);
    }

    protected void click(WebElement element) {
        logger.info("Clicking on element [{}]", element.getTagName());
        navActions.click(element);
    }

    protected void click(WebElement element, String elementName) {
        logger.info("Clicking on element [{}]", elementName);
        navActions.click(element);
    }

    protected void type(WebElement element, String text) {
        logger.info("Typing on element [{}]", element.getTagName());
        navActions.type(element, text);
    }

    protected String getText(WebElement element) {
        return getText(element,element.getTagName());
    }

    protected String getText(WebElement element, String elementName) {
        logger.info("Getting text from element [{}]", elementName);
        navActions.waitVisible(element);
        return element.getText();
    }

    protected Boolean isVisible(WebElement element) {
        logger.info("Checking if visibility of element [{}]", element.getTagName());
        try {
            navActions.waitVisible(element);
            logger.info("Element [{}] is visible", element.getTagName());
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not visible", element.getTagName());
            return false;
        }
    }

    protected Boolean isVisible(WebElement element, String elementName) {
        logger.info("Checking if visibility of element [{}]", elementName);
        try {
            navActions.waitVisible(element);
            logger.info("Element [{}] is visible", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not visible", elementName);
            return false;
        }
    }

    protected Boolean isClickable(WebElement element) {
        return isClickable(element,element.getTagName());
    }

    protected Boolean isClickable(WebElement element, String elementName) {

        logger.info("Checking if clickable on element [{}]", elementName);
        try {
            navActions.waitClickable(element);
            logger.info("Element [{}] is clickable", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not clickable", elementName);
            return false;
        }
    }

    protected void waitUntilPageIsReady() {
        logger.info("Waiting for the page to load");
        navActions.waitUntilPageIsReady();
        logger.info("The page is ready");
    }

}
