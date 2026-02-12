package com.solvd.webAutomation.components;

import com.solvd.webAutomation.actions.NavActions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TopMenu {
    private WebDriver driver;
    private WebDriverWait wait;
    private NavActions navActions;

    @FindBy(css = "a[class='nav-link'][href='index.html']")
    private WebElement homeButton;
    @FindBy(css = "a[class='nav-link'][data-target='#exampleModal']")
    private WebElement contactButton;
    @FindBy(css = "a[class='nav-link'][data-target='#videoModal']")
    private WebElement aboutUsButton;
    @FindBy(css = "a[class='nav-link'][id='cartur']")
    private WebElement cartButton;
    @FindBy(css = "a[class='nav-link'][id='login2']")
    private WebElement logInButton;
    @FindBy(css = "a[class='nav-link'][id='signin2']")
    private WebElement signUpButton;

    @FindBy(css = "div[id='exampleModal'] button[class='close']")
    private WebElement contactCloseButton;
    @FindBy(css = "div[id='videoModal'] button[class='close']")
    private WebElement aboutUsCloseButton;
    @FindBy(css = "div[id='logInModal'] button[class='close']")
    private WebElement logInCloseButton;
    @FindBy(css = "div[id='signInModal'] button[class='close']")
    private WebElement signUpCloseButton;


    public TopMenu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.navActions = new NavActions(driver);
    }

    public void clickButton(MenuItem item) {
        switch (item) {
            case HOME -> navActions.click(homeButton);
            case CONTACT -> navActions.click(contactButton);
            case ABOUT_US -> navActions.click(aboutUsButton);
            case CART -> navActions.click(cartButton);
            case LOG_IN -> navActions.click(logInButton);
            case SIGN_UP -> navActions.click(signUpButton);
        }
    }

    public void clickCloseButton(MenuItem item) {

        switch (item) {
            case CONTACT -> navActions.click(contactCloseButton);
            case ABOUT_US -> navActions.click(aboutUsCloseButton);
            case LOG_IN -> navActions.click(logInCloseButton);
            case SIGN_UP -> navActions.click(signUpCloseButton);
        }
    }

    public boolean isVisible(MenuItem item) {
        Boolean result = false;
        switch (item) {
            case HOME -> result = driver.getCurrentUrl().contains("index.html");
            case CONTACT -> result = isVisible(contactCloseButton);
            case ABOUT_US -> result = isVisible(aboutUsCloseButton);
            case CART -> result = driver.getCurrentUrl().contains("cart.html");
            case LOG_IN -> result = isVisible(logInCloseButton);
            case SIGN_UP -> result = isVisible(signUpCloseButton);
        }
        return result;
    }

    public boolean isVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public enum MenuItem {
        HOME(),
        CONTACT(),
        ABOUT_US(),
        CART(),
        LOG_IN(),
        SIGN_UP()
    }

}
