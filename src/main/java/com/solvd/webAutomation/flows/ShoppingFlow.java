package com.solvd.webAutomation.flows;

import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.config.ConfigReader;
import com.solvd.webAutomation.pages.desktop.HomePage;
import com.solvd.webAutomation.wait.WaitUtil;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShoppingFlow {

    private final WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingFlow.class);

    public ShoppingFlow(WebDriver driver) {
        this.driver = driver;
    }

    public String addProductToCart(int productIndex) {

        HomePage homePage = new HomePage(driver);

        ProductGrid productGrid = homePage.getProductGrid();

        String productName = productGrid.getProductNameByIndex(productIndex);
        String excludedProductsConfig = ConfigReader.get("excluded_products");
        List<String> excludedProducts = excludedProductsConfig != null
                ? Arrays.stream(excludedProductsConfig.split(","))
                .map(String::trim)
                .toList()
                : List.of();

        //Identified a bug specific for some products (e.g. Nexus 6).
        List<String> availableProducts = productGrid.getProductTitles()
                .stream()
                .filter(p -> !excludedProducts.contains(p))
                .toList();

        if (availableProducts.isEmpty()) {
            throw new RuntimeException("No products available to add to cart");
        }

        if(!availableProducts.contains(productName)) {
            logger.debug("Product [{}] not found in available products. Selecting first available product.", productName);
            productIndex = availableProducts.indexOf(availableProducts.get(0));
        }

        productGrid
                .openProductByIndex(productIndex)
                .addToCart()
                .getNavigation()
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

    public List<String> addRandomProductsToCart(int numberOfProducts) {
        List<String> productNames = new ArrayList<>();

        for (int i = 0; i < numberOfProducts; i++) {
            productNames.add(addRandomProductToCart());
        }
        return productNames;
    }

}
