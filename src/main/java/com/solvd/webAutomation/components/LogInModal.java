package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Map;

public class LogInModal extends AbstractPage {

    @FindBy(id = "logInModalLabel")
    private WebElement title;
    @FindBy(css = "#logInModal button.btn.btn-primary")
    private WebElement logInButton;
    @FindBy(css = "#logInModal button.btn.btn-secondary")
    private WebElement closeButton;
    @FindBy(id="loginusername")
    private WebElement usernameInput;
    @FindBy(id="loginpassword")
    private WebElement passwordInput;

    public LogInModal(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, WebElement> menuInputs = Map.of(
            MenuItem.USERNAME, usernameInput,
            MenuItem.PASSWORD, passwordInput
    );

    private final Map<MenuItem, WebElement> menuButtons = Map.of(
            MenuItem.CLOSE, closeButton,
            MenuItem.LOG_IN, logInButton
    );

    @Override
    protected By getPageLoadedIndicator() {
        return By.id("logInModalLabel");
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

    public boolean isLogInModalVisible() {
        return title.isDisplayed();
    }

    public void acceptWrongPasswordAlert() {
        logger.info("accepting 'Wrong password' Alert");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public enum MenuItem {
        USERNAME("Input Username"),
        PASSWORD("Input Password"),
        CLOSE("Close Button"),
        LOG_IN("Log In Button");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
