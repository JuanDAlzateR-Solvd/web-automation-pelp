package com.solvd.webAutomation.pages.common;

import com.solvd.webAutomation.config.ConfigReader;
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
    public int waitDuration;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        waitDuration = Integer.parseInt(ConfigReader.get("wait_duration"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, waitDuration), this);

        logger.info("Page Created | Thread: {} | Driver: {}",
                Thread.currentThread().getId(),
                System.identityHashCode(driver)
        );

        waitUntilPageIsReady();
    }

    protected abstract WebElement getPageLoadedIndicator();

    public void click(WebElement element) {
        click(element, element.getTagName());
    }

    public void click(WebElement element, String elementName) {
        logger.info("Clicking on element [{}]", elementName);

        //better to use a new WebDriver Wait??
        wait.withTimeout(Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
        scrollTo(element);
        element.click();
    }

    public void click(By locator, String elementName) {
        logger.info("Clicking on element [{}]", elementName);
//        waitUntilModalIsGone();
        WebElement element = driver.findElement(locator);

        wait.withTimeout(Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
        scrollTo(element);
        element.click();
    }

    protected void type(WebElement element, String elementName, String text) {
        logger.info("Typing on element [{}]", elementName);

        wait.withTimeout(Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
        scrollTo(element);
        element.clear();
        element.sendKeys(text);

    }

    protected String getText(WebElement element) {
        return getText(element, element.getTagName());
    }

    protected String getText(WebElement element, String elementName) {
        logger.info("Getting text from element [{}]", elementName);

        wait.withTimeout(Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
        scrollTo(element);

        return element.getText();
    }

    protected boolean isVisible(WebElement element) {
        return isVisible(element, element.getTagName());
    }

    protected boolean isVisible(WebElement element, String elementName) {
        logger.info("Checking if visibility of element [{}]", elementName);
        try {
            waitUntilVisible(element);
            logger.info("Element [{}] is visible", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not visible", elementName);
            return false;
        }
    }

    public boolean isPageVisible() {
        WebElement element = getPageLoadedIndicator();
        return isVisible(element, this.getClass().getSimpleName() + " Indicator");
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

    protected boolean isClickable(WebElement element) {
        return isClickable(element, element.getTagName());
    }

    protected boolean isClickable(WebElement element, String elementName) {

        logger.info("Checking if clickable on element [{}]", elementName);
        try {
            waitUntilClickable(element);
            logger.info("Element [{}] is clickable", elementName);
            return true;
        } catch (TimeoutException e) {
            logger.warn("Element [{}] is not clickable", elementName);
            return false;
        }
    }

    public void waitUntilPageIsReady() {
        logger.info("Waiting for the page [{}] to load",this.getClass().getSimpleName());

        WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        pageWait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        pageWait.until(ExpectedConditions.invisibilityOfElementLocated(LOADER));
        pageWait.until(ExpectedConditions.visibilityOf(getPageLoadedIndicator()));
        logger.info("The page [{}] is ready",this.getClass().getSimpleName());
    }

    protected void scrollTo(@NonNull WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element
        );
    }

    protected void waitUntilModalIsGone(WebElement element) {
        try {
            logger.info("Waiting for modal to be invisible");
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            logger.info("Modal is not visible, continuing");
        }

    }

    public void waitUntilVisible(WebElement element) {

        wait.withTimeout(Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element));
        scrollTo(element);
    }

    protected void waitUntilClickable(WebElement element) {
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
