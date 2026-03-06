package com.solvd.webAutomation.actions;

import com.solvd.webAutomation.wait.WaitUtil;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementActions {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;
    protected WaitUtil waitUtil;

    public ElementActions(WebDriver driver, WaitUtil waitUtil) {
        this.driver = driver;
        this.waitUtil = waitUtil;
    }

    public void click(WebElement element, String elementName) {
        logger.info("Clicking on element [{}]", elementName);
        waitUtil.waitForElementClickable(element, elementName);
        scrollTo(element);
        element.click();
    }

    public void click(By locator, String elementName) {
        logger.debug("Finding element [{}] to click", elementName);
        WebElement element = driver.findElement(locator);

        logger.info("Clicking on element [{}]", elementName);
        waitUtil.waitForElementClickable(element, elementName);
        scrollTo(element);
        element.click();
    }

    public void type(WebElement element, String elementName, String text) {
        logger.info("Typing on element [{}]", elementName);

        waitUtil.waitForElementVisible(element, elementName);
        scrollTo(element);
        element.clear();
        element.sendKeys(text);

    }

    public String getText(WebElement element, String elementName) {
        logger.info("Getting text from element [{}]", elementName);

        waitUtil.waitForElementVisible(element, elementName);
        scrollTo(element);

        return element.getText();
    }

    public void scrollTo(@NonNull WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element
        );
    }

    public void click(WebElement element) {
        click(element, element.getTagName());
    }

    public String getText(WebElement element) {
        return getText(element, element.getTagName());
    }

    /**
     * Pauses using Thread sleep. Use only for debug code, not for test implementation.
     *
     * @param milliseconds int number of milliseconds to pause*
     */
    @Deprecated
    public void debugPause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
