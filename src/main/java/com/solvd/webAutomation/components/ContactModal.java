package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactModal extends AbstractPage {

    private static final String inputEmailCssSelector = "input[id='recipient-email']";
    private static final String inputNameCssSelector = "input[id='recipient-name']";
    private static final String inputMessageCssSelector = "textarea[id='message-text']";

    @FindBy(css = "h5[id='exampleModalLabel']")
    private WebElement title;
    @FindBy(css = "button[onclick='send()']")
    private WebElement sendButton;
    @FindBy(css = inputEmailCssSelector)
    private WebElement inputEmail;
    @FindBy(css = inputNameCssSelector)
    private WebElement inputName;
    @FindBy(css = inputMessageCssSelector)
    private WebElement inputMessage;

    public ContactModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("h5[id='exampleModalLabel']");
    }

    public WebElement getTitle() {
        return title;
    }

    public void clickSendButton() {
        click(sendButton, "Send Button");
    }

    public void click(MenuItem item) {
        By by = By.cssSelector(item.cssSelector);
        click(by, item.name);
    }

    public void type(MenuItem item, String text) {
        By by = By.cssSelector(item.cssSelector);
        type(by, item.name, text);
    }

    public boolean isContactModalVisible() {
        return title.isDisplayed();
    }

    public void acceptMessageAlert() {
        logger.info("accepting 'Thanks for message' Alert");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }


    public enum MenuItem {
        EMAIL("Input Email", inputEmailCssSelector),
        NAME("Input Name", inputNameCssSelector),
        MESSAGE("Input Message", inputMessageCssSelector);

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
