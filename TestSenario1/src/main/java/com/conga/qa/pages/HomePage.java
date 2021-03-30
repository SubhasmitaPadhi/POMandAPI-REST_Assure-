package com.conga.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.conga.qa.base.TestBase;

public class HomePage extends TestBase {
	@FindBy(xpath = "//a[contains(text(),'T-shirts')]")
	public static WebElement Tshirt;

	@FindBy(name = "replace-2x img-responsive")
	WebElement tshirtimage;

	@FindBy(xpath = "//div[@class='box-cart-bottom']//div//p//button//span")
	WebElement addtocart;

	@FindBy(xpath = "//div[@class='button-container']//a//span")
	WebElement proceedtocheckout;
	@FindBy(xpath = "//div[@class=\"layer_cart_product col-xs-12 col-md-6\"]//h2")
	WebElement getheader;

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public String verifyHomePageTitle() {
		return driver.getTitle();
	}

	public Object tshirtSelection() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", Tshirt);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		js.executeScript("arguments[0].click();", tshirtimage);
		// driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		js.executeScript("arguments[0].click();", addtocart);
		js.executeScript("arguments[0].click();", proceedtocheckout);
		return getheader.getText();

	}
}
