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

    @FindBy(css = "#tbodyid")
    private WebElement productGridContainer;

    @FindBy(css = ".pagination #next2")
    private WebElement nextButton;

    @FindBy(css = "#tbodyid .card-title")
    private List<WebElement> productElements;

    @FindBy(css = "#tbodyid .card-img-top.img-fluid")
    private WebElement imageIndicator;

    public ProductGrid(WebDriver driver) {
        super(driver);
    }

    @Override
    protected WebElement getPageLoadedIndicator() {
        return imageIndicator;
    }

    public List<WebElement> getProductElements() {
        return productElements;
    }

    public List<String> getProductTitles() {
        List<String> productsList = new ArrayList<>();
        for (WebElement product : getProductElements()) {
            productsList.add(getText(product));
        }
        return productsList;
    }

    public boolean isNextButtonClickable() {
        return isClickable(nextButton);
    }

    public void clickNextButton() {
        click(nextButton, "Next Button");
    }

    public void clickNextButtonIfPossible(HomePage.MenuItem category) {
        if (isNextButtonClickable() && category != HomePage.MenuItem.MONITORS) {
            //demoblaze.com has a bug, when click on category monitors it shows the next button, even thought it shouldn't.
            clickNextButton();
        }
    }

    public String getTextOf(WebElement product) {
        String productName = extractProductName(product);
        return getText(product, productName);
    }

    public String getProductName(WebElement product) {
        return extractProductName(product);
    }

    private String extractProductName(WebElement product) {
        return getText(product).split("\n")[0];
    }

    public void clickProduct(WebElement product) {
        String productName = getProductName(product);
        click(product, productName);
    }

    public WebElement getProductGridContainer() {
        return productGridContainer;
    }

    public WebElement getProductByIndex(int productIndex) {
        List<WebElement> products = getProductElements();
        WebElement product = products.get(productIndex);
        logger.info(getTextOf(product));
        return product;
    }

}
