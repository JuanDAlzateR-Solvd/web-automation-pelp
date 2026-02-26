package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import com.solvd.webAutomation.pages.desktop.ProductPage;
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

    public List<ProductGridItemComponent> getProductComponents() {
        return productElements.stream().map(ProductGridItemComponent::new).toList();
    }

    public List<String> getProductTitles() {
        return  getProductComponents().stream()
                .map(ProductGridItemComponent::getTitle)
                .map(WebElement::getText)
                .toList();
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

    public void clickProduct(WebElement product) {
        String productName = getProductName(product);
        click(product, productName);
    }

    public WebElement getProductGridContainer() {
        return productGridContainer;
    }

    public WebElement getProductByIndex(int productIndex) {
        List<ProductGridItemComponent> products = getProductComponents();
        WebElement product = products.get(productIndex).getRoot();
        logger.info(getTextOf(product));
        return product;
    }

    //Test flow methods

    public ProductPage openProductByIndex(int index) {
        WebElement product = getProductByIndex(index);
        clickProduct(product);
        return new ProductPage(driver);
    }

    public String getProductNameByIndex(int index) {
        WebElement product = getProductByIndex(index);
        return extractProductName(product);
    }

    public int getProductCount() {
        logger.info("Checking number of products in product grid");
        waitService.waitForPresenceOfElementLocated(By.id("tbodyid"));

        List<WebElement> rows =
                driver.findElements(By.cssSelector("#tbodyid .card-title"));

        int size = rows.size();
        logger.info("Product grid has {} products", size);

        return size;
    }

}
