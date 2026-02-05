package com.solvd.webAutomation.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Actions {
    private WebDriver driver;
    private WebDriverWait wait;

    public Actions(WebDriver driver) {
        this.driver = driver;
    }

    public void click(WebElement element) {
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
        WebDriverWait waitTime=new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(d -> true);
    }
}
