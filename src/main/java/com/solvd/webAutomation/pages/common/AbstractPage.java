package com.solvd.webAutomation.pages.common;

import com.solvd.webAutomation.actions.NavActions;
import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not visible", element.getTagName());
            return false;
        }
    }

    protected Boolean isClickable(WebElement element) {
        logger.info("Checking if clickable on element [{}]", element.getTagName());
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Element [{}] is clickable", element.getTagName());
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not clickable", element.getTagName());
            return false;
        }
    }
}
