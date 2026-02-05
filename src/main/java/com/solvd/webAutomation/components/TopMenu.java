package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;

public class TopMenu {
    private WebDriver driver;

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

    public HashMap<MenuItem, WebElement> navButtons = new HashMap<>();

    public TopMenu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        navButtons.put(MenuItem.HOME, homeButton);
        navButtons.put(MenuItem.CONTACT, contactButton);
        navButtons.put(MenuItem.ABOUTUS, aboutUsButton);
        navButtons.put(MenuItem.CART, cartButton);
        navButtons.put(MenuItem.LOGIN, logInButton);
        navButtons.put(MenuItem.SIGNUP, signUpButton);

    }

    public WebDriver getDriver() {
        return driver;
    }

    public void clickButton(MenuItem navItem) {
        navButtons.get(navItem).click();
    }

    public enum MenuItem {
        HOME(),
        CONTACT(),
        ABOUTUS(),
        CART(),
        LOGIN(),
        SIGNUP()
    }

}
