package automation.pageobjects;

import automation.library.common.Constants;
import automation.library.common.Property;
import automation.library.reporting.ExtentReporter;
import automation.library.selenium.BasePage;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface PageObjects {
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();

}
