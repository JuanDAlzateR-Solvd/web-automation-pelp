package com.solvd.webAutomation.pagefactory;

import com.solvd.webAutomation.components.AbstractComponent;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class LocatingComponentListHandler<T extends AbstractComponent> implements InvocationHandler {

    private final ElementLocator locator;
    private final WebDriver driver;
    private final Class<T> componentType;

    private List<T> cachedComponents = List.of();

    public LocatingComponentListHandler(ElementLocator locator, WebDriver driver, Class<T> componentType) {
        this.locator = locator;
        this.driver = driver;
        this.componentType = componentType;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        List<T> components = resolveComponents();
        try {
            return method.invoke(components, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private List<T> resolveComponents() {
        List<WebElement> roots = locator.findElements();
        if (shouldRebuild(roots)) {
            cachedComponents = roots.stream()
                    .map(root -> ComponentFactory.create(componentType, driver, root))
                    .toList();
        }
        return cachedComponents;
    }

    private boolean shouldRebuild(List<WebElement> roots) {
        if (cachedComponents.isEmpty()) {
            return !roots.isEmpty();
        }

        if (cachedComponents.size() != roots.size()) {
            return true;
        }

        return cachedComponents.stream().anyMatch(this::hasStaleRoot);
    }

    private boolean hasStaleRoot(AbstractComponent component) {
        try {
            component.getRoot().isDisplayed();
            return false;
        } catch (StaleElementReferenceException e) {
            return true;
        }
    }
}

