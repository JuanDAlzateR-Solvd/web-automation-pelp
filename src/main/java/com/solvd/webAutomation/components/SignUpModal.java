package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class SignUpModal extends AbstractPage {

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

    public SignUpModal(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, WebElement> menuInputs = Map.of(
            MenuItem.USERNAME, usernameInput,
            MenuItem.PASSWORD, passwordInput
    );

    private final Map<MenuItem, WebElement> menuButtons = Map.of(
            MenuItem.CLOSE, closeButton,
            MenuItem.SIGN_IN, signInButton
    );

    @Override
    protected WebElement getPageLoadedIndicator() {
        return title;
    }

    public WebElement getTitle() {
        return title;
    }

    public void click(MenuItem item) {
        WebElement button = menuButtons.get(item);
        click(button, item.name);
    }

    public void type(MenuItem item, String text) {
        WebElement element = menuInputs.get(item);
        type(element, item.name, text);
    }

    public boolean isModalVisible() {
        return title.isDisplayed();
    }

    public void acceptWrongPasswordAlert() {
        logger.info("accepting 'Wrong password' Alert");
        Alert alert = waitService.waitForAlert();
        alert.accept();
    }

    public enum MenuItem {
        USERNAME("Input Username"),
        PASSWORD("Input Password"),
        CLOSE("Close Button"),
        SIGN_IN("Sign In Button");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
