package com.solvd.webAutomation.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductGridItemComponent extends AbstractComponent {


    @FindBy(css = ".card-title")
    private WebElement title;

    @FindBy(css = "h5")
    private WebElement price;

    @FindBy(css = ".card-text")
    private WebElement description;

    @FindBy(css = ".card-img-top")
    private WebElement imageIndicator;


    public ProductGridItemComponent(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    public WebElement getComponentLoadedIndicator() {
        return title;
    }
    public WebElement getTitle() {
        return title;
    }

    public String getText() {

        return getText(title, "Product Component"); //It doesn't work with root -> used title.
    }


    public String getProductName() {
        return getText(title, "Product title");
    }

    public WebElement getPrice() {
        return price;
    }

    public WebElement getDescription() {
        return description;
    }

    public void clickProduct() {
        click(title, getProductName());
    }
}