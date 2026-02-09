package com.solvd.webAutomation.components;

import com.solvd.webAutomation.actions.NavActions;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductGrid extends AbstractPage {

    @FindBy(css = "div[id='tbodyid']")
    private WebElement grid;

    @FindBy(css = "button[id*='next']")
    private WebElement nextButton;

    public ProductGrid(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> products;

    public WebDriver getDriver() {
        return driver;
    }

    public List<WebElement> getElementsList() {
        return grid.findElements(By.cssSelector(":scope >* a[class='hrefch']"));
    }

    public List<String> productsList() {
        List<String> productsList = new ArrayList<>();
        for (WebElement product : getElementsList()) {
            productsList.add(getText(product));
        }
        return productsList;
    }

    public Boolean nextButtonIsClickable() {
        return isClickable(nextButton);
    }
    public void clickNextButton() {
       click(nextButton,"Next Button");
    }

    public String getTextOf(WebElement product) {
        String productName = getText(product).split("\n")[0];
        return getText(product,productName);
    }

    public void clickProduct(WebElement product) {
        String productName = getText(product).split("\n")[0];
        click(product,productName);
    }

    public WebElement getGrid() {
        return grid;
    }

}
