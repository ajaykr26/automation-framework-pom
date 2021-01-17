package pageobjects;

import com.aventstack.extentreports.Status;
import library.common.Constants;
import library.common.Property;
import library.reporting.ExtentReporter;
import library.selenium.BasePO;
import org.openqa.selenium.By;

import static library.selenium.FactoryMethod.*;

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


    public void launchApplication(String applicationName) {
        String url = Property.getProperty(Constants.getCurrentEnvFilePath(), applicationName);
        if (url != null) {
            getDriver().manage().window().maximize();
            logger.info("application {} launched in new window", url);
            getDriver().get(url);
            ExtentReporter.updateReport("Facebook_LoginPage", "launchApplication",Status.PASS, "application launched successfully");
        } else {
            ExtentReporter.updateReport( "Facebook_LoginPage", "launchApplication",Status.FAIL, "application not launched successfully");
            logger.error("application url not defined in the file: " + Constants.getCurrentEnvFilePath());
        }
    }

    public void loginToApplication() {
        getElement(username).sendKeys(parseText("USERNAME"));
        getElement(password).sendKeys(parseText("PASSWORD"));
    }

    public void navigateToRegistrationPage() {
        getElement(register).click();
        ExtentReporter.updateReport( "Facebook_LoginPage", "navigateToRegistrationPage",Status.PASS, "navigated to Registration Page");
    }

    public void register() {

        getElement(firstname).sendKeys(parseText("FIRSTNAME"));
        getElement(lastname).sendKeys(parseText("LASTNAME"));
        getElement(reg_email).sendKeys(parseText("EMAIL"));
        getElement(password_step_input).sendKeys(parseText("PASSWORD"));
        getSelectMethods().selectOptionFromDropdown("value", "10", day);
        getSelectMethods().selectOptionFromDropdown("value", "10", month);
        getSelectMethods().selectOptionFromDropdown("value", "1990", year);
        getSelectMethods().selectOptionFromRadioButtonGroup("value", "2", sex);
        ExtentReporter.updateReport( "Facebook_LoginPage", "register",Status.PASS, "all the details provided");

    }

    public void manualyProceed() {
        logger.debug("proceed manually and resume execution");
    }

}
