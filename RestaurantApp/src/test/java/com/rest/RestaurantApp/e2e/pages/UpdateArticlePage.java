package com.rest.RestaurantApp.e2e.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateArticlePage {
	
	private WebDriver driver;
	
	@FindBy(id = "name-input")
	private WebElement nameInput;
	
	@FindBy(id = "description-input")
	private WebElement descriptionInput;
	
	@FindBy(id = "update-article")
	private WebElement updateArticleButton;
	
	public UpdateArticlePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement getDescriptionInput() {
		return Utilities.visibilityWait(driver, this.descriptionInput, 10);
	}
	
	public void updateArticleClick() {
		Utilities.visibilityWait(driver, this.updateArticleButton, 10).click();
	}
	
	public void setDescriptionInput(String value) {
		WebElement el = getDescriptionInput();
		el.clear();
		el.sendKeys(value);
	}
	
	public WebElement getNameInput() {
		return Utilities.visibilityWait(driver, this.nameInput, 10);
	}
	
	public void setNameInput(String value) {
		WebElement el = getNameInput();
		el.clear();
		el.sendKeys(value);
	}
	
}
