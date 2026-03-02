package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

public class Footer extends AbstractPage {

    @FindBy(css = "#fotcont div[class*='col-sm-3'] .caption")
    private WebElement contactInfo;

    @FindBy(css = "#fotcont img")
    private WebElement imageLocator;

    public Footer(WebDriver driver) {
        super(driver);
    }

    @Override
    protected WebElement getPageLoadedIndicator() {
        return imageLocator;
    }

    public String[] getContactInfoText() {
        logger.debug(contactInfo.getText());
        return Arrays.stream(contactInfo.getText().split("\n"))
                .map(String::trim)
                .toArray(String[]::new);
    }

    private String extractValueFromLine(String[] lines, int lineIndex) {
        if (lines.length <= lineIndex) return "";
        String[] parts = lines[lineIndex].split(":", 2);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    public String getInfo(InfoItem item) {
        return extractValueFromLine(getContactInfoText(), item.getLineIndex());
    }

    public boolean isVisibleInScreen() {
        return isInViewport(contactInfo, "Get In Touch (Contact Info) Thumbnail");
    }

    public void scrollToBottom() {
        scrollTo(contactInfo);
    }

    public boolean verifyAddress() {
        return getInfo(InfoItem.ADDRESS).length() > 5;
    }

    public boolean verifyPhone() {
        return getInfo(InfoItem.PHONE).length() > 5;
    }

    public boolean verifyEmail() {
        return getInfo(InfoItem.EMAIL).length() > 5;
    }

    public boolean verifyFooterInfo() {
        return verifyAddress() && verifyPhone() && verifyEmail();
    }

    public void ensureVisible() {
        scrollToBottom();
    }

    public enum InfoItem {
        ADDRESS("Get in Touch Address", 1),
        PHONE("Get in Touch Phone", 2),
        EMAIL("Get in Touch Email", 3);

        private final String name;
        private final int lineIndex;

        InfoItem(String name, int lineIndex) {
            this.name = name;
            this.lineIndex = lineIndex;
        }

        public String getName() {
            return name;
        }

        public int getLineIndex() {
            return lineIndex;
        }
    }

}
