package com.solvd.webAutomation.pages.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    private By rejectCookiesButton =By.cssSelector("#onetrust-reject-all-handler");
                                    //By.id("onetrust-reject-all-handler");
    /*
    cssSelector cheatsheet:
         by id: By.cssSelector("#onetrust-reject-all-handler")
         by class: By.cssSelector(".class-to-find")
         by attribute: By.cssSelector("input[name='email']")
    */
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    public WebDriver getDriver() {
        return driver;
    }
    public void clickRejectCookiesButton() {
        driver.findElement(rejectCookiesButton).click();
    }
}
