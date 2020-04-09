package automation.library.reporting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static String testSuite;
    private static String testName;
    private Logger logger = LogManager.getLogger(this.getClass());

    public void onStart(ITestContext context) {
        testSuite = context.getName();
        logger.info("*** Test Suite " + testSuite + " started ***");
    }

    public void onFinish(ITestContext context) {
        logger.info(("*** Test Suite " + testSuite + " ending ***"));
    }

    public void onTestStart(ITestResult result) {
        testName = result.getName();
        logger.info(("*** Running test method: " + testName));

    }

    public void onTestSuccess(ITestResult result) {
        logger.info("*** Executed " + testName + " test successfully...");
    }

    public void onTestFailure(ITestResult result) {
        logger.info("*** Test execution " + testName + " failed...");
    }

    public void onTestSkipped(ITestResult result) {
        logger.info("*** Test " + testName + " skipped...");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }
}
