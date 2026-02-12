package com.solvd.webAutomation.actions;

import com.solvd.webAutomation.pages.desktop.HomePage;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class NavActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public NavActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void click(@NonNull WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public void waitVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void pause(int seconds) {
//        WebDriverWait waitTime=new WebDriverWait(driver, Duration.ofSeconds(seconds));
//        wait.until(d -> true);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitUntilPageIsReady(HomePage homePage) {
        WebDriverWait waitTime=new WebDriverWait(driver, Duration.ofSeconds(10));
        waitTime.until(driver->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }
}
