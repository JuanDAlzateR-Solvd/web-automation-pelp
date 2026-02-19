package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

    @FindBy(css = "a[onclick*='phone']")
    private WebElement phonesButton;

    @FindBy(css = "a[onclick*='notebook']")
    private WebElement laptopsButton;

    @FindBy(css = "a[onclick*='monitor']")
    private WebElement monitorsButton;

    @FindBy(css = "#tbodyid .card-img-top.img-fluid")
    private WebElement imageIndicator;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected WebElement getPageLoadedIndicator() {
        return imageIndicator;
    }

    public void click(MenuItem item) {
        switch (item) {
            case PHONES -> click(phonesButton, item.name);
            case LAPTOPS -> click(laptopsButton, item.name);
            case MONITORS -> click(monitorsButton, item.name);
        }
    }

    public enum MenuItem {
        PHONES("Category Phones"),
        LAPTOPS("Category Laptops"),
        MONITORS("Category Monitors");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
}