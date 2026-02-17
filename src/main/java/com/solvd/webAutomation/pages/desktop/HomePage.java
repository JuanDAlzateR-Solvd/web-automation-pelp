package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class HomePage extends AbstractPage {

    @FindBy(css = ".list-group a[onclick*='phone']")
    private WebElement phonesButton;
    @FindBy(css = ".list-group a[onclick*='notebook']")
    private WebElement laptopsButton;
    @FindBy(css = ".list-group a[onclick*='monitor']")
    private WebElement monitorsButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, WebElement> menuButtons = Map.of(
            MenuItem.PHONES, phonesButton,
            MenuItem.LAPTOPS, laptopsButton,
            MenuItem.MONITORS, monitorsButton
    );

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("#tbodyid .card-img-top.img-fluid");
    }

    public void clickButton(MenuItem item) {
        click(menuButtons.get(item), item.name);
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