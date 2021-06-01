package pageobjects;

import com.aventstack.extentreports.Status;
import library.common.TestContext;
import library.reporting.ExtentReporter;
import library.selenium.BasePO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class HandleAlerts extends BasePO {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public void launchAllertApps(String URL) {
        getDriver().get(URL);
    }

    public void acceptAlert() {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().accept();
            logger.debug("alert accepted successfully.");
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    public void dismissAlert() {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().dismiss();
            logger.debug("alert dismissed successfully.");
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    public void enterValueIntoAlertTextbox(String valueToEnter) {
        try {
            Alert alert = getWait().until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(valueToEnter);
            logger.debug("entered: {} successfully into the alert", valueToEnter);
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    public void storeAlertMessage(String dictionaryKey) {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            String valueToStore = getDriver().switchTo().alert().getText();
            TestContext.getInstance().testdataPut(dictionaryKey, valueToStore);
            logger.debug("alert message {} successfully stored in data dictionary with key: {}", valueToStore, dictionaryKey);
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

}
