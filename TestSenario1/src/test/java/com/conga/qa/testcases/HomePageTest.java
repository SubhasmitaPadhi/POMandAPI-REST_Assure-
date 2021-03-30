
package com.conga.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.conga.qa.base.TestBase;
import com.conga.qa.pages.HomePage;
import com.conga.qa.pages.LoginPage;
import com.conga.qa.util.TestUtil;

public class HomePageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;

	public HomePageTest() {
		super();
	}
	// test cases should be separated -- independent with each other
	// before each test case -- launch the browser and login
	// @test -- execute test case
	// after each test case -- close the browser

	@BeforeMethod
	public void setUp() {
		initialization();
		testUtil = new TestUtil();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(priority = 1)
	public void verifyHomePageTitleTest() {
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, "T-shirts - My Store", "Home page title not matched");
	}

	@Test(priority = 2)
	public void testScenario1() {

		String scenario1 = (String) homePage.tshirtSelection();
		Assert.assertEquals(scenario1, "Product successfully added to your shopping cart\r\n" + "				",
				"Home page title not matched");

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
