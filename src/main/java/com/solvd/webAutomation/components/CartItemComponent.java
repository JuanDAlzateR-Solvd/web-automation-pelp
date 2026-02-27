package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartItemComponent extends AbstractComponent {

    @FindBy(css = "a[onclick*='deleteItem']")
    private WebElement deleteButton;

    @FindBy(css = ".card-text")
    private List<WebElement> tableDataList;

    @FindBy(css = "img")
    private WebElement imageIndicator;

    public CartItemComponent(WebDriver driver, WebElement root) {
        super(driver, root);
    }


    @Override
    public WebElement getComponentLoadedIndicator() {
        return imageIndicator;
    }

    public String getTitle() {
        return getText(dataItem.TITLE);
    }
    public String getPrice() {
        return getText(dataItem.PRICE);
    }

    public String getText() {
        return getText(root, "Product Component"); //It doesn't work with root -> used title.
    }

    public void deleteProduct() {
        click(deleteButton, "Delete Button");
    }

    protected String getText(dataItem item) {
        return getText(getElementFrom(item), item.getName());
    }

    protected WebElement getElementFrom(dataItem item) {
        return tableDataList.get(item.getColumnIndex());
    }

    public enum dataItem {
        PICTURE("Product Picture", 0),
        TITLE("Product Title", 1),
        PRICE("Product Price", 2),
        DELETE("Product Delete", 3);

        private final String name;
        private final int columnIndex;

        dataItem(String name, int lineIndex) {
            this.name = name;
            this.columnIndex = lineIndex;
        }

        public String getName() {
            return name;
        }

        public int getColumnIndex() {
            return columnIndex;
        }
    }
}