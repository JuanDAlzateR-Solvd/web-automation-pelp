package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class LogInModal extends AbstractPage {

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

    private final Map<MenuItem, WebElement> menuInputs ;

    private final Map<MenuItem, WebElement> menuButtons ;

    public LogInModal(WebDriver driver) {
        super(driver);
        menuInputs = Map.of(
                MenuItem.USERNAME, usernameInput,
                MenuItem.PASSWORD, passwordInput
        );
        menuButtons = Map.of(
                MenuItem.CLOSE, closeButton,
                MenuItem.LOG_IN, logInButton
        );
    }

    @Override
    protected WebElement getPageLoadedIndicator() {
        return title;
    }

    public WebElement getTitle() {
        return title;
    }

    public void click(MenuItem item) {
        WebElement button = menuButtons.get(item);
        click(button, item.getName());
    }

    public void type(MenuItem item, String text) {
        WebElement element = menuInputs.get(item);
        type(element, item.getName(), text);
    }

    public boolean isModalVisible() {
        return title.isDisplayed();
    }

    public LogInModal logInWith(
            String username,
            String password) {
        type(MenuItem.USERNAME, username);
        type(MenuItem.PASSWORD, password);
        click(MenuItem.LOG_IN);
        return this;
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
