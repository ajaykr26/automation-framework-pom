package library.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewStyle;
import library.common.Constants;
import library.common.Encryptor;
import library.common.Property;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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

    public static void launchReport(String filepath) {
        File htmlFile = new File(filepath);
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String parseEmailPassword() {
        String encryptedString = Property.getProperty(Constants.ENVIRONMENT_SECURE_PROP_FILE, "emailPassword");
        String decryptedString = null;
        if (encryptedString != null) {
            decryptedString = Encryptor.decrypt(encryptedString);
            return decryptedString;
        } else {
            return null;
        }
    }

    private static void send(String filename) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                String password = parseEmailPassword();
                return new PasswordAuthentication(System.getProperty("fw.emailSender"), password);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(System.getProperty("fw.emailSender")));
        message.setSubject(System.getProperty("fw.emailSubject"));

        // Create object to add multimedia type content
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setText("Please download the report and open in browser");

        // Create data source and pass the filename
        DataSource source = new FileDataSource(filename);

        // Create object of MimeMultipart class
        Multipart multipart = new MimeMultipart();
        BodyPart messageBodyPart2 = new MimeBodyPart();

        multipart.addBodyPart(messageBodyPart2);
        // set the handler
        messageBodyPart2.setDataHandler(new DataHandler(source));

        // set the file
        messageBodyPart2.setFileName(filename);

        // set the content
        message.setContent(multipart);

        List<String> toList = getAddress(System.getProperty("fw.emailRecipient"));
        for (String address : toList) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
        }

        // default cc list
        String cc = "";
        List<String> ccList = getAddress(cc);
        for (String address : ccList) {
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(address));
        }

        // default bcc list
        String bcc = "";
        List<String> bccList = getAddress(bcc);
        for (String address : bccList) {
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(address));
        }

        Transport.send(message);
    }

    public static void emailReport(String filename) throws MessagingException {
        if (Boolean.parseBoolean(System.getProperty("fw.sendEmail"))) {
            send(filename);
        }
    }

}

