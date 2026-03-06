package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.desktop.HomePage;
import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductGrid extends AbstractComponent {

//    @FindBy(css = "#contcont")  //"#tbodyid"
//    private WebElement productGridContainer;

    @FindBy(css = ".pagination #next2")
    private WebElement nextButton;

    @FindBy(css = ".col-lg-4")
    private List<WebElement> productElements;

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
        List<WebElement> elements = getProductElements();
        logger.debug("Getting product components: found {} products", elements.size());
        return elements.stream()
                .map(el -> new ProductGridItemComponent(driver, el))
                .toList();
    }

    public List<WebElement> getProductElements() {
        return driver.findElements(By.cssSelector("#tbodyid .col-lg-4"));
    }

    public List<String> getProductTitles() {
//        By by = By.cssSelector("#tbodyid .card-title");
//        waitService.waitForNumberOfElementsToBeMoreThan(by,0);
        return getProductComponents().stream()
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

    public void clickNextButtonIfPossible(HomePage.Category category) {
        if (isNextButtonClickable() && category != HomePage.Category.MONITORS) {
            // Monitors category is expected to be a single page without pagination, so do not use the Next button for it.
            clickNextButton();
        }
    }

    public WebElement getProductGridContainer() {
        return root;
    }

    public ProductGridItemComponent getProductByIndex(int productIndex) {
        List<ProductGridItemComponent> products = getProductComponents();
        ProductGridItemComponent product = products.get(productIndex);
        logger.debug("Getting product {} from product grid", productIndex);
//        logger.info("product [{}] in {} grid position",product.getProductName(), productIndex);
        return product;
    }

    //Test flow methods

    public ProductPage openProductByIndex(int index) {
        ProductGridItemComponent product = getProductByIndex(index);
        product.clickProduct();
        return new ProductPage(driver);
    }

    public String getProductNameByIndex(int index) {
        ProductGridItemComponent product = getProductByIndex(index);
        return product.getProductName();
    }

    public int getProductCount() {
        logger.info("Checking number of products in product grid");
        waitUtil.waitForPresenceOfElementLocated(By.id("tbodyid"));

        List<WebElement> rows =
                driver.findElements(By.cssSelector("#tbodyid .card-title"));

        int size = rows.size();
        logger.info("Product grid has {} products", size);

        return size;
    }

}
