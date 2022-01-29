package com.rest.RestaurantApp.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ViewOrderPage {
    private WebDriver driver;

    @FindBy(id = "finish-order")
    private WebElement finishOrderButton;

    @FindBy(id = "quantity-confirm-btn")
    private WebElement quantityConfirmButton;

    @FindBy(id = "add-article")
    private WebElement addArticlesButton;

    @FindBy(id = "add-all-articles")
    private WebElement addAllArticlesButton;

    @FindBy(id = "proceed-button")
    private WebElement proceedButton;

    @FindBy(id = "input-pin")
    private WebElement pinInput;

    @FindBy(id = "confirm-pin-button")
    private WebElement confirmPinButton;

    public ViewOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addArticle(int index) {
        getAddArticlesButton().click();
        List<WebElement> addArticleButtons = getAddArticleButtons();
        addArticleButtons.get(index).click();
        getQuantityConfirmButton().click();
        getAddAllArticlesButton().click();
    }

    private WebElement getAddAllArticlesButton() {
        return Utilities.visibilityWait(driver, addAllArticlesButton, 10);
    }

    public int countAddedArticles() {
        List<WebElement> addedArticles = getAddedArticles();
        return addedArticles.size();
    }

    public void removeArticle(int index) throws InterruptedException {
        Thread.sleep(1000);
        List<WebElement> removeArticlesButtons = getRemoveArticleButtons();
        removeArticlesButtons.get(index).click();
    }

    public void finishOrder() {
        getFinishOrderButton().click();
        getProceedButton().click();
        insertPincode("1234");
    }

    public void insertPincode(String pincode) {
        WebElement pinInput = getPinInput();
        WebElement confirmPinButton = getConfirmPinButton();

        pinInput.clear();
        pinInput.sendKeys(pincode);

        confirmPinButton.click();
    }

    private WebElement getProceedButton() {
        return Utilities.visibilityWait(driver, proceedButton, 10);
    }

    private List<WebElement> getAddArticleButtons() {
        return Utilities.visibilityWait(driver, By.id("add-button"), 10);
    }

    private List<WebElement> getRemoveArticleButtons() {
        try {
            return Utilities.visibilityWait(driver, By.id("remove-button"), 10);
        } catch (Exception e) {
            return Utilities.visibilityWait(driver, By.id("remove-button"), 10);
        }
    }

    public WebElement getFinishOrderButton() {
        return Utilities.visibilityWait(driver, finishOrderButton, 10);
    }

    private WebElement getQuantityConfirmButton() {
        return Utilities.visibilityWait(driver, quantityConfirmButton, 10);
    }

    public WebElement getAddArticlesButton() {
        return Utilities.visibilityWait(driver, addArticlesButton, 10);
    }

    public boolean waitForUrlToBe(String url) {
        return Utilities.urlWait(driver, url, 10);
    }

    private List<WebElement> getAddedArticles() {
        try {
            return Utilities.visibilityWait(driver, By.xpath("//table/tbody/tr"), 10);
        } catch (Exception e) {
            return Utilities.visibilityWait(driver, By.xpath("//table/tbody/tr"), 10);
        }
    }

    public WebElement getConfirmPinButton() {
        return Utilities.visibilityWait(driver, confirmPinButton, 10);
    }

    public WebElement getPinInput() {
        return Utilities.visibilityWait(driver, pinInput, 10);
    }
}
