package com.solvd.webAutomation.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartItemComponent extends AbstractComponent {

    @FindBy(css = "a[onclick*='deleteItem']")
    private WebElement deleteButton;

    @FindBy(css = "td") // ".card-text"
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
        return getText(DataItem.TITLE);
    }

    public String getPrice() {
        return getText(DataItem.PRICE);
    }

    public String getText() {
        return getText(root, "Product Component");
    }

    public void deleteProduct() {
        click(deleteButton, "Delete Button");
    }

    protected String getText(DataItem item) {
        return getText(getElementFrom(item), item.getName());
    }

    protected WebElement getElementFrom(DataItem item) {
        return tableDataList.get(item.getColumnIndex());
    }

    public enum DataItem {
        PICTURE("Product Picture", 0),
        TITLE("Product Title", 1),
        PRICE("Product Price", 2),
        DELETE("Product Delete", 3);

        private final String name;
        private final int columnIndex;

        DataItem(String name, int lineIndex) {
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