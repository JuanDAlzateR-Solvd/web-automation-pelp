package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductGridItemComponent extends AbstractComponent {

    @FindBy(css = ".card-title")
    private WebElement productTitle;

    @FindBy(css = ".card-title a")
    private WebElement productLink;

    @FindBy(css = "h5")
    private WebElement productPrice;

    @FindBy(css = ".card-text")
    private WebElement productDescription;

    @FindBy(css = ".card-img-top")
    private WebElement imageIndicator;

    public ProductGridItemComponent(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    public WebElement getComponentLoadedIndicator() {
        return productTitle;
    }

    public String getProductTitle() {
        return getText(productTitle, "Product title");
    }

    public String getText() {

        return getText(productTitle, "Product Component"); //It doesn't work with root -> used title.
    }

    public String getProductName() {
        return getText(productTitle, "Product title");
    }

    public String getProductPrice() {
        return getText(productPrice, "Product price");
    }

    public String getProductDescription() {
        return getText(productDescription, "Product description");
    }

    public void clickProduct() {
        click(productLink, getProductName());
    }

}