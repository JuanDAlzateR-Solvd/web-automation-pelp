package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInModal extends AbstractComponent {

    @FindBy(id = "logInModalLabel")
    private WebElement title;

    @FindBy(css = "#logInModal button.btn.btn-primary")
    private WebElement logInButton;

    @FindBy(css = "#logInModal button.btn.btn-secondary")
    private WebElement closeButton;

    @FindBy(id = "loginusername")
    private WebElement usernameInput;

    @FindBy(id = "loginpassword")
    private WebElement passwordInput;

    public LogInModal(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    protected WebElement getComponentLoadedIndicator() {
        return title;
    }

    public WebElement getTitle() {
        return title;
    }

    public void clickLogIn() {
        click(logInButton, "Log In Button");
    }

    public void clickClose() {
        click(closeButton, "LogInModal Close Button");
        waitUtil.waitForInvisibility(closeButton, "Close Button");
    }

    public void typeUsername(String username) {
        type(usernameInput, "Input Username", username);
    }

    public void typePassword(String password) {
        type(passwordInput, "Input Password", password);
    }

    public boolean isModalVisible() {
        return title.isDisplayed();
    }

    public LogInModal logInWith(String username, String password) {
        typeUsername(username);
        typePassword(password);
        clickLogIn();
        return this;
    }

}
