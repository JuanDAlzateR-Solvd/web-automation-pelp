package com.solvd.webAutomation.components;

import com.solvd.webAutomation.actions.NavActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductGrid {
    private WebDriver driver;
    private NavActions navActions;

    @FindBy(css = "div[id='tbodyid']")
    private WebElement grid;

    private List<WebElement> products;

    public ProductGrid(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.navActions = new NavActions(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public List<WebElement> getElementsList() {
        return grid.findElements(By.cssSelector(":scope >*"));
    }

    public List<String> getProductTitles() {
        List<String> productsList = new ArrayList<>();
        for (WebElement product : getElementsList()) {
            productsList.add(product.getText());
        }
        return productsList;
    }

}
