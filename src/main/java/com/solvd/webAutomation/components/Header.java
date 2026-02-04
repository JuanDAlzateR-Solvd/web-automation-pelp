package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;

public class Header {
    private WebDriver driver;

    @FindBy(css="a[id='meganav-link-1']")
    private WebElement womenButton;
    @FindBy(css="a[id='meganav-link-2']")
    private WebElement menButton;
    @FindBy(css="a[id='meganav-link-3']")
    private WebElement homeButton;
    @FindBy(css="a[id='meganav-link-4']")
    private WebElement boysButton;
    @FindBy(css="a[id='meganav-link-5']")
    private WebElement girlsButton;

    public HashMap<NavItem, WebElement> navButtons = new HashMap<>();

    public Header(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        navButtons.put(NavItem.WOMEN, womenButton);
        navButtons.put(NavItem.MEN, menButton);
        navButtons.put(NavItem.HOME, homeButton);
        navButtons.put(NavItem.BOYS, boysButton);
        navButtons.put(NavItem.GIRLS, girlsButton);

    }
    public WebDriver getDriver() {
        return driver;
    }
    public WebElement getWomenButton() {return womenButton;}
    public WebElement getMenButton() {return menButton;}
    public WebElement getHomeButton() {return homeButton;}
    public WebElement getBoysButton() {return boysButton;}
    public WebElement getGirlsButton(){return girlsButton;}

    public void clickButton(NavItem navItem) {
        navButtons.get(navItem).click();
    }

    public enum NavItem {

        WOMEN(),
        MEN(),
        HOME(),
        BOYS(),
        GIRLS();

    }


}
