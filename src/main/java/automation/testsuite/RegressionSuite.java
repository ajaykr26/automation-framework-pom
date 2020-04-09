package automation.testsuite;

import automation.library.selenium.BaseTest;
import automation.pageobjects.HomePage;
import automation.pageobjects.LoginPage;
import org.testng.annotations.Test;


public class RegressionSuite extends BaseTest {
	@Test(description = "verify login with valid details")
	public void Test001() {
		System.out.println("this my firs test case in smoke suite");
		HomePage homePage = new HomePage();

		homePage.launchApplication("google");
	}

//	@Test(description = "verify login with invalid details")
//	public void Test002() {
//		System.out.println("this my second test case in smoke suite");
//		HomePage homePage = new HomePage();
//
//		homePage.launchApplication("selenium");
//	}

}