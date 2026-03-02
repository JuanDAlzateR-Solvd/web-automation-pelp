package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class ContactModal extends AbstractPage {

    @FindBy(css = "#exampleModalLabel")
    private WebElement title;

    @FindBy(css = "#recipient-email")
    private WebElement emailInput;

    @FindBy(css = "#recipient-name")
    private WebElement nameInput;

    @FindBy(css = "#message-text")
    private WebElement messageInput;

    @FindBy(css = "#exampleModal button.btn.btn-primary")
    private WebElement sendButton;

    @FindBy(css = "#exampleModal button.btn.btn-secondary")
    private WebElement closeButton;

    private final Map<MenuItem, WebElement> menuItems;

    public ContactModal(WebDriver driver) {
        super(driver);
        menuItems = Map.of(
                MenuItem.EMAIL, emailInput,
                MenuItem.NAME, nameInput,
                MenuItem.MESSAGE, messageInput,
                MenuItem.CLOSE, closeButton,
                MenuItem.SEND, sendButton
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
        click(menuItems.get(item), item.getName());
    }

    public void type(MenuItem item, String text) {
        type(menuItems.get(item), item.getName(), text);
    }

    public boolean isModalVisible() {
        return title.isDisplayed();
    }

    public void acceptMessageAlert() {
        logger.info("accepting 'Thanks for message' Alert");
        Alert alert = waitService.waitForAlert();
        alert.accept();
    }

    public ContactModal submitContactForm(
            String email,
            String name,
            String message) {
        type(MenuItem.EMAIL, email);
        type(MenuItem.NAME, name);
        type(MenuItem.MESSAGE, message);
        click(MenuItem.SEND);
        return this;
    }

    public void close() {
        click(MenuItem.CLOSE);
        waitService.waitForInvisibility(closeButton, "Close Button");
    }

    public enum MenuItem {
        EMAIL("Input Email"),
        NAME("Input Name"),
        MESSAGE("Input Message"),
        CLOSE("Close Button"),
        SEND("Send Button");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
