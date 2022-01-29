package com.rest.RestaurantApp.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class EditTablePage {

    private WebDriver driver;

    @FindBy(id = "demoCanvas")
    private WebElement canvas;

    @FindBy(id = "add-button")
    private WebElement addTableButton;

    @FindBy(id = "save-button")
    private WebElement saveButton;

    @FindBy(id = "remove-button")
    private WebElement removeButton;

    @FindBy(id = "seat-number")
    private WebElement seatNumberMenu;

    @FindBy(id = "message")
    private WebElement messageBox;

    public EditTablePage(WebDriver driver) { this.driver = driver; }

    public void moveObjectOnCanvasByCoordinates(int x, int y, int dest_x, int dest_y) throws InterruptedException {
        WebElement canvas = getCanvas();

        Actions action = new Actions(driver);

        action.moveToElement(canvas, x, y).clickAndHold().moveToElement(canvas, dest_x, dest_y).release().perform();

    }

    public void openDropdown() {
        WebElement el = getSeatNumberMenu();
        el.click();

        Utilities.visibilityWait(driver, By.tagName("p-dropdownitem"), 10).get(0).click();
    }

    public void addTable() {
        WebElement addButton = getAddTableButton();

        addButton.click();
    }

    public void removeTable(int x, int y) {
        WebElement canvas = getCanvas();
        WebElement removeButton = getRemoveButton();

        Actions action = new Actions(driver);

        action.moveToElement(canvas, x, y).click().perform();

        removeButton.click();
    }

    public void saveLayout() {
        WebElement saveButton = getSaveButton();

        saveButton.click();
    }

    public WebElement getSeatNumberMenu() {
        return Utilities.visibilityWait(driver, this.seatNumberMenu, 10);
    }

    public WebElement getCanvas() {
        return Utilities.visibilityWait(driver, this.canvas, 10);
    }

    public WebElement getRemoveButton() {
        return Utilities.visibilityWait(driver, removeButton, 10);
    }

    public WebElement getAddTableButton() {
        return Utilities.visibilityWait(driver, addTableButton, 10);
    }

    public WebElement getSaveButton() {
        return Utilities.visibilityWait(driver, saveButton, 10);
    }

    public WebElement getMessageBox() {
        return Utilities.visibilityWait(driver, messageBox, 10);
    }

    public boolean displayedMessage(String message) {
        return Utilities.textWait(driver, this.messageBox, message, 10);
    }
}
