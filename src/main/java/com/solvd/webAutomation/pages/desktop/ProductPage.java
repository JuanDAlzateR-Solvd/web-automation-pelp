package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends AbstractPage {

    private static final String addToCartButtonCssSelector = "a.btn.btn-success.btn-lg";

    @FindBy(css = "div[class='item active']")
    private WebElement image;
    @FindBy(css = "div[id='tbodyid']>h2[class='name']")
    private WebElement title;
    @FindBy(css = "div[id='tbodyid']>h3[class='price-container']")
    private WebElement price;
    @FindBy(css = "div[id='tbodyid'] div[id='more-information']")
    private WebElement description;
    @FindBy(css = "a.btn.btn-success.btn-lg")
    private WebElement addToCartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("div[id='myCarousel-2'] img");
    }

    public boolean isVisible(InfoItem item) {
        return switch (item) {
            case IMAGE -> isVisible(image, item.getName());
            case TITLE -> isVisible(title, item.getName());
            case PRICE -> isVisible(price, item.getName());
            case DESCRIPTION -> isVisible(description, item.getName());
        };
    }

    public void clickAddToCartButton2() {//It doesn't work
        By by = By.cssSelector(addToCartButtonCssSelector);
        click(by, "Add To Cart Button");
    }

    public void clickAddToCartButton() {
        click(addToCartButton, "Add To Cart Button");
    }

    public void acceptProductAddedAlert() {
        logger.info("accepting 'Product Added' Alert");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public boolean isProductAddedAlertPresent() {
        logger.info("checking 'Product Added' Alert Present");
        try {
            wait.until(ExpectedConditions.alertIsPresent());
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

}
