package library.selenium;

import com.aventstack.extentreports.Status;
import library.common.Constants;
import library.common.Encryptor;
import library.common.Property;
import library.common.TestContext;
import library.reporting.ExtentReporter;
import library.selenium.driver.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasePO {

    protected static Logger logger = LogManager.getLogger(BasePO.class);

    public BasePO() {
    }

    public void launchApplication(String applicationName) {
        String url = Property.getProperty(Constants.getCurrentEnvFilePath(), applicationName);
        if (url != null) {
            getDriver().manage().window().maximize();
            logger.info("application {} launched in new window", url);
            getDriver().get(url);
            ExtentReporter.updateReport("launchApplication",  "BasePO", Status.PASS, "application launched successfully");
        } else {
            ExtentReporter.updateReport("launchApplication",  "BasePO", Status.FAIL, "application not launched successfully");
            logger.error("application url not defined in the file: " + Constants.getCurrentEnvFilePath());
        }
    }

    public WebDriver getDriver() {
        logger.debug("obtaining the driver for current thread");
        return DriverFactory.getInstance().getDriver();
    }

    public WebDriver getDriver(String browser) {
        logger.debug("creating the {} driver for current thread", browser);
        return DriverFactory.getInstance().getDriver(browser);
    }

    protected WebDriverWait getWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverFactory.getInstance().getWait();
    }

    public void waitForPageToLoad() {
        WebDriverWait waitForPageLoad = new WebDriverWait(getDriver(), Property.getProperties(Constants.RUNTIME_PROP_FILE).getInt("waitForPageLoad", 120));
        waitForPageLoad.until(webDriver -> ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").toString().equals("complete"));
    }

    private void quitDriver() {
        logger.debug("quiting the driver and removing from current thread");
        DriverFactory.getInstance().quit();
    }

    private String parseKeyJSON(String string) {
        String parsed_data = null;

        Pattern patternJSON = Pattern.compile("#\\((.*)\\)");
        Matcher matcherJSON = patternJSON.matcher(string);

        if (matcherJSON.matches()) {
            parsed_data = matcherJSON.group(1);
        } else {
            parsed_data = string;
        }
        return parsed_data;
    }

    private String parseKeyProps(String string) {
        String parsed_data = null;
        Pattern patternProp = Pattern.compile("@\\((.*)\\)");
        Matcher matcherProp = patternProp.matcher(string);

        if (matcherProp.matches()) {
            parsed_data = matcherProp.group(1);
        } else {
            parsed_data = string;
        }
        return parsed_data;

    }

    public String parseSecureText(String encryptedStringKey) {
        String environment = Property.getVariable("environment") != null ? Property.getVariable("environment") : "UAT";
        String secureTextFilePath = Constants.ENVIRONMENT_PATH + "SecureText-" + environment + ".properties";
        String encryptedString = parseText(secureTextFilePath, encryptedStringKey);
        String decryptedString = null;
        if (encryptedString != null) {
            decryptedString = Encryptor.decrypt(encryptedString);
            return decryptedString;
        } else {
            logger.error("entry for key '{}' was not found in the SecureText property file", encryptedStringKey);
            return null;
        }
    }

    protected static String parseText(String string) {
        String parsedValue = null;
        if (TestContext.getInstance().testdataGet(string) != null) {
            parsedValue = TestContext.getInstance().testdataGet(string).toString();
            logger.info("returning the value of " + string + " from data dictionary");
        } else if (Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, string) != null) {
            parsedValue = Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, string);
            logger.info("returning the value of " + string + " from ENVIRONMENT_PROP_FILE properties file");
        } else {
            parsedValue = string;
            logger.info("returning the value as it is provided in the steps");
        }
        return parsedValue;
    }

    private String parseText(String fileName, String string) {
        String parsedValue = null;
        String parsedKeyJSON = parseKeyJSON(string);
        String parsedKeyProps = parseKeyProps(string);
        if (TestContext.getInstance().testdataGet(parsedKeyJSON) != null) {
            parsedValue = TestContext.getInstance().testdataGet(parsedKeyJSON).toString();
            logger.info("returning the value of" + parsedKeyJSON + "from JSON");
        } else if (Property.getProperty(fileName, parsedKeyProps) != null) {
            parsedValue = Property.getProperty(fileName, parsedKeyProps);
            logger.info("returning the value of " + parsedKeyProps + "from runtime.properties file");
        } else {
            parsedValue = string;
            logger.info("returning the value as it is provided in the steps");
        }
        return parsedValue;
    }

    protected String parseMathcingText(String string) {
        String parsedValue = null;
        String parsedKeyJSON = parseKeyJSON(string);
        String parsedKeyProps = parseKeyProps(string);
        if (TestContext.getInstance().testdataGet(parsedKeyJSON) != null) {
            parsedValue = TestContext.getInstance().testdataGet(parsedKeyJSON).toString();
            logger.info("returning the value of" + parsedKeyJSON + "from JSON");
        } else if (Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, parsedKeyProps) != null) {
            parsedValue = Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, parsedKeyProps);
            logger.info("returning the value of " + parsedKeyProps + "from ENVIRONMENT_PROP_FILE properties file");
        } else {
            parsedValue = string;
            logger.info("returning the value as it is provided in the steps");
        }
        return parsedValue;
    }

    public void performDriverOperation(String action) {
        logger.debug("performing driver operation: '{}'", action);
        switch (action) {
            case "launch":
                getDriver();
                break;
            case "backward":
                getDriver().navigate().back();
                break;
            case "forward":
                getDriver().navigate().forward();
                break;
            case "quit":
                quitDriver();
            case "close":
                getDriver().close();
        }
    }

    public SoftAssertions softAssertions() {
        return TestContext.getInstance().softAssertions();
    }

    public void open(String url) {
        logger.info("navigating to URL: '{}'", url);
        getDriver().navigate().to(parseText(url));
    }

    public Boolean includesClass(String actClasses, String expClass) {
        Optional<String> classFindResult = Arrays.stream(actClasses.split(" ")).filter(cls -> cls.equals(expClass)).findFirst();
        if (classFindResult.isPresent()) {
            return true;
        } else {
            return false;
        }
    }


    //by objects************************************************
    protected WebElement getElement(By by) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        logger.info("element by '{}' located successfully", by);
        return getDriver().findElement(by);
    }

    public List<WebElement> getNestedElementsLocatedBy(final By parent, final By childLocator) {
        List<WebElement> allChildren = getDriver().findElement(parent).findElements(childLocator);
        return allChildren.isEmpty() ? null : allChildren;
    }

    public List<WebElement> getElements(By by) {
        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        logger.info("elements by '{}' located successfully", by);
        return getDriver().findElements(by);
    }

    protected void enterTextToObject(By by, String text) {

        getElement(by).sendKeys(text);
        logger.info("entered text '{}' into element by: '{}'", text, by);

    }

    protected void clickOnObject(By by) {

        getElement(by).click();
        logger.info("clicked on element by: '{}'", by);
    }

    public boolean isObjectDisplayed(By by) {
        try {
            return getElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getTextFromObject(By by) {
        logger.info("getting displayed text at object '{}'", by);
        return getElement(by).getText().trim();
    }

    public void storeTextFromObject(String dictionaryKey, By by) {
        String displayedText = getElement(by).getText().trim();
        TestContext.getInstance().testdataPut(dictionaryKey, displayedText);
        logger.info("displayed text stored in the data dictionary as '{}': '{}'", dictionaryKey, displayedText);
        ExtentReporter.addStepLog(Status.PASS, String.format("displayed text stored in the data dictionary as '{}': '{}'", dictionaryKey, displayedText));

    }

    public boolean isObjectsPresent(By by) {
        try {
            getDriver().findElements(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitForObjectToAppear(By by) {
        logger.info("waiting for element '{}' to be visible", by);
        getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void explicitlyWaitForObject(By by, long time) {
        logger.info("waiting for element '{}' to be visible", by);
        getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForObjectToDisappear(By by) {
        logger.info("waiting for element '{}' to be disappear", by);
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForObjectTextToDisappear(By by, String text) {
        logger.info("waiting for element '{}' with text '{}' to be disappear", by, text);
        getWait().until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementWithText(by, text)));

    }


    //web element*********************************************
    public void enterTextToElement(WebElement element, String text) {
        element.sendKeys(parseText(text));
    }

    public void clickOnElement(WebElement element) {
        element.click();
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String elementGetText(WebElement element) {

        return element.getText().trim();
    }

    public boolean isElementPresent(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitForElementToAppear(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeVisible(WebElement element) {
        logger.info("waiting for element '{}' to be visible", element);
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementTextToDisappear(WebElement element, String text) {
        logger.info("waiting for element '{}' with text '{}' to be disappear", element, text);
        getWait().until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, text)));

    }

    public Object getAllAttributeOfElement(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        return executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; index++) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", element);
    }

    //string locator*********************************************
    protected By getObjectByType(String locatorType, String locator) {
        switch (locatorType) {
            case "id":
                return By.id(locator);
            case "name":
                return By.name(locator);
            case "class":
                return By.className(locator);
            case "xpath":
                return By.xpath(locator);
            case "css":
                return By.cssSelector(locator);
            case "linkText":
                return By.linkText(locator);
            case "partialLinkText":
                return By.partialLinkText(locator);
            case "tagName":
                return By.tagName(locator);
            default:
                return null;

        }
    }

    protected WebElement getElementByType(String locatorType, String locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return getDriver().findElement(getObjectByType(locatorType, locator));

    }

    public List<WebElement> getElementsByType(String locatorType, String locator) throws Throwable {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return getDriver().findElements(getObjectByType(locatorType, locator));

    }

    public By getObject(String locator) {
        return getObjectByType("xpath", locator);
    }

    public WebElement getElementByLocator(String locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locator)));
        logger.info("element by xpath:'{}' located successfully", locator);
        return getDriver().findElement(getObject(locator));
    }

    public List<WebElement> getElementsByLocator(String locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locator)));
        logger.info("elements by xpath:'{}' located successfully", locator);
        return getDriver().findElements(getObject(locator));
    }

}
