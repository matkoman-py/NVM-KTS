package com.rest.RestaurantApp.e2e.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderedArticlesPage {
	
	private WebDriver driver;
	
	@FindBy(css  =  "p-card p-button")
	private WebElement takeArticleBtns;
	
	@FindBy(tagName = "input")
	private List<WebElement> inputPin;
	
	@FindBy(id = "submit-btn")
	private WebElement submitBtn;

	@FindBy(className =  "p-toast-detail")
	private WebElement toastMessage;
	
	@FindBy(className =  "p-toast-icon-close-icon")
	private WebElement modalCloseBtn;
	
	public OrderedArticlesPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public void clickTakeOrderBtn() {
		Utilities.visibilityWait(driver, takeArticleBtns, 5).click();
	}
	
	public void closeModal() {
		Utilities.visibilityWait(driver, modalCloseBtn, 5).click();
	}
	
	public void inputPin(String pin) {
		Utilities.visibilityWait(driver, inputPin.get(1), 5).click();
		inputPin.get(1).sendKeys(pin);
		Utilities.visibilityWait(driver, submitBtn, 5).click();
	}
	
	public String checkToastMessage() {
		return Utilities.visibilityWait(driver, toastMessage, 5).getText();
	}
}
