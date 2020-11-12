package testscripts;

import library.selenium.BaseTest;
import library.selenium.PageObject;
import org.testng.annotations.Test;


public class Facebook_Login extends BaseTest implements PageObject {

    @Test(testName = "Login To Facebook", description = "verify facebook login functionality")
    public void Test_001() {
        FACEBOOK_LOGIN_PAGE.launchApplication("facebook");
        FACEBOOK_LOGIN_PAGE.navigateToRegistrationPage();
        FACEBOOK_LOGIN_PAGE.register();
    }

}