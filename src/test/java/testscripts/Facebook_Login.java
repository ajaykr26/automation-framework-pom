package testscripts;

import library.selenium.BaseTest;
import library.selenium.PageFactory;
import org.testng.annotations.Test;
import pageobjects.Facebook_LoginPage;

import static library.selenium.PageFactory.getFacebookLoginPage;


public class Facebook_Login extends BaseTest {

    @Test(testName = "Login To Facebook one", description = "verify facebook login functionality")
    public void Test_001() throws Throwable {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

//    @Test(testName = "Login To Facebook two", description = "verify facebook login functionality")
//    public void Test_002() {
//        getFacebookLoginPage().launchApplication("facebook");
//        getFacebookLoginPage().navigateToRegistrationPage();
//        getFacebookLoginPage().register();
//    }
//
//    @Test(testName = "Login To Facebook three", description = "verify facebook login functionality")
//    public void Test_003() {
//        getFacebookLoginPage().launchApplication("facebook");
//        getFacebookLoginPage().navigateToRegistrationPage();
//        getFacebookLoginPage().register();
//    }
//
//    @Test(testName = "Login To Facebook four", description = "verify facebook login functionality")
//    public void Test_004() {
//        getFacebookLoginPage().launchApplication("facebook");
//        getFacebookLoginPage().navigateToRegistrationPage();
//        getFacebookLoginPage().register();
//    }
//
//    @Test(testName = "Login To Facebook five", description = "verify facebook login functionality")
//    public void Test_005() {
//        getFacebookLoginPage().launchApplication("facebook");
//        getFacebookLoginPage().navigateToRegistrationPage();
//        getFacebookLoginPage().register();
//    }
//
//    @Test(testName = "Login To Facebook six", description = "verify facebook login functionality")
//    public void Test_006() {
//        getFacebookLoginPage().launchApplication("facebook");
//        getFacebookLoginPage().navigateToRegistrationPage();
//        getFacebookLoginPage().register();
//    }

}