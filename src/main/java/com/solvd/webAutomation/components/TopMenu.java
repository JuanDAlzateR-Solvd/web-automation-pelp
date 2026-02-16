package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class TopMenu extends AbstractPage {

    private static final String homeButtonCssSelector = "a[class='nav-link'][href='index.html']";
    private static final String contactButtonCssSelector = "a[class='nav-link'][data-target='#exampleModal']";
    private static final String aboutUsButtonCssSelector = "a[class='nav-link'][data-target='#videoModal']";
    private static final String cartButtonCssSelector = "a[class='nav-link'][id='cartur']";
    private static final String logInButtonCssSelector = "a[class='nav-link'][id='login2']";
    private static final String signUpButtonCssSelector = "a[class='nav-link'][id='signin2']";

    @FindBy(css = homeButtonCssSelector)
    private WebElement homeButton;
    @FindBy(css = contactButtonCssSelector)
    private WebElement contactButton;
    @FindBy(css = aboutUsButtonCssSelector)
    private WebElement aboutUsButton;
    @FindBy(css = cartButtonCssSelector)
    private WebElement cartButton;
    @FindBy(css = logInButtonCssSelector)
    private WebElement logInButton;
    @FindBy(css = signUpButtonCssSelector)
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


    public void clickButton(MenuItem item) {
        click(menuButtons.get(item), item.name);
    }

    public void clickMenuItem(MenuItem item) {
        By by = By.cssSelector(item.cssSelector);
        click(by, item.name);
    }

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
        }else{
            switch (item) {
                case HOME -> result = driver.getCurrentUrl().contains("index.html");
                case CART -> result = driver.getCurrentUrl().contains("cart.html");
            }
        }

        return result;
    }

    public enum MenuItem {
        HOME("Top Menu Home", homeButtonCssSelector),
        CONTACT("Top Menu Contact", contactButtonCssSelector),
        ABOUT_US("Top Menu About Us", aboutUsButtonCssSelector),
        CART("Top Menu Cart", cartButtonCssSelector),
        LOG_IN("Top Menu Log In", logInButtonCssSelector),
        SIGN_UP("Top Menu Sign Up", signUpButtonCssSelector);

        private final String name;
        private final String cssSelector;

        MenuItem(String name, String cssSelector) {
            this.name = name;
            this.cssSelector = cssSelector;
        }

        public String getName() {
            return name;
        }

        public String getCssSelector() {
            return cssSelector;
        }

    }

}
