package com.rest.RestaurantApp.e2e.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReportsPage {

	private WebDriver driver;
	
	@FindBy(id = "p-tabpanel-0-label")
	private WebElement yearlyReportTab;
	
	@FindBy(id = "p-tabpanel-1-label")
	private WebElement quarterReportTab;
	
	@FindBy(id = "p-tabpanel-2-label")
	private WebElement monthReportTab;
	
	@FindBy(id = "p-tabpanel-3-label")
	private WebElement dayReportTab;
	
	@FindBy(id = "p-tabpanel-4-label")
	private WebElement fromToReportTab;
	
	@FindBy(tagName = "p")
	private WebElement data;
	
	@FindBy(id = "year-1")
	private WebElement yearInputYearly;
	
	@FindBy(id = "year-2")
	private WebElement yearInputQuarter;
	
	@FindBy(id = "quarter-1")
	private WebElement quarterInput;
	
	@FindBy(id = "year-3")
	private WebElement yearInputMonth;
	
	@FindBy(id = "month-1")
	private WebElement monthInput;
	
	@FindBy(tagName = "p-calendar")
	private WebElement dateInputDay;
	
	@FindBy(className = "calendar1")
	private WebElement dateInputFrom;
	
	@FindBy(className = "calendar2")
	private WebElement dateInputTo;
	
	@FindBy(className = "p-datepicker-prev")
	private WebElement dateLeftArrow;
	
	public ReportsPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	public void clickYearlyReportTab() {
		Utilities.visibilityWait(driver, yearlyReportTab, 5).click();
	}
	
	public void clickQuarterReportTab() {
		Utilities.visibilityWait(driver, quarterReportTab, 5).click();
	}
	
	public void clickMonthReportTab() {
		Utilities.visibilityWait(driver, monthReportTab, 5).click();
	}
	
	public void clickDayReportTab() {
		Utilities.visibilityWait(driver, dayReportTab, 5).click();
	}
	
	public void clickFromToReportTab() {
		Utilities.visibilityWait(driver, fromToReportTab, 5).click();
	}
	
	public void inputYearly() {
		Utilities.visibilityWait(driver, yearInputYearly, 5).clear();
		yearInputYearly.sendKeys("2019");
		yearInputYearly.sendKeys(Keys.RETURN);
	}
	
	public void inputQuarterly() {
		Utilities.visibilityWait(driver, yearInputQuarter, 5).clear();
		yearInputQuarter.sendKeys("2019");
		
		Utilities.visibilityWait(driver, quarterInput, 5).click();
		List<WebElement> options = Utilities.visibilityWait(driver, By.tagName("p-dropdownitem"), 5);
		options.get(2).click();
	}
	
	public void inputMonthly() {
		Utilities.visibilityWait(driver, yearInputMonth, 5).clear();
		yearInputMonth.sendKeys("2019");
		
		Utilities.visibilityWait(driver, monthInput, 5).click();
		List<WebElement> options = Utilities.visibilityWait(driver, By.tagName("p-dropdownitem"), 5);
		options.get(2).click();
	}
	
	public void inputDaily() {
		Utilities.visibilityWait(driver, dateInputDay, 5).click();
		List<WebElement> options = Utilities.visibilityWait(driver, By.tagName("td"), 5);
		options.get(10).click();
	}

	public void inputFromTo() {
		Utilities.visibilityWait(driver, dateInputFrom, 10).click();
		List<WebElement> options1 = Utilities.visibilityWait(driver, By.tagName("td"), 5);
		options1.get(30).click();
		
		Utilities.visibilityWait(driver, dateInputTo, 10).click();//dateLeftArrow
		Utilities.visibilityWait(driver, dateLeftArrow, 10).click();
		dateLeftArrow.click();
		dateLeftArrow.click();
		dateLeftArrow.click();
		dateLeftArrow.click();
		dateLeftArrow.click();
		dateLeftArrow.click();
		dateLeftArrow.click();
		dateLeftArrow.click();
		List<WebElement> options2 = Utilities.visibilityWait(driver, By.tagName("td"), 5);
		options2.get(1).click();
	}
	
	public boolean getData(String text) {
//		if(Utilities.isPresent(driver, By.tagName("p"))){
//			
//			return data.getText();
//		}
//		return "";
		return Utilities.textWait(driver, data, text, 10);
	}
}
