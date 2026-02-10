package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

    private static final String phonesCssSelector = "a[onclick*='phone']";
    private static final String laptopsCssSelector = "a[onclick*='notebook']";
    private static final String monitorsCssSelector = "a[onclick*='monitor']";

    @FindBy(css = phonesCssSelector)
    private WebElement phonesButton;
    @FindBy(css = laptopsCssSelector)
    private WebElement laptopsButton;
    @FindBy(css = monitorsCssSelector)
    private WebElement monitorsButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void clickButton(MenuItem item) {
        switch (item) {
            case PHONES -> click(phonesButton, item.name);
            case LAPTOPS -> click(laptopsButton, item.name);
            case MONITORS -> click(monitorsButton, item.name);

        }
    }

    public void clickBy(MenuItem item) {
        By by = By.cssSelector(item.cssSelector);
        click(by, item.name);
    }

    public enum MenuItem {
        PHONES("Category Phones", phonesCssSelector),
        LAPTOPS("Category Laptops", laptopsCssSelector),
        MONITORS("Category Monitors", monitorsCssSelector);

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
