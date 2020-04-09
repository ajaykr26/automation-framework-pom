package automation.testsuite;

import automation.library.common.TestContext;
import automation.library.selenium.BaseTest;
import automation.pageobjects.PageObjects;
import org.testng.annotations.Test;

public class SmokeSuite extends BaseTest implements PageObjects {

    @Test(description = "Facebook Registration")
    public void FacebookRegistration() {
        //data initialization
        TestContext.getInstance().testdata().putAll(getDataTable(Thread.currentThread().getStackTrace()[1].getMethodName()));

        //calling methods
        homePage.launchApplication("facebook");
        homePage.verifyPageTitle("Facebook – log in or sign up");
        loginPage.registration();
    }

    @Test(description = "verify login with invalid details")
    public void FacebookLogin() {
        //data initialization
        TestContext.getInstance().testdata().putAll(getDataTable(Thread.currentThread().getStackTrace()[1].getMethodName()));

        //calling methods
        homePage.launchApplication("selenium");
    }

}