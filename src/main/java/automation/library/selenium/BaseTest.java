package automation.library.selenium;

import automation.library.common.Constants;
import automation.library.common.FileHelper;
import automation.library.common.Property;
import automation.library.common.TestContext;
import automation.library.reporting.ExtentManager;
import automation.library.selenium.driver.factory.DriverContext;
import automation.library.selenium.driver.factory.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseTest extends ExtentManager {

    private static final String PROJECT_NAME = "PROJECT_NAME";
    private static final String BUILD_NUMBER = "BUILD_NUMBER";
    public static String testsuite;
    public static String testname;
    public static String reportPath;
    protected Logger logger = LogManager.getLogger(this.getClass().getName());
    private static String dataFile;
    private static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    private ExtentReports extentReports;

    protected Map<String, String> getDataTable(String testName){
        return FileHelper.getJSONToMap(FileHelper.getJSONObject(dataFile, testName));
    }

    @BeforeSuite
    public void beforeSuite() {

        testsuite = getClass().getSimpleName();
        dataFile = Constants.TESTDATA_PATH + testsuite + ".json";
        reportPath = System.getProperty("user.dir") + "/extent-report/" + testsuite +"/"+ timeStamp;
        extentReports = ExtentManager.getReporter(reportPath + "/html-report");
    }

    @BeforeMethod
    public void startUp(Method method) {

        Test test = method.getAnnotation(Test.class);
        testname = method.getName();
        Map<String, String> mapOfTechStacks = FileHelper.getJSONObjectToMap(Constants.SELENIUM_STACKS_PATH, "platform");
        startTest(testname, test.description());


//        if (!TestContext.getInstance().testdata().containsKey("fw.cucumberTest")) ;
//        TestContext.getInstance().testdataPut("fw.testDescription", test.description() + "(" + mapOfTechStacks.get("description") + ")");
        if (Property.getVariable(PROJECT_NAME) != null && !Property.getVariable(PROJECT_NAME).isEmpty())
            TestContext.getInstance().testdataPut("fw.projectName", Property.getVariable(PROJECT_NAME));
        if (Property.getVariable(BUILD_NUMBER) != null && !Property.getVariable(BUILD_NUMBER).isEmpty())
            TestContext.getInstance().testdataPut("fw.buildNumber", Property.getVariable(BUILD_NUMBER));

//        String dataFile = Constants.TESTDATA_PATH + testsuite + ".json";
//        TestContext.getInstance().testdataPut("fw.TestNGTest", "true");
//        TestContext.getInstance().testdataPut("fw.testName", testname);
//        TestContext.getInstance().testdataPut("fw.testDescription", test.description());
//        Map<String, String> dataTable = FileHelper.getJSONToMap(FileHelper.getJSONObject(dataFile, testname));
//        TestContext.getInstance().testdata().putAll(dataTable);

        DriverContext.getInstance().setDriverContext(mapOfTechStacks);

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
    }

    @AfterSuite
    public void afterSuite() {
        extentReports.flush();
    }
}
