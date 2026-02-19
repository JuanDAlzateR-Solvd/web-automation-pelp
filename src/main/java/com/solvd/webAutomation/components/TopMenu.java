package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class TopMenu extends AbstractPage {

    @FindBy(css = "a.nav-link[href='index.html']")
    private WebElement homeButton;

    @FindBy(css = "a.nav-link[data-target='#exampleModal']")
    private WebElement contactButton;

    @FindBy(css = "a.nav-link[data-target='#videoModal']")
    private WebElement aboutUsButton;

    @FindBy(css = "a.nav-link[href='cart.html']")
    private WebElement cartButton;

    @FindBy(css = "a.nav-link#login2")
    private WebElement logInButton;

    @FindBy(css = "a.nav-link#signin2")
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
        super(driver);
    }

    private final Map<MenuItem, WebElement> menuButtons = Map.of(
            MenuItem.HOME, homeButton,
            MenuItem.CONTACT, contactButton,
            MenuItem.ABOUT_US, aboutUsButton,
            MenuItem.CART, cartButton,
            MenuItem.LOG_IN, logInButton,
            MenuItem.SIGN_UP, signUpButton
    );

    private final Map<MenuItem, WebElement> closeButtons = Map.of(
            MenuItem.CONTACT, contactCloseButton,
            MenuItem.ABOUT_US, aboutUsCloseButton,
            MenuItem.LOG_IN, logInCloseButton,
            MenuItem.SIGN_UP, signUpCloseButton
    );

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("a[id='nava'] img");
    }

    public void click(MenuItem item) {
        click(menuButtons.get(item), item.getName());
    }

    public void clickClose(MenuItem item) {
        WebElement closeButton = closeButtons.get(item);
        if (closeButton != null) {
            click(closeButton, item.getName().substring(4) + " Close");
        }
    }

    public boolean isVisible(MenuItem item) {
        boolean result = false;
        try {
            result = isModalVisible(item);
        } catch (IllegalArgumentException e) {
            try {
                result = isPageOpened(item);
            } catch (IllegalArgumentException e2) {
                logger.error("Menu item has no modal and does not represent a page: " + item.getName());
            }
        }
        return result;
    }

    public boolean isModalVisible(MenuItem item) {
        WebElement closeButton = closeButtons.get(item);
        if (closeButton == null) {
            throw new IllegalArgumentException("Menu item has no modal: " + item);
        }
        return isVisible(closeButton);
    }

    public boolean isPageOpened(MenuItem item) {
        String url = driver.getCurrentUrl();

        return switch (item) {
            case HOME -> url.contains("index.html");
            case CART -> url.contains("cart.html");
            default -> throw new IllegalArgumentException(
                    "Menu item does not represent a page: " + item);
        };
    }

    public enum MenuItem {
        HOME("Top Menu Home"),
        CONTACT("Top Menu Contact"),
        ABOUT_US("Top Menu About Us"),
        CART("Top Menu Cart"),
        LOG_IN("Top Menu Log In"),
        SIGN_UP("Top Menu Sign Up");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
