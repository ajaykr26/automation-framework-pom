package testscripts;

import library.selenium.BaseTest;
import library.selenium.PageFactory;
import org.testng.annotations.Test;
import pageobjects.Facebook_LoginPage;

import static library.selenium.PageFactory.getFacebookLoginPage;


public class Facebook_Login extends BaseTest {

    @Test(testName = "Login To Facebook one", description = "verify facebook login functionality")
    public void Test_001() {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().launchApplication("facebook", "INTERNET_EXPLORER");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

    @Test(testName = "Login To Facebook two", description = "verify facebook login functionality")
    public void Test_002() {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

}