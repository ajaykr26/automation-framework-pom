package automation.library.reporting;

import automation.library.selenium.BaseTest;
import automation.library.selenium.driver.factory.DriverFactory;
import com.aventstack.extentreports.*;
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

import static automation.library.selenium.BaseTest.reportPath;
import static automation.testsuite.SmokeSuite.testname;
import static automation.testsuite.SmokeSuite.testsuite;


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
                addScreenCaptureFromPath(getScreenshotPath());
                ExtentManager.getTest().log(Status.FAIL, message);
                break;
            case PASS:
                addScreenCaptureFromPath(getScreenshotPath());
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

    public static void getReportName() {

    }

    public static void startFinalStep(Boolean scenarioIsFailed) {

    }

    public static void stopFinalStep() {

    }

    public static void failTest(Throwable message) {

    }

    public static void updateReport(String pageName, String methodName, Status status, String message) {
        try {
            String screenshotPath = getScreenshotAsBase64String();
            switch (status) {
                case DEBUG:
                    ExtentManager.getTest().debug("Page name: " + pageName + " | Method name: " + methodName + "\n" + message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
                    break;
                case ERROR:
                    ExtentManager.getTest().error("Page name: " + pageName + " | Method name: " + methodName + "\n" + message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
                    break;
                case FAIL:
                    ExtentManager.getTest().fail("Page name: " + pageName + " | Method name: " + methodName + "\n" + message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
                    break;
                case PASS:
                    ExtentManager.getTest().pass("Page name: " + pageName + " | Method name: " + methodName + "\n" + message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
                    break;
                case SKIP:
                    ExtentManager.getTest().skip("Page name: " + pageName + " | Method name: " + methodName + "\n" + message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
                    break;
                case INFO:
                    ExtentManager.getTest().info("Page name: " + pageName + " | Method name: " + methodName + "\n" + message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
                    break;
                case WARNING:
                    ExtentManager.getTest().warning("Page name: " + pageName + " | Method name: " + methodName + "\n" + message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
                    break;
                default:
                    ExtentManager.getTest().info("Page name: " + pageName + " | Method name: " + methodName + "\n" + message);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static void takeScreenshot() {
        String screenshotPath = reportPath + "/screenshots";
        String targetLocation = null;
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String fileSeperator = System.getProperty("file.separator");
        String screenShotName = timeStamp + ".png";
        logger.info("Screen shots path - " + screenshotPath);
        try {
            File file = new File(screenshotPath);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    logger.info("Directory: " + file.getAbsolutePath() + " is created!");
                } else {
                    logger.info("Failed to create directory: " + file.getAbsolutePath());
                }

            }

            File screenshotFile = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
            targetLocation = file + fileSeperator + screenShotName;
            File targetFile = new File(targetLocation);
            logger.info("Screen shot file location - " + screenshotFile.getAbsolutePath());
            logger.info("Target File location - " + targetFile.getAbsolutePath());
            FileHandler.copy(screenshotFile, targetFile);

        } catch (FileNotFoundException e) {
            logger.info("File not found exception occurred while taking screenshot " + e.getMessage());
        } catch (Exception e) {
            logger.info("An exception occurred while taking screenshot " + e.getCause());
        }

    }

    public static String getScreenshotPath() {
        String screenshotPath = reportPath + "/screenshots";
        String targetLocation = null;
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String fileSeperator = System.getProperty("file.separator");
        String screenShotName = timeStamp + ".png";
        logger.info("Screen shots path - " + screenshotPath);
        try {
            File file = new File(screenshotPath);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    logger.info("Directory: " + file.getAbsolutePath() + " is created!");
                } else {
                    logger.info("Failed to create directory: " + file.getAbsolutePath());
                }

            }

            File screenshotFile = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
            targetLocation = file + fileSeperator + screenShotName;
            File targetFile = new File(targetLocation);
            logger.info("Screen shot file location - " + screenshotFile.getAbsolutePath());
            logger.info("Target File location - " + targetFile.getAbsolutePath());
            FileHandler.copy(screenshotFile, targetFile);

        } catch (FileNotFoundException e) {
            logger.info("File not found exception occurred while taking screenshot " + e.getMessage());
        } catch (Exception e) {
            logger.info("An exception occurred while taking screenshot " + e.getCause());
        }
        return targetLocation;
    }

    private static String getScreenshotAsBase64String() {
        String screenshotAsString = "";
        String screenshotPath = reportPath + "/screenshots";
        logger.info("Screen shots path - " + screenshotPath);
        try {
            File file = new File(screenshotPath);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    logger.info("Directory: " + file.getAbsolutePath() + " is created!");
                } else {
                    logger.info("Failed to create directory: " + file.getAbsolutePath());
                }

            }

            screenshotAsString = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.BASE64);

        } catch (Exception e) {
            logger.info("An exception occurred while taking screenshot " + e.getCause());
        }

        return screenshotAsString;
    }
}
