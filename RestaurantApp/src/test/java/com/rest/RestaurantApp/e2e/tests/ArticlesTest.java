package com.rest.RestaurantApp.e2e.tests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.rest.RestaurantApp.e2e.pages.ArticlesTablePage;
import com.rest.RestaurantApp.e2e.pages.CreateArticlePage;
import com.rest.RestaurantApp.e2e.pages.LoginPage;
import com.rest.RestaurantApp.e2e.pages.UpdateArticlePage;

public class ArticlesTest {
	private WebDriver browser;
	private LoginPage loginPage;
	private ArticlesTablePage articlesTablePage;
	private UpdateArticlePage updateArticlePage;
	private CreateArticlePage createArticlePage;
	
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
		articlesTablePage = PageFactory.initElements(browser, ArticlesTablePage.class);
		updateArticlePage = PageFactory.initElements(browser, UpdateArticlePage.class);
		createArticlePage = PageFactory.initElements(browser, CreateArticlePage.class);
		loginPage.loginAsPrivileged();
	}
	
	@Test
	public void articlesFlowTest() throws InterruptedException {
		browser.navigate().to("http://localhost:4200/articles");
		
		articlesTablePage.selectFirstArticle();
		articlesTablePage.updateArticleClick();
		assertEquals("http://localhost:4200/update-article/1", browser.getCurrentUrl());
		
		updateArticlePage.setNameInput("DOBOSICA");
		updateArticlePage.setDescriptionInput("Bas je najbolja!");
		updateArticlePage.updateArticleClick();
		
		articlesTablePage.waitForUrlToBe("http://localhost:4200/articles");
		
		assertEquals("http://localhost:4200/articles", browser.getCurrentUrl());
		
		
		//proveri jel update
		assertEquals("DOBOSICA", articlesTablePage.getUpdatedArticleName());
		assertEquals("Bas je najbolja!", articlesTablePage.getUpdatedArticleDescription());
		
		articlesTablePage.selectFirstArticle();
		articlesTablePage.deleteArticleClick();
		
		//proveri jel delete
		assertEquals(9, articlesTablePage.getNumberOfArticles());
		
		articlesTablePage.createArticleClick();
		assertEquals("http://localhost:4200/create-article", browser.getCurrentUrl());
		
		createArticlePage.setNameInput("NOVI TEST");
		createArticlePage.setDescriptionInput("NAJJACI JE OVAJ");
		createArticlePage.setTypeInputToDessert();
		createArticlePage.setMakingPriceInput("50");
		createArticlePage.setSellingPriceInput("60");
		createArticlePage.selectIngredient();
		createArticlePage.createArticleClick();
		
		browser.navigate().to("http://localhost:4200/articles");
		
		//proveri da ga je napravio
		assertEquals(10, articlesTablePage.getNumberOfArticles());
		assertEquals("NOVI TEST", articlesTablePage.getCreatedArticleName());
		
		articlesTablePage.setNameInput("NOVI TES");
		articlesTablePage.setTypeInputToDessert();
		articlesTablePage.submitSearch();
		
		//ovde proveri da ima samo jedan element
		assertEquals(1, articlesTablePage.getNumberOfArticles());
	}
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
	
}
