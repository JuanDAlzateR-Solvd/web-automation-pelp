package com.solvd.webAutomation.components;

import com.solvd.webAutomation.pages.common.AbstractPage;
import org.openqa.selenium.*;

public abstract class AbstractComponent {
    protected WebElement root;

    public AbstractComponent(WebElement root){
        this.root=root;
    }

    protected abstract WebElement getComponentLoadedIndicator();

    public WebElement getRoot() {
        return root;
    }

}
