package com.solvd.webAutomation.actions;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

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

    public void type(@NonNull WebElement element, @NonNull String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
    }

    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public void waitVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void pause(int seconds) {
        WebDriverWait waitTime=new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(d -> true);
//        try {
//            Thread.sleep(seconds * 1000L);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
