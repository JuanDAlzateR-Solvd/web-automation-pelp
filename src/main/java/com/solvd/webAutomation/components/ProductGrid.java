package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
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

    public List<String> getProductTitles() {
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
//        click(nextButton, "Next Button");
        click(By.cssSelector("button[id*='next']"), "Next Button");
    }

    public void clickNextButtonIfPossible(HomePage.MenuItem category) {
        if (nextButtonIsClickable() && category != HomePage.MenuItem.MONITORS) {
            //demoblaze.com has a bug, when click on category monitors it shows the next button, even thought it shouldn't.
            clickNextButton();
        }
    }

    public String getTextOf(WebElement product) {
        String productName = getText(product).split("\n")[0];
        return getText(product, productName);
    }

    public void clickProduct(WebElement product) {
        String productName = getProductName(product);
        click(product, productName);
    }

    public String getProductName(WebElement product) {
        return getText(product).split("\n")[0];
    }

    public WebElement getGrid() {
        return grid;
    }

}
