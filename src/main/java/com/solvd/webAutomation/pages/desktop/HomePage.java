package com.solvd.webAutomation.pages.desktop;

import com.solvd.webAutomation.actions.NavActions;
import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPage {


    @FindBy(css = "a[onclick*='phone']")
    private WebElement phonesButton;
    @FindBy(css = "a[onclick*='notebook']")
    private WebElement laptopsButton;
    @FindBy(css = "a[onclick*='monitor']")
    private WebElement monitorsButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void clickPhonesButton() {
        navActions.click(phonesButton);
    }

    public void clickLaptopsButton() {
        navActions.click(laptopsButton);
    }

    public void clickMonitorsButton() {
        navActions.click(monitorsButton);
    }
}
