package library.selenium;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import library.common.*;
import library.reporting.ExtentManager;
import library.reporting.ExtentReporter;
import library.selenium.driver.factory.DriverContext;
import library.selenium.driver.factory.DriverFactory;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestResult;
import org.testng.annotations.*;

import javax.mail.MessagingException;
import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    private static final String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    public static String reportPath;
    private static String dataFile;
    private static String propFile;
    protected Logger logger = LogManager.getLogger(this.getClass().getName());
    private ExtentReports extentReports;

    private void setDataDictionary(String testname) {
        Map<String, String> datatable = FileHelper.getJSONToMap(FileHelper.getJSONObject(dataFile, testname));
        TestContext.getInstance().testdata().putAll(datatable);
    }

    private void setPropDataDictionary() {
        TestContext.getInstance().propData().putAll(Property.getPropertiesAsMap(Constants.RUNTIME_PROP_FILE));
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
            return techStack;
        } else {
            logger.info("techStach is not defined in vm arguments. getting the configuration from runtime.properties file");
            techStack.put("seleniumServer", Property.getProperty(Constants.RUNTIME_PROP_FILE, "seleniumServer").toLowerCase());
            techStack.put("browserName", Property.getProperty(Constants.RUNTIME_PROP_FILE, "browserName").toLowerCase());
            return techStack;
        }

    }

    @BeforeSuite
    public void beforeSuite() {

        String testsuite = getClass().getSimpleName();
        TestContext.getInstance().testdataPut("testsuite", testsuite);
        dataFile = Constants.TESTDATA_PATH + testsuite + ".json";
        propFile = Constants.RUNTIME_PROP_FILE;
        reportPath = System.getProperty("user.dir") + "/extent-report/" + testsuite + "/" + timeStamp;
        extentReports = ExtentManager.getReporter(reportPath + "/html-report");
    }

    @BeforeMethod
    public void startUp(Method method) {
        Test test = method.getAnnotation(Test.class);
        ThreadContext.put("testname", test.testName());
        TestContext.getInstance().testdataPut("fw.testname", test.testName());
        ExtentManager.startTest(test.testName(), test.description());
        setDataDictionary(test.testName());
        setPropDataDictionary();
        Property.setRuntimeProperties();
        DriverContext.getInstance().setDriverContext(getTechStack());
        DriverFactory.getInstance().getDriver();

    }

    public void createDriver(String browser) {
        DriverContext.getInstance().setDriverContext(getTechStack());
        DriverFactory.getInstance().getDriver();

    }

    @AfterMethod
    protected void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentManager.getTest().log(Status.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentManager.getTest().log(Status.SKIP, "Test skipped " + result.getThrowable());
        } else {
            ExtentManager.getTest().log(Status.PASS, "Test passed");
        }
        DriverFactory.getInstance().quit();

        try {
            Screenshot.insertImageToWord(Boolean.parseBoolean(TestContext.getInstance().propDataGet("addScreenshotToWord").toString()));
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void afterSuite() throws MessagingException {
        extentReports.flush();
        ExtentManager.launchReport(reportPath + "/html-report/index.html");
        ExtentManager.emailReport(reportPath + "/html-report/index.html");
    }


}
