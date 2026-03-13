package com.solvd.webAutomation.pagefactory;

import com.solvd.webAutomation.components.AbstractComponent;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.List;

public class ComponentFieldDecorator extends DefaultFieldDecorator {

    private final WebDriver driver;
    private final ElementLocatorFactory listLocatorFactory;

    public ComponentFieldDecorator(ElementLocatorFactory factory, SearchContext searchContext, WebDriver driver) {
        super(factory);
        this.driver = driver;
        this.listLocatorFactory = new DefaultElementLocatorFactory(searchContext);
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (isDecoratableComponent(field.getType())) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) {
                return null;
            }
            return ComponentFactory.create(
                    field.getType().asSubclass(AbstractComponent.class),
                    driver,
                    proxyForLocator(loader, locator)
            );
        }

        if (isDecoratableComponentList(field)) {
            ElementLocator locator = listLocatorFactory.createLocator(field);
            if (locator == null) {
                return null;
            }

            Class<? extends AbstractComponent> componentType = getListComponentType(field);
            return Proxy.newProxyInstance(
                    loader,
                    new Class[]{List.class},
                    new LocatingComponentListHandler<>(locator, driver, componentType)
            );
        }

        return super.decorate(loader, field);
    }

    private boolean isDecoratableComponent(Class<?> fieldType) {
        return AbstractComponent.class.isAssignableFrom(fieldType);
    }

    private boolean isDecoratableComponentList(Field field) {
        if (!List.class.isAssignableFrom(field.getType()) || !(field.getGenericType() instanceof ParameterizedType)) {
            return false;
        }

        return getListComponentType(field) != null;
    }

    private Class<? extends AbstractComponent> getListComponentType(Field field) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        if (!(parameterizedType.getActualTypeArguments()[0] instanceof Class<?> listItemType)) {
            return null;
        }

        if (!AbstractComponent.class.isAssignableFrom(listItemType)) {
            return null;
        }

        return listItemType.asSubclass(AbstractComponent.class);
    }

}
