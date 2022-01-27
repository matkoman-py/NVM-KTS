package com.rest.RestaurantApp.e2e.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.rest.RestaurantApp.e2e.pages.LoginPage;
import com.rest.RestaurantApp.e2e.pages.OrderedArticlesPage;
import com.rest.RestaurantApp.e2e.pages.OrdersPage;

public class OrdersTest {

	private WebDriver browser;
	private LoginPage loginPage;
	private OrdersPage ordersPage;
	private OrderedArticlesPage orderedArticlesPage;
	
	@Before
	public void setupSelenium() {

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/login");

		loginPage = PageFactory.initElements(browser, LoginPage.class);
		ordersPage = PageFactory.initElements(browser, OrdersPage.class);
		orderedArticlesPage = PageFactory.initElements(browser, OrderedArticlesPage.class);
	}
	
	@Test
	public void ordersTest() throws InterruptedException {
		assertEquals("http://localhost:4200/login",
				browser.getCurrentUrl());

		loginPage.employeeTabClick();
		loginPage.setPincodeInput("cook123");
		loginPage.loginButtonClickEmployee();
		
		ordersPage.clickSeeOrderBtn();
		assertEquals("No order selected", ordersPage.isOrderNotSelectedError());
		
		ordersPage.selectOrder();
		ordersPage.clickSeeOrderBtn();
		assertEquals("http://localhost:4200/view-order-kitchen/1",
				browser.getCurrentUrl());
		
		orderedArticlesPage.clickTakeOrderBtn();
		orderedArticlesPage.inputPin("2910");	
		assertEquals("Status of article successfully updated", orderedArticlesPage.checkToastMessage());
		orderedArticlesPage.closeModal();
		
		orderedArticlesPage.clickTakeOrderBtn();
		orderedArticlesPage.inputPin("0000");	
		assertEquals("Employee with pin 0 was not found", orderedArticlesPage.checkToastMessage());
		orderedArticlesPage.closeModal();
		
		orderedArticlesPage.clickTakeOrderBtn();
		orderedArticlesPage.inputPin("1234");	
		assertEquals("An employee of type WAITER can't", orderedArticlesPage.checkToastMessage().substring(0, 32));
		orderedArticlesPage.closeModal();
	}
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
