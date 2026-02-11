package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public TopMenu(WebDriver driver) {
        super(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void clickButton(MenuItem item) {
        switch (item) {
            case HOME -> click(homeButton, item.name);
            case CONTACT -> click(contactButton, item.name);
            case ABOUT_US -> click(aboutUsButton, item.name);
            case CART -> click(cartButton, item.name);
            case LOG_IN -> click(logInButton, item.name);
            case SIGN_UP -> click(signUpButton, item.name);
        }
    }

    public void clickMenuItem(MenuItem item) {
        By by = By.cssSelector(item.cssSelector);
        click(by, item.name);
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
