package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.solvd.webAutomation.pages.desktop.HomePage.MenuItem.MONITORS;

public class ContactModal extends AbstractPage {

    private static final String inputEmailCssSelector = "input[id='recipient-email']";
    private static final String inputNameCssSelector = "input[id='recipient-name']";
    private static final String inputMessageCssSelector = "textarea[id='message-text']";

    @FindBy(css = "h5[id='exampleModalLabel']")
    private WebElement title;
    @FindBy(css = inputEmailCssSelector)
    private WebElement inputEmail;
    @FindBy(css = inputNameCssSelector)
    private WebElement inputName;
    @FindBy(css = inputMessageCssSelector)
    private WebElement inputMessage;

    public ContactModal(WebDriver driver) {
        super(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void click(MenuItem item) {
        By by = By.cssSelector(item.cssSelector);
        click(by, item.name);
    }

    public void type(MenuItem item, String text) {
        By by = By.cssSelector(item.cssSelector);
        type(by, item.name,text);
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
