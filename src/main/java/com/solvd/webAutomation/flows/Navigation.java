package com.solvd.webAutomation.flows;

import com.solvd.webAutomation.components.*;
import com.solvd.webAutomation.pages.desktop.CartPage;
import com.solvd.webAutomation.pages.desktop.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Navigation extends AbstractComponent {

    @FindBy(css = "#exampleModal")
    private WebElement contactContainer;

    @FindBy(css = "#videoModal")
    private WebElement aboutUsContainer;

    @FindBy(css = "#logInModal")
    private WebElement logInContainer;

    @FindBy(css = "#signInModal")
    private WebElement signUpContainer;

    private final TopMenu topMenu;

    public Navigation(WebDriver driver, WebElement root, TopMenu topMenu) {
        super(driver, root);
        this.topMenu = topMenu;
    }

    @Override
    protected WebElement getComponentLoadedIndicator() {
        return logInContainer; // cualquier elemento estable del DOM
    }

    public LogInModal openLogInModal() {
        topMenu.clickLogIn();
        LogInModal modal = new LogInModal(driver, logInContainer);
        modal.waitUntilComponentIsReady();
        return modal;
    }

    public SignUpModal openSignUpModal() {
        topMenu.clickSignUp();
        SignUpModal modal = new SignUpModal(driver, signUpContainer);
        modal.waitUntilComponentIsReady();
        return modal;
    }

    public ContactModal openContactModal() {
        topMenu.clickContact();
        ContactModal modal = new ContactModal(driver, contactContainer);
        modal.waitUntilComponentIsReady();
        return modal;
    }

    public AboutUsModal openAboutUsModal() {
        topMenu.clickAboutUs();
        AboutUsModal modal = new AboutUsModal(driver, aboutUsContainer);
        modal.waitUntilComponentIsReady();
        return modal;
    }

    public CartPage goToCartPage() {
        topMenu.clickCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.waitUntilPageIsReady();
        cartPage.waitUntilCartLoadsProducts();
        return cartPage;
    }

    public HomePage goToHomePage() {
        topMenu.clickHome();
        HomePage homePage = new HomePage(driver);
        homePage.waitUntilPageIsReady();
        return homePage;
    }

}