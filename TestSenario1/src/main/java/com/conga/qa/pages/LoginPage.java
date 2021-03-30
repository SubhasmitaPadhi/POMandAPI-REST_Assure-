package com.conga.qa.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.conga.qa.base.TestBase;

public class LoginPage extends TestBase {

	// Page Factory - OR:
	@FindBy(name = "email")
	WebElement username;

	@FindBy(name = "passwd")
	WebElement password;

	@FindBy(className = "login")
	WebElement signinbtn;

	@FindBy(name = "SubmitLogin")
	WebElement submit;

	// Initializing the Page Objects:
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	public HomePage login(String un, String pwd) {
		// upper sign in button clicked
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", signinbtn);

		username.sendKeys(un);
		password.sendKeys(pwd);
		// loginBtn.click();
		js.executeScript("arguments[0].click();", submit);

		return new HomePage();
	}
}
