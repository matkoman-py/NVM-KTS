package com.rest.RestaurantApp.e2e.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ArticlesTablePage {
	
	private WebDriver driver;
	
	@FindBy(id = "create-article")
	private WebElement createArticleButton;
	
	@FindBy(id = "delete-article")
	private WebElement deleteArticleButton;
	
	@FindBy(id = "update-article")
	private WebElement updateArticleButton;
	
	@FindBy(id = "submit-search")
	private WebElement submitSearchButton;
	
	@FindBy(id = "name-input")
	private WebElement nameInput;
	
	@FindBy(id = "type-input")
	private WebElement typeInput;
	
	@FindBy(css = "p-dropdownitem li")
	private WebElement firstSelectItem;
	
	public ArticlesTablePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public int getNumberOfArticles() throws InterruptedException {
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(1000);
		List<WebElement> list = Utilities.visibilityWait(driver, By.cssSelector("tbody tr"), 10);
		return list.size();
	}
	
	public String getUpdatedArticleName() {
		WebElement updatedArticle = Utilities.visibilityWait(driver, By.id("1"), 10).get(0);
		WebElement name = updatedArticle.findElements(By.cssSelector("td")).get(0);
		return name.getText();
	}
	
	public String getCreatedArticleName() {
		WebElement updatedArticle = Utilities.visibilityWait(driver, By.id("11"), 10).get(0);
		WebElement name = updatedArticle.findElements(By.cssSelector("td")).get(0);
		return name.getText();
	}
	
	public String getUpdatedArticleDescription() {
		WebElement updatedArticle = Utilities.visibilityWait(driver, By.id("1"), 10).get(0);
		WebElement description = updatedArticle.findElements(By.cssSelector("td")).get(4);
		return description.getText();
	}
	
	public void setTypeInputToDessert() {
		Utilities.visibilityWait(driver, this.typeInput, 10).click();
		Utilities.visibilityWait(driver, this.firstSelectItem, 10).click();
	}
	
	public WebElement getNameInput() {
		return Utilities.visibilityWait(driver, this.nameInput, 10);
	}
	
	public void setNameInput(String value) {
		WebElement el = getNameInput();
		el.clear();
		el.sendKeys(value);
	}
	
	public void submitSearch() {
		Utilities.visibilityWait(driver, this.submitSearchButton, 10).click();
	}
	
	public void updateArticleClick() {
		Utilities.visibilityWait(driver, this.updateArticleButton, 10).click();
	}
	
	public void deleteArticleClick() {
		Utilities.visibilityWait(driver, this.deleteArticleButton, 10).click();
	}
	
	public void createArticleClick() {
		Utilities.visibilityWait(driver, this.createArticleButton, 10).click();
	}
	
	public void selectFirstArticle() {
		try {
			WebElement firstArticle = Utilities.visibilityWait(driver, By.id("1"), 10).get(0);
			firstArticle.click();
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			WebElement firstArticle = Utilities.visibilityWait(driver, By.id("1"), 10).get(0);
			firstArticle.click();
		}
		
	}
	
	public void waitForUrlToBe(String url) {
		Utilities.urlWait(driver, url, 10);
	}
	
}
