package com.rest.RestaurantApp.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import com.rest.RestaurantApp.e2e.pages.LoginPage;
import com.rest.RestaurantApp.e2e.pages.ReportsPage;

public class ReportsTest {
	
	private WebDriver browser;
	private LoginPage loginPage;
	private ReportsPage reportsPage;
	
	@Before
	public void setupSelenium() {

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/login");

		loginPage = PageFactory.initElements(browser, LoginPage.class);
		reportsPage = PageFactory.initElements(browser, ReportsPage.class);
	}
	
	@Test
	public void reportsTest() throws InterruptedException {
		assertEquals("http://localhost:4200/login",
				browser.getCurrentUrl());

		loginPage.setUsernameInput("markuza99");
		loginPage.setPasswordInput("petar123");
		loginPage.loginButtonClickPrivileged();
		
		loginPage.waitForUrlToBe("http://localhost:4200/home");
		browser.navigate().to("http://localhost:4200/reports");
		
		reportsPage.clickYearlyReportTab();
		assertTrue(reportsPage.getData("Dobos torta,Jagnjetina,Mesano meso,Meze,Espreso,Coca cola"));
		reportsPage.inputYearly();
		assertTrue(reportsPage.getData("No data availabe"));
		
		reportsPage.clickQuarterReportTab();
		assertTrue(reportsPage.getData("Jagnjetina,Mesano meso,Meze,Espreso,Coca cola"));
		reportsPage.inputQuarterly();
		assertTrue(reportsPage.getData("No data availabe"));
		
		reportsPage.clickMonthReportTab();
		assertTrue(reportsPage.getData("Jagnjetina,Mesano meso,Meze,Espreso,Coca cola"));
		reportsPage.inputMonthly();
		assertTrue(reportsPage.getData("No data availabe"));
		
		reportsPage.clickDayReportTab();
		assertTrue(reportsPage.getData("Jagnjetina,Mesano meso,Meze"));
		reportsPage.inputDaily();
		assertTrue(reportsPage.getData("No data availabe"));
		
		reportsPage.clickFromToReportTab();
		assertTrue(reportsPage.getData("Dobos torta,Jagnjetina,Mesano meso,Meze,Espreso,Coca cola"));	
	}
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
