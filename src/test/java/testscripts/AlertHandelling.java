package testscripts;

import library.selenium.BaseTest;
import library.selenium.driver.factory.DriverContext;
import library.selenium.driver.factory.DriverFactory;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import pageobjects.HandleAlerts;

import static library.selenium.PageFactory.getFacebookLoginPage;


public class AlertHandelling extends BaseTest {

    @Test(testName = "Simple Alert", description = "The simple alert class in Selenium displays some information or warning on the screen")
    public void Test_001() {
        HandleAlerts handleAlerts = new HandleAlerts();
        handleAlerts.launchAllertApps("https://chercher.tech/practice/practice-pop-ups-selenium-webdriver");
        handleAlerts.acceptAlert();
        DriverFactory.getInstance().getDriver().findElement(By.name("prompt")).click();
        handleAlerts.enterValueIntoAlertTextbox("alertValure");
        handleAlerts.dismissAlert();
    }

    @Test(enabled = true, testName = "Login To Facebook two", description = "verify facebook login functionality")
    public void Test_002() {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

    @Test(enabled = true, testName = "Login To Facebook two", description = "verify facebook login functionality")
    public void Test_003() {
        getFacebookLoginPage().launchApplication("facebook");
        getFacebookLoginPage().navigateToRegistrationPage();
        getFacebookLoginPage().register();
    }

}