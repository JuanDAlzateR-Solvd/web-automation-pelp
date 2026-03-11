package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopMenu extends AbstractComponent {

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

    @FindBy(css = "a[id='nava'] img")
    private WebElement imageIndicator;

    public TopMenu(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    protected WebElement getComponentLoadedIndicator() {
        return imageIndicator;
    }

    public void clickCart() {
        click(cartButton, "Top Menu Cart");
    }

    public void clickHome() {
        click(homeButton, "Top Menu Home");
    }

    public void clickAboutUs() {
        click(aboutUsButton, "Top Menu About Us");
    }

    public void clickSignUp() {
        click(signUpButton, "Top Menu Sign Up");
    }

    public void clickContact() {
        click(contactButton, "Top Menu Contact");
    }

    public void clickLogIn() {
        click(logInButton, "Top Menu Log In");
    }

}
