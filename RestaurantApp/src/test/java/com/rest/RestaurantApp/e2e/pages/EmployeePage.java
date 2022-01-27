package com.rest.RestaurantApp.e2e.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import ch.qos.logback.classic.pattern.Util;

public class EmployeePage {
	
	private WebDriver driver;
	
	@FindBy(id = "employee-name")
	private WebElement empName;
	
	@FindBy(id = "employee-surname")
	private WebElement empSurname;
	
	@FindBy(id = "employee-email")
	private WebElement empEmail;
	
	@FindBy(id = "withoutgrouping")
	private WebElement empPin;
	
	@FindBy(id = "currency-germany")
	private WebElement empSalary;
	
	@FindBy(xpath = "/html/body/app-root/div/div[2]/div/app-employees/div/div[1]/div/div[7]/p-calendar/span/input")
	private WebElement empBirthday;
	
	@FindBy(id = "name-search")
	private WebElement nameSearch;
	
	@FindBy(id = "surname-search")
	private WebElement surnameSearch;
	
	@FindBy(id = "email-search")
	private WebElement emailSearch;
	
	
	
	@FindBy(id = "create-button")
	private WebElement createBtn;
	
	@FindBy(id = "update-button")
	private WebElement updateBtn;
	
	@FindBy(id = "delete-button")
	private WebElement deleteBtn;
	
	@FindBy(id = "search-button")
	private WebElement searchBtn;
	
	@FindBy(xpath = "//*[@id=\"pr_id_4\"]/p-paginator/div/span[1]")
	private WebElement employeeCount;
	
	@FindBy(xpath = "/html/body/app-root/div/div[2]/div/app-employees/div/div[1]/div/div[4]/p-dropdown/div")
	private WebElement dropdown;
	
	public int getEmployeeCount() {
		return Integer.parseInt(Utilities.visibilityWait(driver, employeeCount, 10).getText().split(" ")[5]);
	}
	
	
	public void clickCreateBtn() {
		Utilities.clickableWait(driver, this.createBtn, 10).click();
	}
	
	public void clickDeleteBtn() {
		Utilities.clickableWait(driver, this.deleteBtn, 10).click();
	}
	
	public void clickUpdateBtn() {
		Utilities.clickableWait(driver, this.updateBtn, 10).click();
	}
	
	public void clickSearchBtn() {
		Utilities.clickableWait(driver, this.searchBtn, 10).click();
	}
	
	public List<WebElement> getEmployees() {
		return Utilities.visibilityWait(driver, By.tagName("tr"), 10);
	}
	
	public void clickOnRow(int rowNum) {
		Actions act = new Actions(driver);
		act.doubleClick(getEmployees().get(rowNum)).perform();
	}
	
	
	public WebElement getDropdown() {
		return Utilities.visibilityWait(driver, this.dropdown, 10);
	}
	
	public void selectRole(int roleNum) {
		WebElement el = getDropdown();
		el.click();
		Utilities.visibilityWait(driver, By.tagName("p-dropdownitem"), 10).get(roleNum).click();
	}
	
	public WebElement getEmpName() {
		return Utilities.visibilityWait(driver, this.empName, 10);
	}
	
	public WebElement getEmpSurname() {
		return Utilities.visibilityWait(driver, this.empSurname, 10);
	}
	
	public WebElement getEmpSalary() {
		return Utilities.visibilityWait(driver, this.empSalary, 10);
	}
	
	public WebElement getEmpEmail() {
		return Utilities.visibilityWait(driver, this.empEmail, 10);
	}
	
	public WebElement getEmpBirthday() {
		return Utilities.visibilityWait(driver, this.empBirthday, 10);
	}
	
	public WebElement getEmpPin() {
		return Utilities.visibilityWait(driver, this.empPin, 10);
	}
	
	public WebElement getNameSearch() {
		return Utilities.visibilityWait(driver, this.nameSearch, 10);
	}
	
	public WebElement getSurnameSearch() {
		return Utilities.visibilityWait(driver, this.surnameSearch, 10);
	}
	
	public WebElement getEmailSearch() {
		return Utilities.visibilityWait(driver, this.emailSearch, 10);
	}
	
	public void setNameSearch(String text) {
		WebElement el = getNameSearch();
		el.clear();
		el.sendKeys(text);
	}
	
	public void setSurnameSearch(String text) {
		WebElement el = getSurnameSearch();
		el.clear();
		el.sendKeys(text);
	}
	
	public void setEmailSearch(String text) {
		WebElement el = getEmailSearch();
		el.clear();
		el.sendKeys(text);
	}
	
	
	
	public void setEmpName(String text) {
		WebElement el = getEmpName();
		el.clear();
		el.sendKeys(text);
	}
	
	public void setEmpSurname(String text) {
		WebElement el = getEmpSurname();
		el.clear();
		el.sendKeys(text);
	}
	
	public void setEmpSalary(String text) {
		WebElement el = getEmpSalary();
		el.clear();
		el.sendKeys(text);
	}
	
	public void setEmpEmail(String text) {
		WebElement el = getEmpEmail();
		el.clear();
		el.sendKeys(text);
	}
	
	public void setEmpBirthday(String text) {
		WebElement el = getEmpBirthday();
		el.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		el.sendKeys(text);
		el.sendKeys(Keys.ENTER);
	}
	
	public void setEmpPin(String text) {
		WebElement el = getEmpPin();
		el.clear();
		el.sendKeys(text);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public EmployeePage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	

	
	
}
