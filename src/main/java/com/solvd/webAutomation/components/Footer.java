package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

public class Footer extends AbstractPage {

    private static final String getInTouchCssSelector = "div[id='fotcont'] div[class*='col-sm-3'] .caption";

    @FindBy(css = getInTouchCssSelector)
    private WebElement getInTouch;

    public Footer(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("div[id='fotcont'] img");
    }

    public String[] getGetInTouchText(){
//        logger.info(getInTouch.getText());
        String[] lines= getInTouch.getText().split("\n");
        return lines;
    }

    public String getAddress(){
        String string = getGetInTouchText()[1]
                .split(":")[1]
                .substring(1);
        logger.info("Address: "+string);
        return string;
    }
    public String getPhone(){
        String string = getGetInTouchText()[2]
                .split(":")[1]
                .substring(1);
        logger.info("Phone: "+string);
        return string;
    }
    public String getEmail(){
        String string = getGetInTouchText()[3]
                .split(":")[1]
                .substring(1);
        logger.info("Email: "+string);
        return string;
    }


}
