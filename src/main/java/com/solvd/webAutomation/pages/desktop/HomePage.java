package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.components.Footer;
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

    @FindBy(css = "#tbodyid .card-img-top.img-fluid")
    private List<WebElement> imageIndicator;

    @FindBy(css = "#contcont")
    private WebElement productGridContainer;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    private final Map<Category, WebElement> menuButtons;

    public HomePage(WebDriver driver) {
        super(driver);
        menuButtons = Map.of(
                Category.PHONES, phonesButton,
                Category.LAPTOPS, laptopsButton,
                Category.MONITORS, monitorsButton
        );
    }

    @Override
    public WebElement getPageLoadedIndicator() {
        return imageIndicator.get(0);
    }

    public void click(Category item) {
        click(menuButtons.get(item), item.getName());
    }

    public enum Category {
        PHONES("Category Phones"),
        LAPTOPS("Category Laptops"),
        MONITORS("Category Monitors");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    //Test flow methods

    public ProductGrid getProductGrid() {
        return new ProductGrid(driver, productGridContainer);
    }

    public TopMenu getTopMenu() {
        return new TopMenu(driver);
    }

    public Footer getFooter() {
        return new Footer(driver);
    }

    public ProductGrid selectCategory(Category item) {
        click(item);
        return new ProductGrid(driver, productGridContainer);
    }

}