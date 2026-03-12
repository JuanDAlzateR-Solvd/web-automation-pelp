package com.solvd.webAutomation.pagefactory;

import com.solvd.webAutomation.components.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static <T extends AbstractComponent> T create(Class<T> componentType, WebDriver driver, WebElement root) {
        try {
            return componentType.getDeclaredConstructor(WebDriver.class, WebElement.class)
                    .newInstance(driver, root);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(
                    "Failed to create component " + componentType.getSimpleName()
                            + ". Expected a constructor (WebDriver, WebElement).",
                    e
            );
        }
    }
}

