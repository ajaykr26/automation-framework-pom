package automation.pageobjects;

import automation.library.common.Property;
import automation.library.reporting.ExtentReporter;
import automation.library.selenium.BasePage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {


	private By usernameInput = By.xpath("//input[@name='email']");
	private By passwordInput = By.xpath("//input[@name='pass']");
	private By submitBtn = By.xpath("");
	private By firstnameInput = By.name("firstname");
	private By lastnameInput = By.name("lastname");
	private By mobileInput = By.xpath("//input[@aria-label='Mobile number or email address']");
	private By dayDD = By.xpath("//select[@title='Day']");
	private By monthDD = By.xpath("//select[@title='Month']");
	private By yearDD = By.xpath("//select[@title='Year']");
	private By sexRD = By.name("sex");
	private By radioRDL = By.xpath("//span[@data-type='radio']//input");
	private By tableLoc = By.xpath("//div[@class='ui-grid-contents-wrapper']");
	private By headerLoc = By.xpath("//div/span[@class='ui-grid-header-cell-label ng-binding']");
	private By rowLoc = By.xpath("//div[@class='ui-grid-canvas']/div");
	private By colLoc = By.xpath("//div[@role='gridcell']");

	public void login() {
		setUsername();
		setPassword();
		getElement(submitBtn).click();

	}

	public void setUsername() {
		getElement(usernameInput).click();
		getElement(usernameInput).sendKeys(parseText("username"));
		ExtentReporter.updateReport("Home Page", "verifyHomePage", Status.PASS, "user navigated to home page");
	}

	public void setPassword() {
		getElement(passwordInput).click();
		getElement(passwordInput).sendKeys(parseText("password"));
		ExtentReporter.updateReport("Home Page", "verifyHomePage", Status.PASS, "user navigated to home page");
	}

	public void logout() {

	}

	public void registration() {
		getElement(firstnameInput).sendKeys(parseMathcingText("#(firstname)"));
		getElement(lastnameInput).sendKeys(parseMathcingText("#(lastname)"));
		getElement(mobileInput).sendKeys(parseMathcingText("#(mobile)"));
		inputMethodsObj.selectOptionFromDropdown("value", "7", dayDD);
		inputMethodsObj.selectOptionFromDropdown("text", parseMathcingText("#(month)"), monthDD);
		inputMethodsObj.selectOptionFromDropdown("value", "1990", yearDD);
		inputMethodsObj.selectOptionFromRadioButtonGroup("value", "Male", radioRDL);

		ExtentReporter.updateReport(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), Status.PASS, "user completed facebook registration");

	}
}
