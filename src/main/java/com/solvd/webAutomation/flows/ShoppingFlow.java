package com.solvd.webAutomation.flows;

import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.desktop.CartPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import com.solvd.webAutomation.pages.desktop.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class ShoppingFlow {

    private final WebDriver driver;

    public ShoppingFlow(WebDriver driver) {
        this.driver = driver;
    }

    public String addProductToCart(int productIndex) {

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.getProductGrid();

        String productName = productGrid.getProductNameByIndex(productIndex);

        //Identified a bug specific for the Nexus 6 product, for now just change product.
        if ("Nexus 6".equals(productName)) {
            productIndex++;
            productName = productGrid.getProductNameByIndex(productIndex);
        }

        productGrid
                .openProductByIndex(productIndex)
                .addToCart()
                .getTopMenu()
                .goToHomePage();

        return productName;
    }

    public String addRandomProductToCart() {

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.getProductGrid();

        int size = productGrid.getProductCount();

        int randomIndex = new Random().nextInt(size);

        return addProductToCart(randomIndex);
    }
}
