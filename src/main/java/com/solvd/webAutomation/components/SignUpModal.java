package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpModal extends AbstractComponent {

    @FindBy(id = "signInModalLabel")
    private WebElement title;

    @FindBy(css = "#signInModal button.btn.btn-primary")
    private WebElement signInButton;

    @FindBy(css = "#signInModal button.btn.btn-secondary")
    private WebElement closeButton;

    @FindBy(id = "sign-username")
    private WebElement usernameInput;

    @FindBy(id = "sign-password")
    private WebElement passwordInput;

    public SignUpModal(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    protected WebElement getComponentLoadedIndicator() {
        return title;
    }

    public WebElement getTitle() {
        return title;
    }

    public void clickSignIn() {
        click(signInButton, "Sign In Button");
    }

    public void clickClose() {
        click(closeButton, "Close Button");
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

}
