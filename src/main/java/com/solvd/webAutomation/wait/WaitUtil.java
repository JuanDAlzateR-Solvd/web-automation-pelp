package com.solvd.webAutomation.wait;

import com.solvd.webAutomation.config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Function;

public class WaitUtil {

    private static final Logger logger = LoggerFactory.getLogger(WaitUtil.class);

    private final WebDriver driver;
    private final int defaultTimeout;

    public WaitUtil(WebDriver driver) {
        this.driver = driver;
        this.defaultTimeout = Integer.parseInt(ConfigReader.get("wait_duration"));
    }

    private WebDriverWait buildWait(int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }

    private WebDriverWait buildWait() {
        return buildWait(defaultTimeout);
    }

    // ==========================
    // ELEMENT WAITS
    // ==========================

    public void waitForElementVisible(WebElement element, String elementName) {
        logger.debug("Waiting for element to be visible: {}", elementName);
        buildWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementClickable(WebElement element, String elementName) {
        logger.debug("Waiting for element to be clickable: {}", elementName);
        buildWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForInvisibility(WebElement element, String elementName) {
        logger.debug("Waiting for element to be invisible: {}", elementName);
        buildWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForInvisibilityOfElementLocated(By locator, String elementName) {
        logger.debug("Waiting for element to be invisible: {}", elementName);
        buildWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForPresenceOfElementLocated(By locator) {
        logger.debug("Waiting for presence of element located by {}", locator);
        return buildWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForNumberOfElementsToBeMoreThan(By locator, int number) {
        logger.debug("Waiting for number of elements located by {} to be more than {}", locator, number);
        buildWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, number));
    }

    public void waitForNumberOfElementsToBe(By locator, int number) {
        logger.debug("Waiting for number of elements located by {} to be {}", locator, number);
        buildWait().until(ExpectedConditions.numberOfElementsToBe(locator, number));
    }

    public void waitForStalenessOf(WebElement element, String elementName) {
        logger.debug("Waiting for staleness of element {}", elementName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout));
        wait.until(ExpectedConditions.stalenessOf(element));
    }

    // ==========================
    // ALERTS
    // ==========================

    public Alert waitForAlert() {
        logger.debug("Waiting for alert to be present");
        return buildWait().until(ExpectedConditions.alertIsPresent());
    }

    // ==========================
    // PAGE LOAD
    // ==========================

    public void waitForPageLoad() {
        logger.debug("Waiting for page load to complete");
        buildWait().until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    // ==========================
    // CONDITIONS
    // ==========================

    public <T> T waitUntil(Function<WebDriver, T> condition) {
        return buildWait().until(condition);
    }

    public <X, T> T waitUntilApply(Function<X, T> condition, X object) {
        return buildWait().until(driver -> condition.apply(object));
    }

    public void waitUntilTrue(Function<WebDriver, Boolean> condition) {
        buildWait().until(condition);
    }

}