package com.rest.RestaurantApp.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.rest.RestaurantApp.e2e.pages.EmployeePage;
import com.rest.RestaurantApp.e2e.pages.IngredientsPage;
import com.rest.RestaurantApp.e2e.pages.LoginPage;


public class EmployeeTest {
	
	private WebDriver browser;
	private LoginPage loginPage;
	private EmployeePage employeePage;

	
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
		employeePage = PageFactory.initElements(browser, EmployeePage.class);
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
		browser.navigate().to("http://localhost:4200/employees");
		
		
		int count = employeePage.getEmployeeCount();
		employeePage.clickOnRow(2);
		employeePage.clickDeleteBtn();
//		
//		
//		//sacekaj da se ocita promena
		Thread.sleep(1000);
		int countAfterDelete = employeePage.getEmployeeCount();
		assertEquals(count-1, countAfterDelete);
		
		count = countAfterDelete;
		
		
		employeePage.setEmpBirthday("1/1/2001");
		employeePage.setEmpPin("354344");
		employeePage.setEmpSalary("54000");
		employeePage.setEmpName("Mirko");
		employeePage.setEmpSurname("Mirkovic");
		employeePage.setEmpEmail("mirko213e@gmail.com");
		employeePage.clickCreateBtn();
		
		Thread.sleep(1000);
		int countAfterCreate = employeePage.getEmployeeCount();
		assertEquals(count+1, countAfterCreate);

		
		
		count = countAfterCreate;
		
		employeePage.clickOnRow(2);
		employeePage.setEmpBirthday("3/3/2003");
		employeePage.setEmpPin("5555");
		employeePage.setEmpSalary("56000");
		employeePage.setEmpName("Marko");
		employeePage.selectRole(0);
		employeePage.setEmpSurname("Markovic");
		employeePage.setEmpEmail("markoni@gmail.com");
		employeePage.clickUpdateBtn();
		
		Thread.sleep(1000);
		int countAfterUpdate = employeePage.getEmployeeCount();
		assertEquals(count, countAfterUpdate);

		
		count = countAfterUpdate;
		employeePage.setNameSearch("ma");
		employeePage.setSurnameSearch("m");
		employeePage.setEmailSearch("mar");
		employeePage.clickSearchBtn();
		
		Thread.sleep(1000);
		int countAfterSearch = employeePage.getEmployeeCount();
		assertTrue(count >= countAfterSearch);
//		
//		ingredientsPage.clickOnRow(2);
//		ingredientsPage.setCreateUpdateName("Updatiran sastojak");
//		ingredientsPage.clickUpdateBtn();
//		
//		Thread.sleep(600);
//		int countAfterUpdate = ingredientsPage.getIngredientCount();
//		assertEquals(count, countAfterUpdate);
//		
//		count = countAfterUpdate;
//		
//		ingredientsPage.setSearchInput("a");
//		ingredientsPage.clickDropdown();
//		ingredientsPage.clickOption(1);
//		ingredientsPage.clickSearchBtn();
//		
//		Thread.sleep(600);
//		int countAfterSearch = ingredientsPage.getIngredientCount();
//		
//		assertTrue(countAfterSearch <= count);
		
		
		
		
		
	
	}
	
	
	//OVO KOMENTARISI ZA VREME DEVELOPMENTA
//	@After
//	public void closeSelenium() {
//		// Shutdown the browser
//		browser.quit();
//	}
}
