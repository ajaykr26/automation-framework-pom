package library.reporting;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import library.common.TestContext;
import library.selenium.driver.factory.DriverContext;
import library.selenium.driver.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static library.selenium.BaseTest.reportPath;

public class ExtentReporter {

    private static Logger logger = LogManager.getLogger(ExtentReporter.class);

    public static void addStepLog(Status status, String message) {
        switch (status) {
            case DEBUG:
                ExtentManager.getTest().log(Status.DEBUG, message);
                break;
            case ERROR:
                ExtentManager.getTest().log(Status.ERROR, message);
                break;
            case FAIL:
                ExtentManager.getTest().log(Status.FAIL, message);
                break;
            case PASS:
                ExtentManager.getTest().log(Status.PASS, message);
                break;
            case SKIP:
                ExtentManager.getTest().log(Status.SKIP, message);
                break;
            case INFO:
                ExtentManager.getTest().log(Status.INFO, message);
                break;
            case WARNING:
                ExtentManager.getTest().log(Status.WARNING, message);
                break;
            default:
                ExtentManager.getTest().info(message);
        }
    }

    public static void updateReport(String pageName, String methodName, Status status, String message) {
        if (!DriverContext.getInstance().getTechStack().get("seleniumServer").startsWith("remote")) {
            try {
                String screenshotPath = takeScreenshot();
                switch (status) {
                    case DEBUG:
                        ExtentManager.getTest().debug(pageName + " | " + methodName + " -> " + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                        break;
                    case ERROR:
                        ExtentManager.getTest().error(pageName + " | " + methodName + " -> " + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                        break;
                    case FAIL:
                        ExtentManager.getTest().fail(pageName + " | " + methodName + " -> " + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                        break;
                    case PASS:
                        ExtentManager.getTest().pass(pageName + " | " + methodName + " -> " + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                        break;
                    case SKIP:
                        ExtentManager.getTest().skip(pageName + " | " + methodName + " -> " + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                        break;
                    case INFO:
                        ExtentManager.getTest().info(pageName + " | " + methodName + " -> " + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                        break;
                    case WARNING:
                        ExtentManager.getTest().warning(pageName + " | " + methodName + " -> " + message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                        break;
                    default:
                        ExtentManager.getTest().info(pageName + " | " + methodName + " -> " + message);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            logger.info("unable to take screenshot in headless execution");
        }


    }

    public static void addScreenCaptureFromPath(String imagePath) {
        try {
            ExtentManager.getTest().addScreenCaptureFromPath(imagePath);
        } catch (IOException exception) {
            logger.error("Screenshot failed: {}", exception.getMessage());
        }
    }

    public static void addScreenCaptureFromPath(String imagePath, String title) {
        try {
            ExtentManager.getTest().addScreenCaptureFromPath(imagePath, "Screenshot");
        } catch (IOException exception) {
            logger.error("Screenshot failed: {}", exception.getMessage());
        }
    }

    public static String getReportPath() {
        DateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "RunReport_" + format.format(new Date()) + ".html";
    }

    public static String getScreenshotPath() {
        return reportPath + "/screenshots/" + TestContext.getInstance().testdataGet("fw.testname");
    }

    public static void failTest(Throwable message) {

    }

    public static String takeScreenshot() {
        String targetLocation = null;

        if (!DriverContext.getInstance().getTechStack().get("seleniumServer").startsWith("remote")) {

            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String fileSeperator = System.getProperty("file.separator");
            String screenShotName = timeStamp + ".png";
            try {
                File file = new File(getScreenshotPath());
                if (!file.exists()) {
                    if (file.mkdirs()) {
                        logger.info("Directory: " + file.getAbsolutePath() + " is created!");
                    } else {
                        logger.info("Directory: " + file.getAbsolutePath() + "already exists");
                    }

                }
                File screenshotFile = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
                targetLocation = file + fileSeperator + screenShotName;
                File targetFile = new File(targetLocation);
                FileHandler.copy(screenshotFile, targetFile);
            } catch (FileNotFoundException e) {
                logger.info("File not found exception occurred while taking screenshot " + e.getMessage());
            } catch (Exception e) {
                logger.info("An exception occurred while taking screenshot " + e.getCause());
            }
        } else logger.info("unable to take screenshot in headless execution");

        return targetLocation;
    }



}
