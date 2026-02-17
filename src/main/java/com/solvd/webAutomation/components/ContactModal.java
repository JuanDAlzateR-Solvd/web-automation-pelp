package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Map;

public class ContactModal extends AbstractPage {

    @FindBy(css = "#exampleModalLabel")
    private WebElement title;
    @FindBy(css = "#exampleModal button.btn.btn-primary")
    private WebElement sendButton;
    @FindBy(css = "#recipient-email")
    private WebElement inputEmail;
    @FindBy(css = "#recipient-name")
    private WebElement inputName;
    @FindBy(css = "#message-text")
    private WebElement inputMessage;

    public ContactModal(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, WebElement> menuInputs = Map.of(
            MenuItem.EMAIL, inputEmail,
            MenuItem.NAME, inputName,
            MenuItem.MESSAGE, inputMessage
    );

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
        WebElement element = menuInputs.get(item);
        click(element, item.getName());
    }

    public void type(MenuItem item, String text) {
        WebElement element = menuInputs.get(item);
        type(element, item.getName(), text);
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
        EMAIL("Input Email"),
        NAME("Input Name"),
        MESSAGE("Input Message");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
}
