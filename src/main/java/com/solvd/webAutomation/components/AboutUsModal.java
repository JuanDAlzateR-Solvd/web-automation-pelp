package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class AboutUsModal extends AbstractPage {

    @FindBy(id = "videoModalLabel")
    private WebElement title;

    @FindBy(css = "#videoModal button.btn.btn-secondary")
    private WebElement closeButton;

    @FindBy(css = "#videoModal .close")
    private WebElement exitButton;

    private final Map<MenuItem, WebElement> menuButtons;

    public AboutUsModal(WebDriver driver) {
        super(driver);
        menuButtons = Map.of(
                MenuItem.CLOSE, closeButton,
                MenuItem.EXIT, exitButton
        );
    }

    @Override
    protected WebElement getPageLoadedIndicator() {
        return title;
    }

    public WebElement getTitle() {
        return title;
    }

    public void click(MenuItem item) {
        WebElement button = menuButtons.get(item);
        click(button, item.name);
    }

    public boolean isModalVisible() {
        return title.isDisplayed();
    }

    public enum MenuItem {
        CLOSE("Close Button"),
        EXIT("Exit Button");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public void close() {
        click(AboutUsModal.MenuItem.CLOSE);
    }

}
