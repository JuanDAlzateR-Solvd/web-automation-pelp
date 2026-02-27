package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.IntStream;

public class CartPage extends AbstractPage {

    @FindBy(css = "#tbodyid")
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

    public List<WebElement> getDeleteButtonsList() {
        return deleteButtonsList;
    }

    public String getTextOf(WebElement product) {
        logger.debug("Getting text of product [{}]",product.getAccessibleName());
        String productName = getText(product).split("\n")[0];
        logger.debug("Got text of product [{}] with name [{}]",product.getAccessibleName(),productName);
        return productName;
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
     * <p>The search performs a match using {@code String.contains()}.
     *
     * @param cartProducts list of product elements displayed in the cart
     * @param productName  text to match against each product's visible name
     * @return zero-based index of the first matching product,
     * or -1 if no match is found
     */
    public int findProductIndexInCart(List<WebElement> cartProducts, String productName) {
        for (int i = 0; i < cartProducts.size(); i++) {
            WebElement product = cartProducts.get(i);
            String rowText = getTextOf(product);
            if (rowText.contains(productName)) {
                logger.info("Product '{}' found in cart at index {}", productName, i);
                return i;
            }
        }
        logger.info("Product '{}' not found in cart", productName);
        return -1;
    }

    public void printProductsInCart() {
        logger.info("Printing products in cart:");
        getCartProducts().forEach(p -> {
            logger.info(p.getText());
        });
        logger.info("Finished printing products in cart.");
    }

    public void deleteProduct(int productIndex) {
        logger.info("Deleting product {}", getProductName(productIndex));
        int initialCartSize = getProductCount();
        clickDeleteButton(productIndex);
        waitCartUpdatesAfterDeleteProduct(initialCartSize);
    }

    public String getProductName(int productIndex) {
        List<WebElement> products = getProductElements();
        WebElement productToDelete = products.get(productIndex);
        return getTextOf(productToDelete);
    }

    private void clickDeleteButton(int productIndex) {
        //DOM updates after delete.

//        List<WebElement> deleteButtons = getDeleteButtonsList();
//        click(deleteButtons.get(productIndex), "deleteButton" + productIndex);
        By by = By.cssSelector("#tbodyid a[onclick*='deleteItem']");
        List<WebElement> deleteButtons = driver.findElements(by);
        click(deleteButtons.get(productIndex), "deleteButton" + productIndex);
    }

    private void waitCartUpdatesAfterDeleteProduct(int initialCartSize) {
        if (!isCartEmpty()) {
            waitUntilCartDeletesProduct(initialCartSize);
            waitUntilVisible(tableIndicator,"Shopping cart table");
        }
    }

    public void waitUntilCartShowsProducts() {
        logger.info("Waiting for the shopping cart to show products");
        By by = By.cssSelector("#tbodyid .success");
        waitService.waitForNumberOfElementsToBeMoreThan(by, 0);
    }

    public void waitUntilCartDeletesProduct(int initialCartSize) {
        logger.info("Waiting for the shopping cart to reload");
        By by = By.cssSelector("#tbodyid .success");
        waitService.waitForNumberOfElementsToBe(by, initialCartSize - 1);
    }

    public boolean isCartEmpty() {

        logger.info("Checking if shopping cart is empty");
        waitService.waitForPresenceOfElementLocated(By.id("tbodyid"));

        List<WebElement> rows =
                driver.findElements(By.cssSelector("#tbodyid tr"));

        if (rows.size() == 0) {
            logger.info("Shopping cart is empty");
        } else {
            logger.info("Shopping cart is not empty");
        }
        return rows.size() == 0;
    }

    //Test flow methods

    public TopMenu getTopMenu() {
        return new TopMenu(driver);
    }

    public boolean containsProduct(String productName) {
        List<WebElement> products = getProductElements();
        int index = findProductIndexInCart(products, productName);
        return index >= 0;
    }

    public int getProductCount() {
        logger.info("Checking number of products in shopping cart");
        waitService.waitForPresenceOfElementLocated(By.id("tbodyid"));

        List<WebElement> rows =
                driver.findElements(By.cssSelector("#tbodyid .success"));

        int size = rows.size();
        logger.info("Shopping cart has {} products", size);

        return size;
    }

    public void deleteProduct(String productName) {
        logger.info("Trying to delete product {}", productName);
        int productIndex = findProductIndexInCart(getProductElements(), productName);
        if (productIndex >= 0) {
            deleteProduct(productIndex);
        } else {
            logger.info("Product '{}' not found in cart", productName);
        }
    }

    public void waitUntilCartLoadsProducts() {
        if (!isCartEmpty()) {
            waitUntilCartShowsProducts();
            waitUntilVisible(tableIndicator,"Shopping cart table");
        }
    }

    public void emptyShoppingCart() {
        while (!isCartEmpty()) {
            deleteProduct(0);
        }
    }

}
