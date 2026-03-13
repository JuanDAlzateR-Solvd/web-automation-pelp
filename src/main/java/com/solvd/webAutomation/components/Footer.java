package com.solvd.webAutomation.components;

import com.solvd.webAutomation.utils.StringUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Footer extends AbstractComponent {

    @FindBy(css = "div[class*='col-sm-3'] .caption")
    private WebElement contactInfo;

    @FindBy(css = "img")
    private WebElement imageLocator;

    public Footer(WebDriver driver, SearchContext root) {
        super(driver, root);
    }

    @Override
    protected WebElement getComponentLoadedIndicator() {
        return imageLocator;
    }

    /**
     * Parse contact info once and return a map:
     * Address -> value
     * Phone -> value
     * Email -> value
     */
    private Map<String, String> parseContactInfo() {

        return Arrays.stream(contactInfo.getText().split("\n"))
                .map(String::trim)
                .map(line -> line.split(":", 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(
                        parts -> parts[0].trim(),
                        parts -> parts[1].trim()
                ));
    }

    public boolean isVisibleInScreen() {
        return isInViewport(contactInfo, "Get In Touch (Contact Info) Thumbnail");
    }

    public void scrollToBottom() {
        scrollTo(contactInfo);
    }

    public boolean verifyFooterInfo() {

        Map<String, String> info = parseContactInfo();

        String address = info.getOrDefault(InfoItem.ADDRESS.getLabel(), "");
        String phone = info.getOrDefault(InfoItem.PHONE.getLabel(), "");
        String email = info.getOrDefault(InfoItem.EMAIL.getLabel(), "");

        boolean validAddress = !address.isBlank();
        boolean validPhone = StringUtils.isValidPhone(phone);
        boolean validEmail = StringUtils.isValidEmail(email);

        if (!validAddress) {
            logger.error("Invalid Address: {}", address);
        }
        if (!validPhone) {
            logger.error("Invalid Phone: {}", phone);
        }
        if (!validEmail) {
            logger.error("Invalid Email: {}", email);
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
