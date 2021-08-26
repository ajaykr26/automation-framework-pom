package pageobjects;

import com.aventstack.extentreports.Status;
import library.common.Constants;
import library.reporting.ExtentReporter;
import library.selenium.BasePO;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.io.File;

public class Facebook_LoginPage extends BasePO {


    private final By username = By.id("email");
    private final By password = By.id("pass");
    private final By login = By.name("login");
    private final By firstname = By.name("firstname");
    private final By lastname = By.name("lastname");
    private final By reg_email = By.name("reg_email__");
    private final By password_step_input = By.id("password_step_input");
    private final By day = By.id("day");
    private final By month = By.id("month");
    private final By year = By.id("year");
    private final By sex = By.name("sex");
    private final By register = By.xpath("//a[@data-testid='open-registration-form-button']");


    public void loginToApplication() {
        getElement(username).sendKeys(parseText("USERNAME"));
        getElement(password).sendKeys(parseText("PASSWORD"));
    }

    public void navigateToRegistrationPage() {
        getElement(register).click();
        ExtentReporter.updateReport("Facebook_LoginPage", "navigateToRegistrationPage", Status.PASS, "navigated to Registration Page");
    }

    public void register() {

        getElement(firstname).sendKeys(parseText("FIRSTNAME"));
        getElement(lastname).sendKeys(parseText("LASTNAME"));
        getElement(reg_email).sendKeys(parseText("EMAIL"));
        getElement(password_step_input).sendKeys(parseText("PASSWORD"));
        selectOptionFromDropdown("value", "10", day);
        selectOptionFromDropdown("value", "10", month);
        selectOptionFromDropdown("value", "1990", year);
        selectOptionFromRadioButtonGroup("value", "2", sex);
        ExtentReporter.updateReport("Facebook_LoginPage", "register", Status.PASS, "all the details provided");

    }

    public void validateImageMatched(String imageUrl, String filename) throws Throwable {
        filename = parseText(filename);
        imageUrl = parseText(imageUrl);
        getImageFromUrl(imageUrl, Constants.ACTUAL_IMAGE_PATH, filename);
        boolean isVerified = compareScreenshot(new File(Constants.EXPECTED_IMAGE_PATH + filename), new File(Constants.ACTUAL_IMAGE_PATH + filename));
        Assert.assertTrue(isVerified);
    }
}
