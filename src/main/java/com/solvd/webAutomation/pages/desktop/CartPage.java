package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.OptionalInt;
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

    private List<WebElement> products;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getProductGridContainer() {
        return productGridContainer;
    }

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector(".table-responsive");
    }

    public List<WebElement> getProductElements() {
        return productElements;
    }

    public List<WebElement> getProductElementsBy() {
        return productGridContainer.findElements(By.cssSelector(":scope tr[class='success']"));
    }

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
//        List<WebElement> cartProducts = getProductElementsBy();
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
            WebElement indicator = driver.findElement(getPageLoadedIndicator());
            waitVisible(indicator);
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
