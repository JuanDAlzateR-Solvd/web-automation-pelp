package com.solvd.webAutomation.flows;

import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class ShoppingFlow {

    private final ProductGrid productGrid;
    private final ProductPage productPage;
    private final TopMenu topMenu;

    public ShoppingFlow(ProductGrid productGrid,
                        ProductPage productPage,
                        TopMenu topMenu) {
        this.productGrid = productGrid;
        this.productPage = productPage;
        this.topMenu = topMenu;
    }

    public String addProductToCart(int productIndex) {
        WebElement product = productGrid.getProductByIndex(productIndex);
        String productName = productGrid.getProductName(product);

        if (productName.equals("Nexus 6")){//Identified a bug specific for the Nexus 6 product, for now just change product.
            product = productGrid.getProductByIndex(productIndex+1);
            productName = productGrid.getProductName(product);
        }

        productGrid.clickProduct(product);
        productPage.waitUntilPageIsReady();

        productPage.clickAddToCartButton();
        productPage.acceptProductAddedAlert();

        topMenu.clickButton(TopMenu.MenuItem.HOME);
        productGrid.waitUntilPageIsReady();

        return productName;
    }

    public String addRandomProductToCart() {
        productGrid.waitVisible(productGrid.getProductGridContainer());
        List<WebElement> products = productGrid.getProductElements();
        int size = products.size();

        Random rand = new Random();
        int randomNum = rand.nextInt(size);

        return addProductToCart(randomNum);
    }
}
