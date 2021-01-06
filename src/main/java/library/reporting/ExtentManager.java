package library.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewStyle;
import library.common.Constants;
import library.common.Property;

import java.io.File;
import java.util.List;
import java.util.*;

public class ExtentManager {


    private static ExtentReports extentReports;
    private static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

    public synchronized static ExtentReports getReporter(String filePath) {
        if (extentReports == null) {
            extentReports = setReporter(filePath);
        }
        return extentReports;
    }

    public synchronized static ExtentReports getReporter() {
        return extentReports;
    }

    private static ExtentReports setReporter(String reportPath) {
        reportPath = getReportPath(reportPath);

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath, ViewStyle.SPA);
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        spark.config().setCSS("css-string");
        spark.config().setDocumentTitle("automation test report");
        spark.config().setEncoding("utf-8");
        spark.config().setJS("js-string");
        spark.config().setProtocol(Protocol.HTTPS);
        spark.config().setReportName("build name");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        extentReports = new ExtentReports();
        extentReports.attachReporter(spark);

        extentReports.setSystemInfo("application", Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, "application"));
        extentReports.setSystemInfo("environment", Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, "environment"));

        return extentReports;

    }

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest extentTest = extentReports.createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), extentTest);
        return extentTest;
    }

    public static synchronized void endTest() {
        extentReports.removeTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    private static String getReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdirs()) {
                System.out.println("Test Report Directory is created: " + path);
            } else {
                System.out.println("Failed to create directory: " + path);
                System.getProperty("user.dir");
            }
        } else {
            System.out.println("Test Report Directory already exists: " + path);
        }
        return path;
    }

    private static List<String> getAddress(String address) {
        List<String> addressList = new ArrayList<String>();

        if (address.isEmpty())
            return addressList;

        if (address.indexOf(";") > 0) {
            String[] addresses = address.split(";");

            addressList.addAll(Arrays.asList(addresses));
        } else {
            addressList.add(address);
        }

        return addressList;
    }

}

