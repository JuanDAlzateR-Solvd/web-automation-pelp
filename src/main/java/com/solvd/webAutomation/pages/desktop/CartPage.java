package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends AbstractPage {

    private static final String addToCartButtonCssSelector="a[onclick*='addToCart']";

    @FindBy(css = "tbody[id='tbodyid']")
    private WebElement grid;
    @FindBy(css = "h3[id='totalp']")
    private WebElement totalPrice;

    private List<WebElement> products;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getGrid() {
        return grid;
    }

    public List<WebElement> getElementsList() {
        return grid.findElements(By.cssSelector(":scope tr[class='success']"));
    }

    public List<WebElement> getDeleteButtonsList() {
        return grid.findElements(By.cssSelector(":scope a[onclick*='deleteItem']"));
    }

    public String getTextOf(WebElement product) {
        String productName = getText(product).split("\n")[0];
        return getText(product, productName);
    }

    public String getTotalPrice() {
        return getText(totalPrice, "totalPrice");
    }





}
