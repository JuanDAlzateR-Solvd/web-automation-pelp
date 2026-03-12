package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductGrid extends AbstractComponent {

    @FindBy(css = ".pagination #next2")
    private WebElement nextButton;

    @FindBy(css = "#tbodyid .col-lg-4")
    private List<ProductGridItemComponent> productItems;

    @FindBy(css = ".card-img-top.img-fluid")
    private WebElement imageIndicator;

    public ProductGrid(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    protected WebElement getComponentLoadedIndicator() {
        return imageIndicator;
    }

    public List<ProductGridItemComponent> getProductComponents() {
        waitUntilComponentIsReady();
        logger.debug("Getting product components: found {} products", productItems.size());
        return productItems;
    }

    public List<String> getProductTitles() {
        return getProductComponents().stream()
                .map(ProductGridItemComponent::getProductTitle)
                .toList();
    }

    public boolean isNextButtonClickable() {
        return isClickable(nextButton);
    }

    public void clickNextButton() {
        click(nextButton, "Next Button");
    }

    public ProductGridItemComponent getProduct(int productIndex) {
        List<ProductGridItemComponent> products = getProductComponents();

        if (productIndex >= products.size()) {
            throw new IllegalArgumentException(
                    "Requested product index " + productIndex +
                            ", but only " + products.size() + " products found"
            );
        }
        ProductGridItemComponent product = products.get(productIndex);
        logger.debug("Getting product {} from product grid", productIndex);
        return product;
    }

    //Test flow methods

    public ProductPage openProduct(int index) {
        ProductGridItemComponent product = getProduct(index);
        product.clickProduct();
        return new ProductPage(driver);
    }

    public String getProductName(int index) {
        ProductGridItemComponent product = getProduct(index);
        return product.getProductName();
    }

    public int getProductCount() {
        logger.info("Checking number of products in product grid");
        waitUtil.waitForPresenceOfElementLocated(By.id("tbodyid"));

        int size = getProductComponents().size();
        logger.info("Product grid has {} products", size);

        return size;
    }

}