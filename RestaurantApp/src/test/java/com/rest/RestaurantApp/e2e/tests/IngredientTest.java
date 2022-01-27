package com.rest.RestaurantApp.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.rest.RestaurantApp.e2e.pages.IngredientsPage;
import com.rest.RestaurantApp.e2e.pages.LoginPage;


public class IngredientTest {
	
	private WebDriver browser;
	private LoginPage loginPage;
	private IngredientsPage ingredientsPage;

	
	@Before
	public void setupSelenium() {
		// instantiate browser
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		// maximize window
		browser.manage().window().maximize();
		// navigate
		browser.navigate().to("http://localhost:4200/login");

		loginPage = PageFactory.initElements(browser, LoginPage.class);
		ingredientsPage = PageFactory.initElements(browser, IngredientsPage.class);
	}
	
	@Test
	public void loginTestPrivilegedUser() throws InterruptedException {
		assertEquals("http://localhost:4200/login",
				browser.getCurrentUrl());

		// set correct pass
		loginPage.setUsernameInput("markuza99");
		loginPage.setPasswordInput("petar123");
		loginPage.loginButtonClickPrivileged();
		
		loginPage.waitForUrlToBe("http://localhost:4200/home");
		
		assertEquals("http://localhost:4200/home", browser.getCurrentUrl());
		browser.navigate().to("http://localhost:4200/ingredients");
		
		int count = ingredientsPage.getIngredientCount();
		ingredientsPage.clickOnRow(2);
		ingredientsPage.clickDeleteBtn();
		
		//sacekaj da se ocita promena
		Thread.sleep(600);
		int countAfterDelete = ingredientsPage.getIngredientCount();
		assertEquals(count-1, countAfterDelete);
		
		count = countAfterDelete;
				
		ingredientsPage.setCreateUpdateName("Novi sastojak");
		ingredientsPage.clickCheckbox();
		ingredientsPage.clickCreateBtn();
		
		//sacekaj da se ocita promena
		Thread.sleep(600);
		int countAfterCreate = ingredientsPage.getIngredientCount();
		
		assertEquals(count+1, countAfterCreate);
		
		count = countAfterCreate;
		
		ingredientsPage.clickOnRow(2);
		ingredientsPage.setCreateUpdateName("Updatiran sastojak");
		ingredientsPage.clickUpdateBtn();
		
		Thread.sleep(600);
		int countAfterUpdate = ingredientsPage.getIngredientCount();
		assertEquals(count, countAfterUpdate);
		
		count = countAfterUpdate;
		
		ingredientsPage.setSearchInput("a");
		ingredientsPage.clickDropdown();
		ingredientsPage.clickOption(1);
		ingredientsPage.clickSearchBtn();
		
		Thread.sleep(600);
		int countAfterSearch = ingredientsPage.getIngredientCount();
		
		assertTrue(countAfterSearch <= count);
	}
	

	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
