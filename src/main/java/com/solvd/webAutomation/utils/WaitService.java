package com.solvd.webAutomation.utils;

import com.solvd.webAutomation.config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WaitService {

    private static final Logger logger = LoggerFactory.getLogger(WaitService.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final int waitDuration;

    public WaitService(WebDriver driver) {
        this.driver = driver;
        this.waitDuration = Integer.parseInt(ConfigReader.get("wait_duration"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
    }

    public void waitForElementVisible(WebElement element) {
        waitForElementVisible(element, waitDuration);
    }

    public void waitForElementVisible(WebElement element, int timeoutInSeconds) {
        logger.info("Waiting for element to be visible: {} with timeout {}s", element, timeoutInSeconds);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementClickable(WebElement element) {
        waitForElementClickable(element, waitDuration);
    }

    public void waitForElementClickable(WebElement element, int timeoutInSeconds) {
        logger.info("Waiting for element to be clickable: {} with timeout {}s", element, timeoutInSeconds);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForInvisibility(WebElement element) {
        waitForInvisibility(element, waitDuration);
    }

    public void waitForInvisibility(WebElement element, int timeoutInSeconds) {
        logger.info("Waiting for element to be invisible: {} with timeout {}s", element, timeoutInSeconds);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForInvisibilityOfElementLocated(By locator) {
        logger.info("Waiting for element located by {} to be invisible", locator);
        wait.withTimeout(Duration.ofSeconds(waitDuration))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForPageLoad() {
        logger.info("Waiting for page load to complete");
        wait.withTimeout(Duration.ofSeconds(waitDuration))
                .until(driver -> ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    public void waitForAlert() {
        waitForAlert(waitDuration);
    }

    public void waitForAlert(int timeoutInSeconds) {
        logger.info("Waiting for alert to be present with timeout {}s", timeoutInSeconds);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.alertIsPresent());
    }

    public Alert getAlert() {
        return getAlert(waitDuration);
    }

    public Alert getAlert(int timeoutInSeconds) {
        logger.info("Getting alert with timeout {}s", timeoutInSeconds);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.alertIsPresent());
    }

    public void waitForNumberOfElementsToBeMoreThan(By locator, int number) {
        waitForNumberOfElementsToBeMoreThan(locator, number, waitDuration);
    }

    public void waitForNumberOfElementsToBeMoreThan(By locator, int number, int timeoutInSeconds) {
        logger.info("Waiting for number of elements located by {} to be more than {} with timeout {}s", locator, number, timeoutInSeconds);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, number));
    }

    public void waitForNumberOfElementsToBe(By locator, int number) {
        waitForNumberOfElementsToBe(locator, number, waitDuration);
    }

    public void waitForNumberOfElementsToBe(By locator, int number, int timeoutInSeconds) {
        logger.info("Waiting for number of elements located by {} to be {} with timeout {}s", locator, number, timeoutInSeconds);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.numberOfElementsToBe(locator, number));
    }

    public WebElement waitForPresenceOfElementLocated(By locator) {
        return waitForPresenceOfElementLocated(locator, waitDuration);
    }

    public WebElement waitForPresenceOfElementLocated(By locator, int timeoutInSeconds) {
        logger.info("Waiting for presence of element located by {} with timeout {}s", locator, timeoutInSeconds);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
