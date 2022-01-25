package com.rest.RestaurantApp.e2e.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.rest.RestaurantApp.e2e.pages.LoginPage;


public class LoginTest {
	
	private WebDriver browser;
	private LoginPage loginPage;

	
	@Before
	public void setupSelenium() {
		// instantiate browser
		System.out.println("DASDSAD");
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		// maximize window
		browser.manage().window().maximize();
		// navigate
		browser.navigate().to("http://localhost:4200/login");

		loginPage = PageFactory.initElements(browser, LoginPage.class);
	}
	
	@Test
	public void loginTestPrivilegedUser() {
		assertEquals("http://localhost:4200/login",
				browser.getCurrentUrl());

		// all fields empty
		assertEquals(loginPage.isLoginButtonClickablePrivileged(),
				false);

		// set invalid email
		loginPage.setUsernameInput("aaa");
		
		// set wrong pass
		loginPage.setPasswordInput("wrong");
		loginPage.loginButtonClickPrivileged();
		assertEquals(loginPage.isLoginErrorPrivileged("Username or password is incorrect!"), true);

		// set correct pass
		loginPage.setUsernameInput("markuza99");
		loginPage.setPasswordInput("petar123");
		loginPage.loginButtonClickPrivileged();
		
		loginPage.waitForUrlToBe("http://localhost:4200/home");
		
		assertEquals("http://localhost:4200/home", browser.getCurrentUrl());
	}
	
	@Test
	public void loginTestEmployee() {
		assertEquals("http://localhost:4200/login",
				browser.getCurrentUrl());
		
		loginPage.employeeTabClick();
		
		loginPage.loginButtonClickEmployee();
		
		assertEquals(loginPage.isLoginErrorEmployee("Pincode is incorrect!"), true);

		// set invalid pincode
		loginPage.setPincodeInput("8585");
		loginPage.loginButtonClickEmployee();
		assertEquals(loginPage.isLoginErrorEmployee("Pincode is incorrect!"), true);
		
		//set valid pincode
		loginPage.setPincodeInput("cook123");
		loginPage.loginButtonClickEmployee();
		
		loginPage.waitForUrlToBe("http://localhost:4200/home");
		
		assertEquals("http://localhost:4200/home", browser.getCurrentUrl());
	}
	
	
	//OVO KOMENTARISI ZA VREME DEVELOPMENTA
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
