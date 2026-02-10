package com.solvd.webAutomation.actions;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Objects;

public class NavActions {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;
    private WebDriverWait wait;
    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public NavActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public NavActions(WebDriver driver,WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void click(@NonNull WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        scrollTo(element);
        element.click();
    }

    public void click(By locator) {

        wait.until(driver -> {
            try {
                waitUntilModalIsGone();
                WebElement element = driver.findElement(locator);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                scrollTo(element);
                element.click();
                return true;
            } catch (StaleElementReferenceException e) {
                return false;
            }

        });
    }

    public void type(@NonNull WebElement element, @NonNull String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
    }

    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public void waitVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void pause(int milliseconds) {
//        WebDriverWait waitTime=new WebDriverWait(driver, Duration.ofSeconds(milliseconds/1000));
//        waitTime.until(d -> true);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitUntilPageIsReady() {
        WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        pageWait.until(driver ->
                Objects.equals(((JavascriptExecutor) driver)
                        .executeScript("return document.readyState"), "complete")
        );

        pageWait.until(ExpectedConditions.invisibilityOfElementLocated(LOADER));
    }

    public void scrollTo(@NonNull WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element
        );
    }

    private void waitUntilModalIsGone() {
        By modal = By.cssSelector("div[id='exampleModal']");
        try {
            logger.info("Waiting for modal to be invisible");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));
        } catch (TimeoutException e) {
            logger.info("Modal is not visible, continuing");
        }


    }
}
