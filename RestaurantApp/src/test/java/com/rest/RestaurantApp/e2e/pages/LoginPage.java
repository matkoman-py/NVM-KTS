package com.rest.RestaurantApp.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage {
	private WebDriver driver;
	
	@FindBy(id = "float-username")
	private WebElement usernameInput;
	
	@FindBy(css = "#float-password input")
	private WebElement passwordInput;
	
	@FindBy(css = "#pincode-input input")
	private WebElement pincodeInput;
	
	@FindBy(id = "login-button-privileged")
	private WebElement loginButtonPrivileged;
	
	@FindBy(id = "login-button-employee")
	private WebElement loginButtonEmployee;
	
	@FindBy(id = "username-help-privileged")
	private WebElement loginErrorPrivileged;
	
	@FindBy(id = "username-help-employee")
	private WebElement loginErrorEmployee;
	
	@FindBy(id = "p-tabpanel-1-label")
	private WebElement employeeTab;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void loginButtonClickPrivileged() {
		Utilities.visibilityWait(driver, this.loginButtonPrivileged, 10).click();
	}
	
	public void loginButtonClickEmployee() {
		Utilities.visibilityWait(driver, this.loginButtonEmployee, 10).click();
	}
	
	public void employeeTabClick() {
		Utilities.visibilityWait(driver, this.employeeTab, 10).click();
	}
	
	public boolean isLoginButtonClickablePrivileged() {
		return this.loginButtonPrivileged.isEnabled();
	}
	
	public WebElement getUsernameInput() {
		return Utilities.visibilityWait(driver, this.usernameInput, 10);
	}
	
	public WebElement getPasswordInput() {
		return Utilities.visibilityWait(driver, this.passwordInput, 10);
	}
	
	public WebElement getPincodeInput() {
		return Utilities.visibilityWait(driver, this.pincodeInput, 10);
	}
	
	public boolean isLoginErrorPrivileged(String error) {
		return Utilities.textWait(driver, this.loginErrorPrivileged, error, 10);
	}
	
	public boolean isLoginErrorEmployee(String error) {
		return Utilities.textWait(driver, this.loginErrorEmployee, error, 10);
	}
	
	public void waitForUrlToBe(String url) {
		Utilities.urlWait(driver, url, 10);
	}
	
	public void setUsernameInput(String value) {
		WebElement el = getUsernameInput();
		el.clear();
		el.sendKeys(value);
	}
	
	public void setPasswordInput(String value) {
		WebElement el = getPasswordInput();
		el.clear();
		el.sendKeys(value);
	}
	
	public void setPincodeInput(String value) {
		WebElement el = getPincodeInput();
		el.clear();
		el.sendKeys(value);
	}
	
	public void loginAsPrivileged() {
		
		WebElement username = getUsernameInput();
		username.clear();
		username.sendKeys("markuza99");
		
		WebElement password = getPasswordInput();
		password.clear();
		password.sendKeys("petar123");
		
		loginButtonClickPrivileged();
		waitForUrlToBe("http://localhost:4200/home");
	}
	
	public void loginAsCook() {
		employeeTabClick();
		
		WebElement pincode = getPincodeInput();
		pincode.clear();
		pincode.sendKeys("cook123");
		
		loginButtonClickEmployee();
		waitForUrlToBe("http://localhost:4200/home");
	}
	
	public void loginAsWaiter() {
		employeeTabClick();
		
		WebElement pincode = getPincodeInput();
		pincode.clear();
		pincode.sendKeys("waiter123");
		
		loginButtonClickEmployee();
		waitForUrlToBe("http://localhost:4200/home");
	}
	
	public void loginAsBarman() {
		employeeTabClick();
		
		WebElement pincode = getPincodeInput();
		pincode.clear();
		pincode.sendKeys("barman123");
		
		loginButtonClickEmployee();
		waitForUrlToBe("http://localhost:4200/home");
	}
}
