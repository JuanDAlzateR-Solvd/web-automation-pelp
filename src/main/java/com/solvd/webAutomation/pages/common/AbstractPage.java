package com.solvd.webAutomation.pages.common;

import com.solvd.webAutomation.actions.NavActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractPage {
    protected WebDriver driver;
    protected NavActions navActions;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.navActions = new NavActions(driver);
    }
}
