package com.rest.RestaurantApp.e2e.tests;

import static org.junit.Assert.assertTrue;

import com.rest.RestaurantApp.e2e.pages.LoginPage;
import com.rest.RestaurantApp.e2e.pages.EditTablePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class EditTableTest {
    private WebDriver browser;
    private EditTablePage tableLayoutPage;
    private LoginPage loginPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();

        browser.manage().window().maximize();

        browser.navigate().to("http://localhost:4200/login");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        tableLayoutPage = PageFactory.initElements(browser, EditTablePage.class);

        loginPage.loginAsPrivileged();
    }

    @Test
    public void editTableLayoutTest() throws InterruptedException {
        browser.navigate().to("http://localhost:4200/edit-table-layout");
        tableLayoutPage.openDropdown();
        tableLayoutPage.addTable();

        assertTrue(tableLayoutPage.displayedMessage("Table successfully added!"));

        Thread.sleep(500);
        tableLayoutPage.removeTable(20, 20);

        assertTrue(tableLayoutPage.displayedMessage("Table deleted!"));

        Thread.sleep(500);
        tableLayoutPage.addTable();
        tableLayoutPage.moveObjectOnCanvasByCoordinates(20, 20, 150, 150);
        tableLayoutPage.removeTable(150, 150);
        tableLayoutPage.saveLayout();

        assertTrue(tableLayoutPage.displayedMessage("Table layout saved!"));

        browser.navigate().refresh();
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
