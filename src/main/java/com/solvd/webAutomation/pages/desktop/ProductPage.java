package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.flows.Navigation;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends AbstractPage {

    @FindBy(css = ".item.active")
    private WebElement image;

    @FindBy(css = "#tbodyid .name")
    private WebElement title;

    @FindBy(css = "#tbodyid .price-container")
    private WebElement price;

    @FindBy(css = "#tbodyid #more-information")
    private WebElement description;

    @FindBy(css = "a.btn.btn-success.btn-lg")
    private WebElement addToCartButton;

    @FindBy(css = "#myCarousel-2")
    private WebElement imageLocator;

    @FindBy(css = ".navbar.navbar-toggleable-md.bg-inverse")
    private WebElement topMenuContainer;

    @FindBy(css = "html[lang]")
    private WebElement navigationRoot;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected WebElement getPageLoadedIndicator() {
        return imageLocator;
    }

    public boolean isVisible(InfoItem item) {
        return switch (item) {
            case IMAGE -> isVisible(image, item.getName());
            case TITLE -> isVisible(title, item.getName());
            case PRICE -> isVisible(price, item.getName());
            case DESCRIPTION -> isVisible(description, item.getName());
        };
    }

    public void clickAddToCartButton() {
        click(addToCartButton, "Add To Cart Button");
    }

    public void acceptProductAddedAlert() {
        logger.info("accepting 'Product Added' Alert");
        Alert alert = waitUtil.waitForAlert();
        alert.accept();
    }

    public boolean isProductAddedAlertPresent() {
        logger.info("checking 'Product Added' Alert Present");
        try {
            waitUtil.waitForAlert();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public enum InfoItem {
        IMAGE("Product Image"),
        TITLE("Product Title"),
        PRICE("Product Price"),
        DESCRIPTION("Product Description");

        private final String name;

        InfoItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    //Test flow methods

    public ProductPage addToCart() {
        clickAddToCartButton();
        acceptProductAddedAlert();
        return this;
    }

    public TopMenu getTopMenu() {
        return new TopMenu(driver, topMenuContainer);
    }

    public Navigation getNavigation() {
        return new Navigation(driver,navigationRoot,getTopMenu());
    }

    public boolean isInfoVisible() {
        for (InfoItem item : InfoItem.values()) {
            if (!isVisible(item)) {
                return false;
            }
        }
        return true;
    }

}
