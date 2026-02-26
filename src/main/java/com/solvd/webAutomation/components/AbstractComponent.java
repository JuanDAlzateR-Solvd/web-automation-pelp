package com.solvd.webAutomation.components;

import org.openqa.selenium.*;

public abstract class AbstractComponent {
    protected WebElement root;

    public AbstractComponent(WebElement root){
        this.root=root;
    }

}
