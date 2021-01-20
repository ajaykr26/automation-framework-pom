package testscripts;

import library.selenium.BaseTest;
import library.selenium.PageFactory;
import org.testng.annotations.Test;

import static library.selenium.PageFactory.getFacebookLoginPage;


public class Facebook_Login extends BaseTest {

    @Test(testName = "Login To Facebook 1", description = "verify facebook login functionality")
    public void Test_001() {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

    @Test(testName = "Login To Facebook 2", description = "verify facebook login functionality")
    public void Test_002() {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

    @Test(testName = "Login To Facebook 3", description = "verify facebook login functionality")
    public void Test_003() {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

}