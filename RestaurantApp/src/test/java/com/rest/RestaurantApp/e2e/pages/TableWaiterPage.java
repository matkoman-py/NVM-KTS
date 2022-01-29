package com.rest.RestaurantApp.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TableWaiterPage {

    private WebDriver driver;

    @FindBy(id = "demoCanvas")
    private WebElement canvas;

    @FindBy(id = "create-order-form")
    private WebElement orderForm;

    @FindBy(id = "add-button")
    private List<WebElement> addArticleButtons;

    @FindBy(id = "quantity-confirm-btn")
    private WebElement quantityConfirmButton;

    @FindBy(id = "article-quantity")
    private WebElement articleQuantityInput;

    @FindBy(id = "add-articles-button")
    private WebElement addArticlesButton;

    @FindBy(id = "input-pin")
    private WebElement pinInput;

    @FindBy(id = "confirm-pin-button")
    private WebElement confirmPinButton;

    @FindBy(id = "message")
    private WebElement messageBox;

    @FindBy(xpath = "//*[@id='added-articles']/*[@id='pr_id_6']/div/table/tbody/tr")
    private List<WebElement> addedArticles;

    public TableWaiterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickTable() throws InterruptedException {
        Thread.sleep(500);
        WebElement canvas = getCanvas();

        Actions action = new Actions(driver);

        action.moveToElement(canvas, 20, 20).click().perform();
    }

    public void selectQuantity(String quantity) {
        WebElement quantityConfirmButton = getQuantityConfirmButton();
        WebElement quantityInput = getArticleQuantityInput();

        quantityInput.clear();
        quantityInput.sendKeys(quantity);

        quantityConfirmButton.click();
    }

    public void insertPincode(String pincode) {
        WebElement pinInput = getPinInput();
        WebElement confirmPinButton = getConfirmPinButton();

        pinInput.clear();
        pinInput.sendKeys(pincode);

        confirmPinButton.click();
    }

    public int countAddedArticles() {
        List<WebElement> addedArticles = getAddedArticles();
        return addedArticles.size();
    }

    public void addArticlesToOrder() {
        WebElement addArticlesButton = getAddArticlesButton();
        addArticlesButton.click();
    }

    public void addArticle(int index) {
        List<WebElement> addArticleButtons = getAddArticleButtons();
        addArticleButtons.get(index).click();
    }

    public void removeArticle(int index) {
        List<WebElement> removeArticleButtons = getRemoveArticleButtons();
        removeArticleButtons.get(index).click();
    }

    private List<WebElement> getAddArticleButtons() {
        return Utilities.visibilityWait(driver, By.id("add-button"), 10);
    }

    private List<WebElement> getRemoveArticleButtons() {
        return Utilities.visibilityWait(driver, By.id("remove-button"), 10);
    }

    private WebElement getCanvas() {
        return Utilities.visibilityWait(driver, canvas, 10);
    }

    private WebElement getQuantityConfirmButton() {
        return Utilities.visibilityWait(driver, quantityConfirmButton, 10);
    }

    public WebElement getArticleQuantityInput() {
        return Utilities.visibilityWait(driver, articleQuantityInput, 10);
    }

    public WebElement getAddArticlesButton() {
        return Utilities.visibilityWait(driver, addArticlesButton, 10);
    }

    public WebElement getConfirmPinButton() {
        return Utilities.visibilityWait(driver, confirmPinButton, 10);
    }

    public WebElement getPinInput() {
        return Utilities.visibilityWait(driver, pinInput, 10);
    }

    public boolean isOrderFormVisible() {
        return orderForm.isDisplayed();
    }

    public WebElement getMessageBox() {
        return Utilities.visibilityWait(driver, messageBox, 10);
    }

    public boolean displayedMessage(String message) {
        return Utilities.textWait(driver, this.messageBox, message, 10);
    }

    public List<WebElement> getAddedArticles() {
        return Utilities.visibilityWait(driver, By.xpath("//*[@id='added-articles']/div/div/table/tbody/tr"), 10);
    }
}
