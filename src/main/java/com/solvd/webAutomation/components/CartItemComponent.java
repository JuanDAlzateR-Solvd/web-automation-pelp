package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartItemComponent extends AbstractComponent {

    @FindBy(css = "td:nth-child(1) img")
    private WebElement imageIndicator;

    @FindBy(css = "td:nth-child(2)")
    private WebElement titleCell;

    @FindBy(css = "td:nth-child(3)")
    private WebElement priceCell;

    @FindBy(css = "td:nth-child(4) a[onclick*='deleteItem']")
    private WebElement deleteButton;

    public CartItemComponent(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    public WebElement getComponentLoadedIndicator() {
        return imageIndicator;
    }

    public String getTitle() {
        return getText(titleCell, "Product Title");
    }

    public String getPrice() {
        return getText(priceCell, "Product Price");
    }

    public String getRootText() {
        return getText(root, "Product Component");
    }

    public void deleteProduct() {
        click(deleteButton, "Delete Button");
    }

}