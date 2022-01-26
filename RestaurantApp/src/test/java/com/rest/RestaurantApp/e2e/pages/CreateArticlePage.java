package com.rest.RestaurantApp.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateArticlePage {
	
	private WebDriver driver;
	
	@FindBy(id = "name-input")
	private WebElement nameInput;
	
	@FindBy(id = "description-input")
	private WebElement descriptionInput;
	
	@FindBy(id = "create-article")
	private WebElement createArticleButton;
	
	@FindBy(id = "type-input")
	private WebElement typeInput;
	
	@FindBy(css = "p-dropdownitem li")
	private WebElement firstSelectItem;
	
	@FindBy(css = "#making-price input")
	private WebElement makingPriceInput;
	
	@FindBy(css = "#selling-price input")
	private WebElement sellingPriceInput;
	
	@FindBy(id = "ingredient-input")
	private WebElement ingredientInput;
	
	@FindBy(css = "p-multiselectitem li")
	private WebElement firstIngredient;
	
	public CreateArticlePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setTypeInputToDessert() {
		Utilities.visibilityWait(driver, this.typeInput, 10).click();
		Utilities.visibilityWait(driver, this.firstSelectItem, 10).click();
	}
	
	public void selectIngredient() {
		Utilities.visibilityWait(driver, this.ingredientInput, 10).click();
		Utilities.visibilityWait(driver, this.firstIngredient, 10).click();
	}
	
	public WebElement getDescriptionInput() {
		return Utilities.visibilityWait(driver, this.descriptionInput, 10);
	}
	
	public void createArticleClick() {
		Utilities.visibilityWait(driver, this.createArticleButton, 10).click();
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
	
	public WebElement getMakingPriceInput() {
		return Utilities.visibilityWait(driver, this.makingPriceInput, 10);
	}
	
	public void setMakingPriceInput(String value) {
		WebElement el = getMakingPriceInput();
		el.clear();
		el.sendKeys(value);
	}
	
	public WebElement getSellingPriceInput() {
		return Utilities.visibilityWait(driver, this.sellingPriceInput, 10);
	}
	
	public void setSellingPriceInput(String value) {
		WebElement el = getSellingPriceInput();
		el.clear();
		el.sendKeys(value);
	}
}
