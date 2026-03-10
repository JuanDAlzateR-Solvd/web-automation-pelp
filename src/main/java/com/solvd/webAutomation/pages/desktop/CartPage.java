package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.components.CartItemComponent;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends AbstractPage {

    @FindBy(css = "#tbodyid")
    private WebElement productGridContainer;

    @FindBy(css = "#totalp")
    private WebElement totalPrice;

    @FindBy(css = "#tbodyid .success")
    private List<WebElement> tableRows;

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

    public List<CartItemComponent> getCartItemComponents() {
        waitUntilPageIsReady();
        waitUtil.waitForPresenceOfElementLocated(By.id("tbodyid"));

        By by = By.cssSelector("#tbodyid .success");
        List<WebElement> elements = driver.findElements(by);
        logger.debug("Getting cart item components: found {} products", elements.size());
        return elements.stream()
                .map(el -> new CartItemComponent(driver, el))
                .toList();
    }

    public CartItemComponent getCartItemComponentByName(String productName) {
        List<CartItemComponent> cartItems = getCartItemComponents();
        return cartItems.stream()
                .filter(ci -> ci.getTitle().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);
    }

    public void deleteProduct(String productName) {
        //DOM updates after delete.
        waitUntilPageIsReady();
        waitUntilCartLoadsProducts();

        logger.info("Trying to delete Product [{}] in cart", productName);
        CartItemComponent cartItem = getCartItemComponentByName(productName);
        if (cartItem == null) {
            logger.warn("Product [{}] not found in cart", productName);
            return;
        }
        cartItem.waitUntilComponentIsReady();
        int initialCartSize = getProductCount();
        cartItem.deleteProduct();
        waitCartUpdatesAfterDeleteProduct(initialCartSize);
    }

    public String getTotalPrice() {
        return getText(totalPrice, "totalPrice");
    }

    public void printProductsInCart() {
        List<CartItemComponent> cartItems = getCartItemComponents();
        logger.info("Printing products in cart:");
        cartItems.forEach(p -> {
            logger.info(p.getRootText());
        });
        logger.info("Finished printing products in cart.");
    }

    private void waitCartUpdatesAfterDeleteProduct(int initialCartSize) {
        if (!isCartEmpty()) {
            waitUntilCartDeletesProduct(initialCartSize);
            waitUntilVisible(tableIndicator, "Shopping cart table");
        }
    }

    public void waitUntilCartShowsProducts() {
        logger.info("Waiting for the shopping cart to show products");
        By by = By.cssSelector("#tbodyid .success");
        waitUtil.waitForNumberOfElementsToBeMoreThan(by, 0);
    }

    public void waitUntilCartDeletesProduct(int initialCartSize) {
        logger.info("Waiting for the shopping cart to reload");
        By by = By.cssSelector("#tbodyid .success");
        waitUtil.waitForNumberOfElementsToBe(by, initialCartSize - 1);
    }

    public boolean isCartEmpty() {
        waitUntilPageIsReady();

        logger.info("Checking if shopping cart is empty");
        waitUtil.waitForPresenceOfElementLocated(By.id("tbodyid"));

        List<WebElement> rows =
                driver.findElements(By.cssSelector("#tbodyid .success"));

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
        CartItemComponent cartItem = getCartItemComponentByName(productName);
        return cartItem != null;
    }

    public int getProductCount() {
        logger.info("Checking number of products in shopping cart");
        waitUtil.waitForPresenceOfElementLocated(By.id("tbodyid"));

        List<WebElement> rows =
                driver.findElements(By.cssSelector("#tbodyid .success"));

        int size = rows.size();
        logger.info("Shopping cart has {} products", size);

        return size;
    }

    public void waitUntilCartLoadsProducts() {
        if (!isCartEmpty()) {
            waitUntilCartShowsProducts();
            waitUntilVisible(tableIndicator, "Shopping cart table");
        }
    }

    public void emptyShoppingCart() {
        while (!isCartEmpty()) {
            logger.info("DELETE Deleting {} products from cart", getProductCount());
            String productName = getCartItemComponents().get(0).getTitle();
            deleteProduct(productName);
        }
    }

}
