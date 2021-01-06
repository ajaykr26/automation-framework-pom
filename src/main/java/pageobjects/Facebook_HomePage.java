package pageobjects;

import com.aventstack.extentreports.Status;
import library.reporting.ExtentReporter;
import library.selenium.BasePO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class Facebook_HomePage extends BasePO {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public void verifyPageTitle(String pageTitle) {
        pageTitle = parseText(pageTitle);
        Assert.assertEquals(getDriver().getTitle(), pageTitle);
        ExtentReporter.addStepLog(Status.PASS, "page title verified successfully");
//        if (pageTitle.equals(getDriver().getTitle())) {
//            ExtentReporter.addStepLog(Status.PASS, "page title verified successfully");
//        } else {
//            ExtentReporter.addStepLog(Status.FAIL, "page title not matched successfully");
//        }

    }

    public void handleAlert(String option) {
        try {
            Alert alert = getWait().until(ExpectedConditions.alertIsPresent());
            if (option.equalsIgnoreCase("ok")) {
                alert.accept();
                logger.debug("Accepted the alert successfully.");
            } else {
                alert.dismiss();
                logger.debug("Dismissed the alert successfully.");
            }
        } catch (Throwable e) {
            System.err.println("Error came while waiting for the alert popup. " + e.getMessage());
        }
    }

}
