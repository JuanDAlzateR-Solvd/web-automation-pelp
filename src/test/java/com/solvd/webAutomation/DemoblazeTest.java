package com.solvd.webAutomation;

import com.solvd.webAutomation.components.ProductGrid;
import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

public class DemoblazeTest extends AbstractTest {
    private static final Logger logger =
            LoggerFactory.getLogger(DemoblazeTest.class);

    @Test(testName = "Functionality of top menu", description = "verifies that home page loads,top Menu works correctly")
    public void verifyTopMenuNavigation() {

        HomePage homePage = new HomePage(driver);
        TopMenu topMenu = new TopMenu(driver);

        homePage.waitUntilPageIsReady();

        SoftAssert sa = new SoftAssert();

        Arrays.stream(TopMenu.MenuItem.values())
                .forEach(menuItem -> {
                    topMenu.click(menuItem);
                    sa.assertTrue(topMenu.isVisible(menuItem));
                    topMenu.clickClose(menuItem);
                });

        sa.assertAll();
    }

    @Test(testName = "List of Products - Task1", description = "filters the products by category, then prints in console all the products")
    public void verifyProductsDisplayedForSelectedCategory() {

        HomePage homePage = new HomePage(driver);
        ProductGrid productGrid = new ProductGrid(driver);

        homePage.waitUntilPageIsReady();

        homePage.click(HomePage.MenuItem.LAPTOPS);

        homePage.waitUntilPageIsReady();

        List<String> productsList = productGrid.getProductTitles();
        productsList.forEach(logger::info);

        Assert.assertFalse(productsList.isEmpty(), "There are no products in the grid");
    }

}
