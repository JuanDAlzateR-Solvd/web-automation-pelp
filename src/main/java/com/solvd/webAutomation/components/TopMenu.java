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

    @FindBy(css = "#exampleModal .close")
    private WebElement contactCloseButton;
    @FindBy(css = "#videoModal .close")
    private WebElement aboutUsCloseButton;
    @FindBy(css = "#logInModal .close")
    private WebElement logInCloseButton;
    @FindBy(css = "#signInModal .close")
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

    public void clickButton(MenuItem item) {
        click(menuButtons.get(item), item.name);
    }

//    public void clickMenuItem(MenuItem item) {
//        By by = By.cssSelector(item.cssSelector);
//        click(by, item.name);
//    }

    public void clickCloseButton(MenuItem item) {
        if (closeButtons.containsKey(item)) {
            click(closeButtons.get(item),
                    item.name.substring(4) + " Close Button");
        }
    }

    public boolean isVisible(MenuItem item) {
        boolean result = false;

        if (closeButtons.containsKey(item)) {
            result = isVisible(closeButtons.get(item));
        } else {
            switch (item) {
                case HOME -> result = driver.getCurrentUrl().contains("index.html");
                case CART -> result = driver.getCurrentUrl().contains("cart.html");
            }
        }

        return result;
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
