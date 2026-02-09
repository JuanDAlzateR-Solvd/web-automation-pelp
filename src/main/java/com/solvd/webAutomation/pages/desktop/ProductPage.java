package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends AbstractPage {

    @FindBy(css = "div[class='item active']")
    private WebElement image;
    @FindBy(css = "div[id='tbodyid']>h2[class='name']")
    private WebElement title;
    @FindBy(css = "div[id='tbodyid']>h3[class='price-container']")
    private WebElement price;
    @FindBy(css = "div[id='tbodyid'] div[id='more-information']")
    private WebElement description;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }


    public Boolean isVisible(InfoItem item) {
        switch (item) {
            case IMAGE -> {
                return isVisible(image, item.name);
            }
            case TITLE -> {
                return isVisible(title, item.name);
            }
            case PRICE -> {
                return isVisible(price, item.name);
            }
            case DESCRIPTION -> {
                return isVisible(description, item.name);
            }
        }
        logger.info("error: infoItem not found");
        return false;
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
    }
}
