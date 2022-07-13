package testscripts;

import library.reporting.ExtentReporter;
import library.selenium.BaseTest;
import library.selenium.PageFactory;
import org.testng.annotations.Test;


public class Facebook_Login extends BaseTest {

    @Test(testName = "Login To Facebook one", description = "verify facebook login functionality")
    public void Test_001() {
        PageFactory.getFacebookLoginPage().launchApplication("facebook");
        PageFactory.getFacebookLoginPage().navigateToRegistrationPage();
        PageFactory.getFacebookLoginPage().register();

    }

    @Test(testName = "Login To Facebook two", description = "verify facebook login functionality")
    public void Test_002() {
        PageFactory.getFacebookLoginPage().launchApplication("facebook");
        PageFactory.getFacebookLoginPage().navigateToRegistrationPage();
        PageFactory.getFacebookLoginPage().register();
    }

}