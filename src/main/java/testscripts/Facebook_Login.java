package testscripts;

import library.selenium.BaseTest;
import library.selenium.PageFactory;
import org.testng.annotations.Test;


public class Facebook_Login extends BaseTest {

    @Test(testName = "Login To Facebook", description = "verify facebook login functionality")
    public void Test_001() {
        PageFactory.getFacebookLoginPage().launchApplication("facebook");
        PageFactory.getFacebookLoginPage().navigateToRegistrationPage();
        PageFactory.getFacebookLoginPage().register();
    }

}