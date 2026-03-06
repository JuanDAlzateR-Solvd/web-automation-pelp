package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import com.solvd.webAutomation.utils.StringUtils;
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

    public String getInfo(InfoItem item) {
        return Arrays.stream(contactInfo.getText().split("\n"))
                .map(String::trim)
                .filter(line -> line.startsWith(item.getLabel()))
                .map(line -> line.split(":", 2)[1].trim())
                .findFirst()
                .orElse("");
    }

    public boolean isVisibleInScreen() {
        return isInViewport(contactInfo, "Get In Touch (Contact Info) Thumbnail");
    }

    public void scrollToBottom() {
        scrollTo(contactInfo);
    }

    public boolean verifyAddress() {
        return !getInfo(InfoItem.ADDRESS).isBlank();
    }

    public boolean verifyPhone() {
        String phone = getInfo(InfoItem.PHONE);
        return StringUtils.isValidPhone(phone);
    }

    public boolean verifyEmail() {
        String email = getInfo(InfoItem.EMAIL);
        return StringUtils.isValidEmail(email);
    }

    public boolean verifyFooterInfo() {
        boolean validAddress = verifyAddress();
        boolean validPhone = verifyPhone();
        boolean validEmail = verifyEmail();

        if (!validAddress) {
            logger.error("Invalid Address: {}", getInfo(InfoItem.ADDRESS));
        }
        if (!validPhone) {
            logger.error("Invalid Phone: {}", getInfo(InfoItem.PHONE));
        }
        if (!validEmail) {
            logger.error("Invalid Email: {}", getInfo(InfoItem.EMAIL));
        }
        return validAddress && validPhone && validEmail;
    }

    public void ensureVisible() {
        scrollToBottom();
    }

    public enum InfoItem {
        ADDRESS("Address"),
        PHONE("Phone"),
        EMAIL("Email");

        private final String label;

        InfoItem(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

}
