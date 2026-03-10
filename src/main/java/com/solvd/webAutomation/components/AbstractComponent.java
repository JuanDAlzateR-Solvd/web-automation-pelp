package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractUIObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public abstract class AbstractComponent extends AbstractUIObject {

    protected final WebElement root;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public AbstractComponent(WebDriver driver, WebElement root) {
        super(driver);
        this.root = root;

        PageFactory.initElements(
                new DefaultElementLocatorFactory(root),
                this
        );

        logger.info("Page Created | Thread: {} | Driver: {}",
                Thread.currentThread().getId(),
                System.identityHashCode(driver)
        );
    }

    protected abstract WebElement getComponentLoadedIndicator();

    public WebElement getRoot() {
        return root;
    }

    public void waitUntilComponentIsReady() {
        String className = this.getClass().getSimpleName();
        logger.info("Waiting for the component [{}] to be ready", className);

//        waitUtil.waitForPageLoad();
        waitUtil.waitForInvisibilityOfElementLocated(LOADER, "Component Loader");
        waitUtil.waitForElementVisible(getComponentLoadedIndicator(), className + " Indicator");

        logger.info("The component [{}] is ready", this.getClass().getSimpleName());
    }

    public boolean isAlertPresent() {
        try {
            waitUtil.waitForAlert();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void acceptWrongPasswordAlert() {
        logger.info("accepting 'Wrong password' Alert");
        Alert alert = waitUtil.waitForAlert();
        alert.accept();
    }

}
