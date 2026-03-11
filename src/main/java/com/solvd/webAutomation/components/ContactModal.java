package com.solvd.webAutomation.components;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactModal extends AbstractComponent {

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

    public ContactModal(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    protected WebElement getComponentLoadedIndicator() {
        return title;
    }

    public WebElement getTitle() {
        return title;
    }

    public boolean isModalVisible() {
        return title.isDisplayed();
    }

    public void acceptMessageAlert() {
        logger.info("accepting 'Thanks for message' Alert");
        Alert alert = waitUtil.waitForAlert();
        alert.accept();
    }

    public ContactModal fillAndSubmitForm(String email, String name, String message) {
        typeEmail(email);
        typeName(name);
        typeMessage(message);
        clickSend();
        return this;
    }

    public void typeEmail(String email) {
        type(emailInput, "ContactModal Email", email);
    }

    public void typeName(String name) {
        type(nameInput, "ContactModal Name", name);
    }

    public void typeMessage(String message) {
        type(messageInput, "ContactModal Message", message);
    }

    public void clickSend() {
        click(sendButton, "ContactModal SendButton");
    }

    public void clickClose() {
        click(closeButton, "ContactModal CloseButton");
        waitUtil.waitForInvisibility(closeButton, "Close Button");
    }

}
