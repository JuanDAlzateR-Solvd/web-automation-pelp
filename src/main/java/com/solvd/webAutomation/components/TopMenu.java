package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;

import com.solvd.webAutomation.pages.desktop.CartPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class TopMenu extends AbstractPage {

    @FindBy(css = "a.nav-link[href='index.html']")
    private WebElement homeButton;

    @FindBy(css = "a.nav-link[data-target='#exampleModal']")
    private WebElement contactButton;

    @FindBy(css = "a.nav-link[data-target='#videoModal']")
    private WebElement aboutUsButton;

    @FindBy(css = "a.nav-link[href='cart.html']")
    private WebElement cartButton;

    @FindBy(css = "a.nav-link#login2")
    private WebElement logInButton;

    @FindBy(css = "a.nav-link#signin2")
    private WebElement signUpButton;

    @FindBy(css = "#exampleModal .close")
    private WebElement contactCloseButton;

    @FindBy(css = "#videoModal .close")
    private WebElement aboutUsCloseButton;

    @FindBy(css = "#logInModal .close")
    private WebElement logInCloseButton;

    @FindBy(css = "#signInModal .close")
    private WebElement signUpCloseButton;

    @FindBy(css = "a[id='nava'] img")
    private WebElement imageIndicator;

    private final Map<MenuItem, WebElement> menuButtons ;

    private final Map<MenuItem, WebElement> closeButtons ;

    public TopMenu(WebDriver driver) {
        super(driver);
        menuButtons = Map.of(
                MenuItem.HOME, homeButton,
                MenuItem.CONTACT, contactButton,
                MenuItem.ABOUT_US, aboutUsButton,
                MenuItem.CART, cartButton,
                MenuItem.LOG_IN, logInButton,
                MenuItem.SIGN_UP, signUpButton
        );
        closeButtons = Map.of(
                MenuItem.CONTACT, contactCloseButton,
                MenuItem.ABOUT_US, aboutUsCloseButton,
                MenuItem.LOG_IN, logInCloseButton,
                MenuItem.SIGN_UP, signUpCloseButton
        );
    }

    @Override
    protected WebElement getPageLoadedIndicator() {
        return imageIndicator;
    }

    public void click(MenuItem item) {
        click(menuButtons.get(item), item.getName());
    }

    public void clickClose(MenuItem item) {
        WebElement closeButton = closeButtons.get(item);
        if (closeButton != null) {
            click(closeButton, item.getName().substring(4) + " Close");
        }
    }

    public boolean isVisible(MenuItem item) {
        boolean result = false;

        if (closeButtons.containsKey(item)) {
            result = isVisible(closeButtons.get(item));
        } else {
            switch (item) {
                case HOME -> result = driver.getCurrentUrl().contains("index.html");
                case CART -> result = driver.getCurrentUrl().contains("cart.html");
            }
        }
        return result;
    }

    public boolean isModalVisible(MenuItem item) {
        WebElement closeButton = closeButtons.get(item);
        if (closeButton == null) {
            throw new IllegalArgumentException("Menu item has no modal: " + item);
        }
        return isVisible(closeButton);
    }

    public boolean isPageOpened(MenuItem item) {
        return switch (item) {
            case HOME -> new HomePage(driver).isPageVisible();
            case CART -> new CartPage(driver).isPageVisible();
            default -> throw new IllegalArgumentException(
                    "Menu item does not represent a page: " + item);
        };
    }

    public enum MenuItem {
        HOME("Top Menu Home"),
        CONTACT("Top Menu Contact"),
        ABOUT_US("Top Menu About Us"),
        CART("Top Menu Cart"),
        LOG_IN("Top Menu Log In"),
        SIGN_UP("Top Menu Sign Up");

        private final String name;

        MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    //Test flow methods

    public CartPage goToCartPage() {
        click(MenuItem.CART);
        CartPage cartPage = new CartPage(driver);
        cartPage.waitUntilPageIsReady();//just added
        cartPage.waitUntilCartLoadsProducts();
        return cartPage;
    }

    public HomePage goToHomePage() {
        click(MenuItem.HOME);
        HomePage homePage = new HomePage(driver);
        homePage.waitUntilPageIsReady();
        return homePage;
    }

    public AboutUsModal openAboutUsModal() {
        click(MenuItem.ABOUT_US);
        AboutUsModal aboutUsModal = new AboutUsModal(driver);
        aboutUsModal.waitUntilPageIsReady();
        return aboutUsModal;
    }

    public SignUpModal openSignUpModal() {
        click(MenuItem.SIGN_UP);
        SignUpModal signUpModal = new SignUpModal(driver);
        signUpModal.waitUntilPageIsReady();
        return signUpModal;
    }

    public ContactModal openContactModal() {
        click(MenuItem.CONTACT);
        ContactModal contactModal = new ContactModal(driver);
        contactModal.waitUntilPageIsReady();
        return contactModal;
    }

    public LogInModal openLogInModal() {
        click(MenuItem.LOG_IN);
        LogInModal logInModal = new LogInModal(driver);
        logInModal.waitUntilPageIsReady();
        return logInModal;
    }

}
