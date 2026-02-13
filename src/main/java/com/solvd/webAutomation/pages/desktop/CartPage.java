package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class CartPage extends AbstractPage {

    private static final String addToCartButtonCssSelector = "a[onclick*='addToCart']";

    @FindBy(css = "tbody[id='tbodyid']")
    private WebElement grid;
    @FindBy(css = "h3[id='totalp']")
    private WebElement totalPrice;

    private List<WebElement> products;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getGrid() {
        return grid;
    }

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("div[class='table-responsive']");
    }

    public List<WebElement> getElementsList() {
        return grid.findElements(By.cssSelector(":scope tr[class='success']"));
    }

    public List<WebElement> getDeleteButtonsList() {
        return grid.findElements(By.cssSelector(":scope a[onclick*='deleteItem']"));
    }

    public String getTextOf(WebElement product) {
        String productName = getText(product).split("\n")[0];
        return getText(product, productName);
    }

    public String getTotalPrice() {
        return getText(totalPrice, "totalPrice");
    }

    public boolean isCartEmpty() {
        return getElementsList().isEmpty();
    }

    public List<WebElement> getCartProducts() {
        List<WebElement> cartProducts = getElementsList();
        logger.info("products in cart:{}", cartProducts.size());
        cartProducts.forEach(p -> {
            logger.info(p.getText());
        });
        return cartProducts;
    }

    public int findProductIndexInCart(List<WebElement> cartProducts, String productName) {
        int productIndex = -1;

        OptionalInt index = IntStream.range(0, cartProducts.size())
                .filter(i -> cartProducts.get(i).getText().contains(productName))
                .findFirst();
        if (index.isPresent()) {
            logger.info("Product is in the cart in position {}", index.getAsInt());
            productIndex = index.getAsInt();
        } else {
            logger.info("Product is not in the cart");
        }
        return productIndex;
    }

    public void deleteProduct(int productIndex) {
        List<WebElement> products = getElementsList();
        List<WebElement> deleteButtons = getDeleteButtonsList();
        WebElement productToDelete = products.get(productIndex);
        String productName = getTextOf(productToDelete);
        logger.info("Deleting product {}", productName);
        click(deleteButtons.get(productIndex), "deleteButton" + productIndex);

//        waitUntilPageIsLoaded();
        waitUntilCartDeletesProduct();

        if (deleteButtons.size() > 1) {
            waitVisible(getGrid());
        }

    }

    public void waitUntilCartDeletesProduct() {
        logger.info("Waiting for the shopping cart to reload");
        int cartSize=getCartProducts().size();
        By by = By.cssSelector("tbody[id='tbodyid'] tr[class='success']");
        wait.until(ExpectedConditions.numberOfElementsToBe(by, cartSize-1));

    }
}
