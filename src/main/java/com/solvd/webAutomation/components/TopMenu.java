package com.solvd.webAutomation.components;

import com.solvd.webAutomation.actions.NavActions;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class TopMenu extends AbstractPage {

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
    }

}
