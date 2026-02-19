package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.IntStream;

public class CartPage extends AbstractPage {

    @FindBy(css = "#tbodyid]")
    private WebElement productGridContainer;

    @FindBy(css = "#totalp")
    private WebElement totalPrice;

    @FindBy(css = "#tbodyid .success")
    private List<WebElement> productElements;

    @FindBy(css = "#tbodyid a[onclick*='deleteItem']")
    private List<WebElement> deleteButtonsList;

    @FindBy(css = ".table-responsive")
    private WebElement tableIndicator;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getProductGridContainer() {
        return productGridContainer;
    }

    @Override
    protected WebElement getPageLoadedIndicator() {
        return tableIndicator;
    }

    public List<WebElement> getProductElements() {
        return productElements;
    }

//    public List<WebElement> getProductElementsBy() {
//        return productGridContainer.findElements(By.cssSelector(":scope tr[class='success']"));
//    }

    public List<WebElement> getDeleteButtonsList() {
        return deleteButtonsList;
    }

    public String getTextOf(WebElement product) {
        String productName = getText(product).split("\n")[0];
        return getText(product, productName);
    }

    public String getTotalPrice() {
        return getText(totalPrice, "totalPrice");
    }

    public List<WebElement> getCartProducts() {
        List<WebElement> cartProducts = getProductElements();
        logger.info("products in cart:{}", cartProducts.size());
        cartProducts.forEach(p -> {
            logger.info(p.getText());
        });
        return cartProducts;
    }

    /**
     * Returns the index of the first cart product whose visible text contains
     * the given product name.
     *
     * <p>The search performs a match using {@code String.equalsIgnoreCase()}.
     *
     * @param cartProducts list of product elements displayed in the cart
     * @param productName  text to match against each product's visible name
     * @return zero-based index of the first matching product,
     * or -1 if no match is found
     */
    public int findProductIndexInCart(List<WebElement> cartProducts, String productName) {
        int index = IntStream.range(0, cartProducts.size())
                .filter(i -> cartProducts.get(i).getText().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(-1);

        if (index >= 0) {
            logger.info("Product '{}' found in cart at index {}", productName, index);
        } else {
            logger.info("Product '{}' not found in cart", productName);
        }
        return index;
    }

    public void deleteProduct(int productIndex) {
        List<WebElement> products = getProductElements();
        List<WebElement> deleteButtons = getDeleteButtonsList();
        WebElement productToDelete = products.get(productIndex);
        String productName = getTextOf(productToDelete);
        logger.info("Deleting product {}", productName);
        click(deleteButtons.get(productIndex), "deleteButton" + productIndex);

//        waitUntilPageIsLoaded();
//        waitUntilCartDeletesProduct();

        if (!isCartEmpty()) {
            waitUntilCartDeletesProduct();
            waitVisible(tableIndicator);
        }
    }

    public void waitUntilCartDeletesProduct() {
        logger.info("Waiting for the shopping cart to reload");
        int cartSize = getCartProducts().size();
        By by = By.cssSelector("#tbodyid .success");
        wait.until(ExpectedConditions.numberOfElementsToBe(by, cartSize - 1));
    }

    public boolean isCartEmpty() {

        logger.info("Checking if shopping cart is empty");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tbodyid")));

        List<WebElement> rows =
                driver.findElements(By.cssSelector("#tbodyid tr"));

        if (rows.size() == 0) {
            logger.info("Shopping cart is empty");
        } else {
            logger.info("Shopping cart is not empty");
        }
        return rows.size() == 0;
    }

}
