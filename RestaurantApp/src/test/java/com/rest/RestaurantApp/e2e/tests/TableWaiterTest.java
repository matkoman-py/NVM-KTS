package com.rest.RestaurantApp.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.rest.RestaurantApp.e2e.pages.EditTablePage;
import com.rest.RestaurantApp.e2e.pages.LoginPage;
import com.rest.RestaurantApp.e2e.pages.TableWaiterPage;
import com.rest.RestaurantApp.e2e.pages.ViewOrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class TableWaiterTest {
    private WebDriver browser;
    private TableWaiterPage tableWaiterPage;
    private ViewOrderPage viewOrderPage;
    private LoginPage loginPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();

        browser.manage().window().maximize();

        browser.navigate().to("http://localhost:4200/login");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        tableWaiterPage = PageFactory.initElements(browser, TableWaiterPage.class);
        viewOrderPage = PageFactory.initElements(browser, ViewOrderPage.class);

        loginPage.loginAsWaiter();
    }

    @Test
    public void testWaiterTables() throws InterruptedException {
        browser.navigate().to("http://localhost:4200/table-layout");

        tableWaiterPage.clickTable();

        assertTrue(tableWaiterPage.isOrderFormVisible());

        tableWaiterPage.addArticle(0);
        tableWaiterPage.selectQuantity("1");
        assertEquals(1, tableWaiterPage.countAddedArticles());

        tableWaiterPage.addArticle(1);
        tableWaiterPage.selectQuantity("2");
        assertEquals(3, tableWaiterPage.countAddedArticles());

        tableWaiterPage.removeArticle(1);
        assertEquals(2, tableWaiterPage.countAddedArticles());

        tableWaiterPage.addArticlesToOrder();

        tableWaiterPage.insertPincode("1234");
        assertTrue(tableWaiterPage.displayedMessage("New order successfully created"));

        tableWaiterPage.clickTable();
//        assertTrue(tableWaiterPage.waitForUrlToBe());

        viewOrderPage.addArticle(1);

        viewOrderPage.removeArticle(0);

        viewOrderPage.finishOrder();

        assertTrue(viewOrderPage.waitForUrlToBe("http://localhost:4200/table-layout"));
    }

//    @After
//    public void closeSelenium() {
//        // Shutdown the browser
//        browser.quit();
//    }
}
