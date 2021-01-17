package testscripts;

import library.selenium.BaseTest;
import library.selenium.PageFactory;
import org.testng.annotations.Test;

import static library.selenium.PageFactory.getFacebookLoginPage;


public class Facebook_Login extends BaseTest {

    @Test(testName = "Login To Facebook", description = "verify facebook login functionality")
    public void Test_001() {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

}