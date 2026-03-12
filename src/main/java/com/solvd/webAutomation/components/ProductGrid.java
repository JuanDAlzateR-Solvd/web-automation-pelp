package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ProductGrid extends AbstractComponent {

    @FindBy(css = ".pagination #next2")
    private WebElement nextButton;

    @FindBy(css = "#tbodyid .col-lg-4")
    private List<ProductGridItemComponent> productItems;

    @FindBy(css = ".card-img-top.img-fluid")
    private WebElement imageIndicator;

    public ProductGrid(WebDriver driver, SearchContext root) {
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

        Supplier<List<String>> getTitles = () -> {
            return getProductComponents().stream()
                    .map(ProductGridItemComponent::getProductTitle)
                    .toList();
        };

        return waitUtil.waitUntil(driver -> getTitles.get());

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

        Function<Integer,ProductPage> openProd = (i) -> {
            ProductGridItemComponent product = getProduct(i);
            product.clickProduct();
            return new ProductPage(driver);
        };

        return waitUtil.waitUntil(driver->openProd.apply(index));
    }

    public String getProductName(int index) {

        Function<Integer,String> getName = (i) -> {
            ProductGridItemComponent product = getProduct(index);
            return product.getProductName();
        };

        return waitUtil.waitUntil(driver->getName.apply(index));
    }

    public int getProductCount() {
        logger.info("Checking number of products in product grid");
        waitUtil.waitForPresenceOfElementLocated(By.id("tbodyid"));

        int size = getProductComponents().size();
        logger.info("Product grid has {} products", size);

        return size;
    }

}