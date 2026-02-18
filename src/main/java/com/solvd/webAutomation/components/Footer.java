package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

public class Footer extends AbstractPage {

    private static final String getInTouchCssSelector = "#fotcont div[class*='col-sm-3'] .caption";

    @FindBy(css = getInTouchCssSelector)
    private WebElement getInTouch;

    public Footer(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getPageLoadedIndicator() {
        return By.cssSelector("#fotcont img");
    }

    public String[] getGetInTouchText() {
//        logger.info(getInTouch.getText());
        return Arrays.stream(getInTouch.getText().split("\n"))
                .map(String::trim)
                .toArray(String[]::new);
    }

    private String extractValueFromLine(int lineIndex) {
        String[] lines = getGetInTouchText();
        if (lines.length <= lineIndex) return "";
        String[] parts = lines[lineIndex].split(":", 2);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    public String getAddress() {
        return extractValueFromLine(1);
    }

    public String getPhone() {
        return extractValueFromLine(2);
    }

    public String getEmail() {
        return extractValueFromLine(3);
    }

    public boolean isVisibleInScreen() {
        return isInViewport(getInTouch, "Get In Touch Thumbnail");
    }

    public void scrollToBottom() {
        scrollTo(getInTouch);
    }

}
