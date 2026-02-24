package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.components.TopMenu;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomePage extends AbstractPage {

    @FindBy(css = ".list-group a[onclick*='phone']")
    private WebElement phonesButton;

    @FindBy(css = ".list-group a[onclick*='notebook']")
    private WebElement laptopsButton;

    @FindBy(css = ".list-group a[onclick*='monitor']")
    private WebElement monitorsButton;

    @FindBy(css = "#tbodyid .card-img-top.img-fluid")// "#tbodyid .card-img-top.img-fluid"
    private List<WebElement> imageIndicator;

    private static final By LOADER = By.cssSelector(".loader, .spinner, .loading");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final Map<MenuItem, WebElement> menuButtons = Map.of(
            MenuItem.PHONES, phonesButton,
            MenuItem.LAPTOPS, laptopsButton,
            MenuItem.MONITORS, monitorsButton
    );

    @Override
    public WebElement getPageLoadedIndicator() {
        return imageIndicator.get(0);
    }

    public void click(MenuItem item) {
        click(menuButtons.get(item), item.name);
    }

    public enum MenuItem {
        PHONES("Category Phones"),
        LAPTOPS("Category Laptops"),
        MONITORS("Category Monitors");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

//    @Override
//    public void waitUntilPageIsReady() {
//        logger.info("Waiting for the page to load");
//        WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
//        pageWait.until(driver ->
//                ((JavascriptExecutor) driver)
//                        .executeScript("return document.readyState")
//                        .equals("complete")
//        );
//        pageWait.until(ExpectedConditions.invisibilityOfElementLocated(LOADER));
//        By products = By.cssSelector("#tbodyid .card");
//        pageWait.until(webDriver -> !driver.findElements(products).isEmpty());
//        pageWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(products));
//
//
//        logger.info("The page is ready");
//    }

}