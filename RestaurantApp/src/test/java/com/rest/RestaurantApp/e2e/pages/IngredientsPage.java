package com.rest.RestaurantApp.e2e.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import ch.qos.logback.classic.pattern.Util;

public class IngredientsPage {
	
	private WebDriver driver;
	
	@FindBy(id = "create-name")
	private WebElement createUpdateName;
	
	@FindBy(xpath = "//*[@id=\"checkbox-allergen\"]/div/div[2]")
	private WebElement checkIsAllergen;
	
	@FindBy(id="create-button")
	private WebElement createBtn;
	
	@FindBy(id="update-button")
	private WebElement updateBtn;
	
	@FindBy(id="delete-button")
	private WebElement deleteBtn;
	
	@FindBy(id="search-button")
	private WebElement searchBtn;
	
	@FindBy(xpath = "//*[@id=\"pr_id_4\"]/p-paginator/div/span[1]")
	private WebElement ingredientCount;
	
	@FindBy(tagName = "p-dropdown")
	private WebElement dropdown;
	
	@FindBy(id="search-name")
	private WebElement searchInput;
	
	
	
	
	
	

	public IngredientsPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public void clickOption(int optionNum) {
		List<WebElement> options = Utilities.visibilityWait(driver, By.tagName("p-dropdownitem"), 10);
		options.get(optionNum).click();
		
	}
	
	public void clickDropdown() {
		Utilities.visibilityWait(driver, dropdown, 10).click();
	}
	
	public void clickCheckbox() {
		Utilities.visibilityWait(driver, this.checkIsAllergen, 10).click();
	}
	
	public int getRowCount() {
		return Utilities.visibilityWait(driver, By.tagName("tr"), 10).size() - 1; //header je prvi red
	}
	
	public WebElement getIngredientCountString() {
		return Utilities.visibilityWait(driver, this.ingredientCount, 10);
	}
	
	public boolean checkIfIngredientCountChanged(String text) {
		return Utilities.textWait(driver, getIngredientCountString(), text, 10);
	}
	
	
	public List<WebElement> getIngredients() {
		return Utilities.visibilityWait(driver, By.tagName("tr"), 10);
	}
	public int getIngredientCount() {
		return Integer.parseInt(Utilities.visibilityWait(driver, ingredientCount, 10).getText().split(" ")[5]);
	}
	public void clickCreateBtn() {
		Utilities.clickableWait(driver, createBtn, 5).click();
	}
	
	public void clickDeleteBtn() {
		Utilities.clickableWait(driver, deleteBtn, 5).click();
	}
	
	public void clickUpdateBtn() {
		Utilities.clickableWait(driver, updateBtn, 5).click();
	}
	
	public void clickSearchBtn() {
		Utilities.clickableWait(driver, searchBtn, 5).click();
	}
	
	public WebElement getCreateUpdateName() {
		return Utilities.visibilityWait(driver, this.createUpdateName, 5);
	}
	
	public WebElement getSearchInput() {
		return Utilities.visibilityWait(driver, this.searchInput, 5);
	}
	
	public void setSearchInput(String text) {
		WebElement el = getSearchInput();
		el.clear();
		el.sendKeys(text);
	}
	
	public void setCreateUpdateName(String text) {
		WebElement el = getCreateUpdateName();
		el.clear();
		el.sendKeys(text);
	}
	
	public void clickOnRow(int rowNum) {
		Actions act = new Actions(driver);
		act.doubleClick(getIngredients().get(rowNum)).perform();
	}

	
	
}
