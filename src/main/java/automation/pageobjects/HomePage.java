package automation.pageobjects;

import automation.library.common.Constants;
import automation.library.common.Property;
import automation.library.common.TestContext;
import automation.library.reporting.ExtentReporter;
import automation.library.selenium.BasePage;
import automation.library.selenium.BaseTest;
import automation.library.selenium.driver.factory.DriverFactory;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public void launchApplication(String applicationName) {
        String url = Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, applicationName);
        if (url != null) {
            getDriver().get(url);
//            getDriver().manage().window().maximize();

            ExtentReporter.takeScreenshot();
            ExtentReporter.addStepLog(Status.PASS, "application launched successfully");
            ExtentReporter.updateReport(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), Status.PASS, "application launched successfully");
        } else {
            logger.error("application url not defined in the file: " + Constants.ENVIRONMENT_PATH);
        }
    }

    public void verifyPageTitle(String pageTitle) {
        pageTitle = parseText(pageTitle);
        if (pageTitle.equals(getDriver().getTitle())) {
            ExtentReporter.addStepLog(Status.PASS, "page title verified successfully");
        } else {
            ExtentReporter.addStepLog(Status.FAIL, "page title not matched successfully");
        }

    }

}
