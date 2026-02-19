package com.solvd.webAutomation.pages.common;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class AbstractPage {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);

        logger.info("Page Created | Thread: {} | Driver: {}",
                Thread.currentThread().getId(),
                System.identityHashCode(driver)
        );
    }

    protected abstract By getPageLoadedIndicator();

    public void click(WebElement element) {
        click(element, element.getTagName());
    }

    public void click(WebElement element, String elementName) {
        logger.info("Clicking on element [{}]", elementName);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        scrollTo(element);
        element.click();
    }

    public void click(By locator, String elementName) {
        logger.info("Clicking on element [{}]", elementName);
//        waitUntilModalIsGone();
        WebElement element = driver.findElement(locator);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        scrollTo(element);
        element.click();
    }

    public void type(By locator, String elementName, String text) {
        logger.info("Typing into element [{}] value [{}]", elementName, text);
        WebElement element = driver.findElement(locator);

        wait.until(driver -> {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                scrollTo(element);
                element.clear();
                element.sendKeys(text);
                return true;
            } catch (StaleElementReferenceException e) {
                logger.warn("Stale element while typing [{}], retrying...", elementName);
                return false;
            }
        });
    }

    protected void type(WebElement element, String text) {
        logger.info("Typing on element [{}]", element.getTagName());
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        return getText(element, element.getTagName());
    }

    protected String getText(WebElement element, String elementName) {
        logger.info("Getting text from element [{}]", elementName);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    protected Boolean isVisible(WebElement element) {
        return isVisible(element, element.getTagName());
    }

    protected Boolean isVisible(WebElement element, String elementName) {
        logger.info("Checking if visibility of element [{}]", elementName);
        try {
            waitVisible(element);
            logger.info("Element [{}] is visible", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not visible", elementName);
            return false;
        }
    }

    protected Boolean isInViewport(WebElement element, String elementName) {
        logger.info("Checking if element is in Viewport [{}]", elementName);
        Boolean isInViewport = (Boolean) ((JavascriptExecutor) driver)
                .executeScript(
                        "var elem = arguments[0],                 " +
                                "  box = elem.getBoundingClientRect();    " +
                                "return (                                 " +
                                "  box.top >= 0 &&                        " +
                                "  box.left >= 0 &&                       " +
                                "  box.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                                "  box.right <= (window.innerWidth || document.documentElement.clientWidth)       " +
                                ");",
                        element);
        String aux = Boolean.TRUE.equals(isInViewport) ? "" : "not";
        logger.info("Element [{}] is " + aux + " in Viewport", elementName);
        return isInViewport;
    }

    protected Boolean isClickable(WebElement element) {
        return isClickable(element, element.getTagName());
    }

    protected Boolean isClickable(WebElement element, String elementName) {

        logger.info("Checking if clickable on element [{}]", elementName);
        try {
            waitClickable(element);
            logger.info("Element [{}] is clickable", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not clickable", elementName);
            return false;
        }
    }

    public void waitUntilPageIsReady() {
        logger.info("Waiting for the page to load");
        WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        pageWait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        pageWait.until(ExpectedConditions.invisibilityOfElementLocated(LOADER));
        pageWait.until(ExpectedConditions.visibilityOfElementLocated(getPageLoadedIndicator()));
        logger.info("The page is ready");
    }

    protected void scrollTo(@NonNull WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element
        );
    }

    protected void waitUntilModalIsGone() {
        By modal = By.cssSelector("div[id='exampleModal']");
        try {
            logger.info("Waiting for modal to be invisible");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));
        } catch (TimeoutException e) {
            logger.info("Modal is not visible, continuing");
        }

    }

    public void waitVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Pauses using Thread sleep. Use only for debug code, not for test implementation.
     *
     * @param milliseconds int number of milliseconds to pause     *
     */
    public void debugPause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAlertPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
