package library.selenium;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import library.common.*;
import library.reporting.ExtentManager;
import library.selenium.driver.factory.DriverContext;
import library.selenium.driver.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import javax.mail.MessagingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static library.reporting.ExtentReporter.addLogsToExtentReport;
import static library.reporting.ExtentReporter.getLogPath;

public class BaseTest {

    private static final String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    public static String reportPath;
    private static String dataFile;
    protected Logger logger = LogManager.getLogger(this.getClass().getName());
    private ExtentReports extentReports;

    private void setDataDictionary(String testName) {
        Map<String, String> datatable = FileHelper.getJSONToMap(FileHelper.getJSONObject(dataFile, testName));
        TestContext.getInstance().testdata().putAll(datatable);
    }

    private void setPropDictionary() {
        if (Property.getProperties(Constants.RUNTIME_PROP_FILE) != null) {
            Map<String, String> propDataTable = FileHelper.getPropertiesAsMap(Objects.requireNonNull(Property.getProperties(Constants.RUNTIME_PROP_FILE)));
            assert propDataTable != null;
            TestContext.getInstance().testdata().putAll(propDataTable);
        }

    }

    private Map<String, String> getTechStack() {
        Map<String, String> techStack = new HashMap<>();

        if (Property.getVariable("techStack") != null) {
            techStack = FileHelper.getJSONObjectToMap(Constants.JSON_STACKS_FILE, "platform");
            if (techStack.isEmpty()) {
                logger.warn("Tech stack JSON file not found: {}. defaulting to local chrome driver instance.", Constants.JSON_STACKS_FILE);
                techStack.put("seleniumServer", "local");
                techStack.put("browserName", "chrome");
            }
        } else {
            logger.info("techStach is not defined in vm arguments. getting the configuration from runtime.properties file");
            techStack.put("seleniumServer", Property.getProperty(Constants.RUNTIME_PROP_FILE, "seleniumServer").toLowerCase());
            techStack.put("browserName", Property.getProperty(Constants.RUNTIME_PROP_FILE, "browserName").toLowerCase());
        }
        return techStack;

    }

    @BeforeSuite
    public void beforeSuite() {

        String testsuite = getClass().getSimpleName();
        TestContext.getInstance().testdataPut("testsuite", testsuite);
        dataFile = Constants.TESTDATA_PATH + testsuite + ".json";
        reportPath = System.getProperty("user.dir") + "/extent-report/" + testsuite + "/" + timeStamp;
        extentReports = ExtentManager.getReporter(reportPath + "/html-report");
    }

    @BeforeMethod
    public void startUp(Method method) {
        Test test = method.getAnnotation(Test.class);
        ThreadContext.put("testName", getLogPath() + test.testName());
        TestContext.getInstance().testdataPut("fw.testName", test.testName());
        ExtentManager.startTest(test.testName(), test.description());
        setDataDictionary(test.testName());
        Property.setRuntimeProperties();
        DriverContext.getInstance().setDriverContext(getTechStack());
        DriverFactory.getInstance().getDriver();

    }

    @AfterMethod
    protected void afterMethod(ITestResult result) {
        String logLink = "../logs/" + TestContext.getInstance().testdataGet("fw.testName") + ".log";
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error(result.getThrowable());
            ExtentManager.getTest().log(Status.FAIL, "<a href='" + logLink + "'>click here to see the log</a>");
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn(result.getThrowable());
            ExtentManager.getTest().log(Status.SKIP, "<a href='" + logLink + "'>click here to see the log</a>");
        } else {
            ExtentManager.getTest().log(Status.PASS, "<a href='" + logLink + "'>click here to see the log</a>");
        }
        DriverFactory.getInstance().quit();
        logger.info(String.format("Data Dictionary: %s", DataTableFormatter.getDataDictionaryAsFormattedTable()));
        logger.info(String.format("Runtime properties: %s", DataTableFormatter.getMapAsFormattedTable(Property.getPropertiesAsMap(Constants.RUNTIME_PROP_FILE))));
    }

    @AfterSuite
    public void afterSuite() throws MessagingException {
        extentReports.flush();
        ExtentManager.launchReport(reportPath + "/html-report/index.html");
        ExtentManager.emailReport(reportPath + "/html-report/index.html");
    }


}
