package com.solvd.webAutomation.components;

import com.solvd.webAutomation.actions.NavActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;

public class TopMenu {
    private WebDriver driver;
    private NavActions navActions;

    @FindBy(css = "a[class='nav-link'][href='index.html']")
    private WebElement homeButton;
    @FindBy(css = "a[class='nav-link'][data-target='#exampleModal']")
    private WebElement contactButton;
    @FindBy(css = "a[class='nav-link'][data-target='#videoModal']")
    private WebElement aboutUsButton;
    @FindBy(css = "a[class='nav-link'][id='cartur']")
    private WebElement cartButton;
    @FindBy(css = "a[class='nav-link'][id='login2']]")
    private WebElement logInButton;
    @FindBy(css = "a[class='nav-link'][id='signin2']]")
    private WebElement signUpButton;

    public TopMenu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public WebDriver getDriver() {
        return driver;
    }

    public void clickButton(MenuItem item) {
        switch (item) {
            case HOME -> navActions.click(homeButton);
            case CONTACT -> navActions.click(contactButton);
            case ABOUT_US ->  navActions.click(aboutUsButton);
            case CART ->  navActions.click(cartButton);
            case LOG_IN ->  navActions.click(logInButton);
            case SIGN_UP ->  navActions.click(signUpButton);
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
