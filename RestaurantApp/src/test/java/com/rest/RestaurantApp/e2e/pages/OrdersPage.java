package com.rest.RestaurantApp.e2e.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrdersPage {
	
	private WebDriver driver;
	
	@FindBy(className =  "p-button-info")
	private WebElement seeOrderBtn;
	
	@FindBy(className =  "p-toast-detail")
	private WebElement orderNotSelectedError;

	@FindBy(className =  "p-selectable-row")
	private List<WebElement> orderRows;

	public OrdersPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public void clickSeeOrderBtn() {
		Utilities.visibilityWait(driver, seeOrderBtn, 5).click();
	}
	
	public String isOrderNotSelectedError() {
		return Utilities.visibilityWait(driver, orderNotSelectedError, 5).getText();
	}
	
	public void selectOrder() {
		Utilities.visibilityWait(driver, orderRows.get(0), 5).click();
	}
}
