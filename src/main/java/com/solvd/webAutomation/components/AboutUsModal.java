package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutUsModal extends AbstractComponent {

    @FindBy(id = "videoModalLabel")
    private WebElement title;

    @FindBy(css = "#videoModal button.btn.btn-secondary")
    private WebElement closeButton;

    @FindBy(css = "#videoModal .close")
    private WebElement exitButton;

    public AboutUsModal(WebDriver driver, WebElement root) {
        super(driver, root);
    }

    @Override
    protected WebElement getComponentLoadedIndicator() {
        return title;
    }

    public WebElement getTitle() {
        return title;
    }

    public boolean isModalVisible() {
        return title.isDisplayed();
    }

    public void clickClose() {
        click(closeButton, "AboutUsModal CloseButton");
        waitUtil.waitForInvisibility(closeButton, "Close Button");
    }

}
