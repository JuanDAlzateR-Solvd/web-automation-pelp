package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LogInModal extends AbstractPage {

    private static final String inputUsernameCssSelector = "input[id='loginusername']";
    private static final String inputPasswordCssSelector = "input[id='loginpassword']";


    @FindBy(css = "h5[id='logInModalLabel']")
    private WebElement title;
    @FindBy(css = "button[onclick='logIn()']")
    private WebElement logInButton;
    @FindBy(css = "button[class='btn btn-secondary']")
    private WebElement closeButton;
    @FindBy(css = inputUsernameCssSelector)
    private WebElement inputEmail;
    @FindBy(css = inputPasswordCssSelector)
    private WebElement inputName;


    public LogInModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("h5[id='logInModalLabel']");
    }

    public WebElement getTitle() {
        return title;
    }

    public void clickLogInButton() {
        click(logInButton, "Log In Button");
    }

    public void clickCloseButton() {
        click(closeButton, "Close Button");
    }

    public void click(MenuItem item) {
        By by = By.cssSelector(item.cssSelector);
        click(by, item.name);
    }

    public void type(MenuItem item, String text) {
        By by = By.cssSelector(item.cssSelector);
        type(by, item.name, text);
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
        USERNAME("Input Username", inputUsernameCssSelector),
        PASSWORD("Input Password", inputPasswordCssSelector);


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
