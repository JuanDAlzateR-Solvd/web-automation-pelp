package com.solvd.webAutomation.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductGridItemComponent extends AbstractComponent {

    private WebElement title;

    private WebElement price;

    private WebElement description;

    private WebElement imageIndicator;

    public ProductGridItemComponent(WebElement root) {
        super(root);
    }

    public WebElement getComponentLoadedIndicator() {
        return getImageIndicator();
    }

    public WebElement getImageIndicator() {
        imageIndicator=root.findElement(By.cssSelector(".card-img-top"));
        return imageIndicator;
    }

    public WebElement getTitle() {
        title=root.findElement(By.cssSelector(".card-title"));
        return title;
    }

    public WebElement getPrice() {
        price=root.findElement(By.cssSelector("h5"));
        return price;
    }

    public WebElement getDescription() {
        description=root.findElement(By.cssSelector("#article"));
        return description;
    }

    public void loadComponent(){
        getImageIndicator();
        getTitle();
        getPrice();
        getDescription();
    }

    public String getText() {
        String productName = extractProductName(product);
        return getText(product, productName);
    }

    public String getProductName(WebElement product) {
        return extractProductName(product);
    }

    private String extractProductName(WebElement product) {
        return (getTitle());
    }
}
