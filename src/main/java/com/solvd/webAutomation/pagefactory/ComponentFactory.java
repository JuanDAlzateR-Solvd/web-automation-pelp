package com.solvd.webAutomation.pagefactory;

import com.solvd.webAutomation.components.AbstractComponent;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static <T extends AbstractComponent> T create(Class<T> componentType, WebDriver driver, SearchContext root) {
        try {
            return componentType.getDeclaredConstructor(WebDriver.class, SearchContext.class)
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
