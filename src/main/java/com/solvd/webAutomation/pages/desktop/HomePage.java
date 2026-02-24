package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;


public class HomePage extends AbstractPage {

    @FindBy(css = ".list-group a[onclick*='phone']")
    private WebElement phonesButton;

    @FindBy(css = ".list-group a[onclick*='notebook']")
    private WebElement laptopsButton;

    @FindBy(css = ".list-group a[onclick*='monitor']")
    private WebElement monitorsButton;

    @FindBy(css = "#tbodyid .card-img-top.img-fluid")// "#tbodyid .card-img-top.img-fluid"
    private List<WebElement> imageIndicator;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, WebElement> menuButtons = Map.of(
            MenuItem.PHONES, phonesButton,
            MenuItem.LAPTOPS, laptopsButton,
            MenuItem.MONITORS, monitorsButton
    );

    @Override
    public WebElement getPageLoadedIndicator() {
        return imageIndicator.get(0);
    }

    public void click(MenuItem item) {
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

    //Test flow methods

    public ProductGrid getProductGrid() {
        return new ProductGrid(driver);
    }

    public TopMenu getTopMenu() {
        return new TopMenu(driver);
    }

    public ProductGrid selectCategory(MenuItem item) {
        click(item);
        return new ProductGrid(driver);
    }

}