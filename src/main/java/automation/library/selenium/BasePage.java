package automation.library.selenium;

import automation.library.common.Constants;
import automation.library.common.Encryptor;
import automation.library.common.Property;
import automation.library.common.TestContext;
import automation.library.selenium.common.MethodObjects;
import automation.library.selenium.driver.factory.DriverFactory;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasePage implements MethodObjects {
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public BasePage() {
    }

    public WebDriver getDriver() {
        logger.debug("obtaining the automation.library.getDriver() for current thread");
        return DriverFactory.getInstance().getDriver();
    }

    public WebDriverWait getWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverFactory.getInstance().getWait();
    }

    public void quitDriver() {
        logger.debug("quiting the automation.library.getDriver() and removing from current thread of automation.library.getDriver() factory");
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
        String envProp = Property.getVariable("cukes.env");
        String secureTextFilePath = Constants.ENVIRONMENT_PATH + "SecureText-" + envProp + ".properties";
        String encryptedString = parseText(secureTextFilePath, encryptedStringKey);
        String decryptedString = null;
        if (encryptedString != null) {
            decryptedString = Encryptor.decrypt(encryptedString);
            return decryptedString;
        } else {
            logger.error("entry for key '{}' was not found in the SecureText property file", encryptedStringKey);
        }
        return null;
    }

    public String parseText(String string) {
        String parsedValue = null;
        if (TestContext.getInstance().testdataGet(string) != null) {
            parsedValue = TestContext.getInstance().testdataGet(string).toString();
            logger.info("returning the value of" + string + "from JSON");
        } else if (Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, string) != null) {
            parsedValue = Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, string);
            logger.info("returning the value of " + string + "from config.properties file");
        } else {
            parsedValue = string;
            logger.info("returning the value as it is provided in the steps");
        }
        return parsedValue;
    }

    public String parseMathcingText(String string) {
        String parsedValue = null;
        String parsedKeyJSON = parseKeyJSON(string);
        String parsedKeyProps = parseKeyProps(string);
        if (TestContext.getInstance().testdataGet(parsedKeyJSON) != null) {
            parsedValue = TestContext.getInstance().testdataGet(parsedKeyJSON).toString();
            logger.info("returning the value of" + parsedKeyJSON + "from JSON");
        } else if (Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, parsedKeyProps) != null) {
            parsedValue = Property.getProperty(Constants.ENVIRONMENT_PROP_FILE, parsedKeyProps);
            logger.info("returning the value of " + parsedKeyProps + "from config.properties file");
        } else {
            parsedValue = string;
            logger.info("returning the value as it is provided in the steps");
        }
        return parsedValue;
    }

    public String parseText(String fileName, String string) {
        String parsedValue = null;
        String parsedKeyJSON = parseKeyJSON(string);
        String parsedKeyProps = parseKeyProps(string);
        if (TestContext.getInstance().testdataGet(parsedKeyJSON) != null) {
            parsedValue = TestContext.getInstance().testdataGet(parsedKeyJSON).toString();
            logger.info("returning the value of" + parsedKeyJSON + "from JSON");
        } else if (Property.getProperty(fileName, parsedKeyProps) != null) {
            parsedValue = Property.getProperty(fileName, parsedKeyProps);
            logger.info("returning the value of " + parsedKeyProps + "from config.properties file");
        } else {
            parsedValue = string;
            logger.info("returning the value as it is provided in the steps");
        }
        return parsedValue;
    }

    public By getObjectByType(String locatorType, String locator) {
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

    public WebElement getElementByType(String locatorType, String locator) {
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
        return getDriver().findElement(getObject(locator));
    }

    public List<WebElement> getElementsByLocator(String locator) {
        return getDriver().findElements(getObject(locator));
    }

    public void waitForPageToLoad() {
        long timeOut = Integer.parseInt(Property.getProperty(Constants.SELENIUM_RUNTIME_PATH, "waitForPageLoad")) * 1000;
        long endTime = System.currentTimeMillis() + timeOut;
        while (System.currentTimeMillis() < endTime) {
            if (String.valueOf(((JavascriptExecutor) getDriver()).executeScript("return document.readyState")).equals("complete")) {
                logger.info("page loaded completely");
                break;
            } else {
                logger.info("error in page loading,  time out reached:");
            }
        }
    }

    public void performDriverOperation(String action) {
        logger.debug("performing selenium automation.library.getDriver() operation:{}; {}", action);
        switch (action) {
            case "launch":
                getDriver();
                break;
            case "back":
                getDriver().navigate().back();
                break;
            case "forward":
                getDriver().navigate().forward();
                break;
            case "quit":
                quitDriver();
        }
    }

    public SoftAssertions softAssertions() {
        return TestContext.getInstance().softAssertions();
    }

    public Boolean includesClass(String actClasses, String expClass) {
        Optional<String> classFindResult = Arrays.stream(actClasses.split(" ")).filter(cls -> cls.equals(expClass)).findFirst();
        if (classFindResult.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public WebElement getElement(By by) {
        return getDriver().findElement(by);
    }

    public void enterTextToObject(By by, String text) {
        getElement(by).sendKeys(text);
    }

    public void enterTextToElement(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void clickOnObject(By by) {
        getElement(by).click();
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

    public boolean isObjectDisplayed(By by) {
        try {
            return getElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getTextFromObject(By by) {

        return getElement(by).getText().trim();
    }

    public String elementGetText(WebElement element) {

        return element.getText().trim();
    }

    public boolean isObjectsPresent(By by) {
        try {
            getDriver().findElements(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
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

    public void waitForObjectToAppear(By by) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void implicitlyWait(long time) {
        getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public void explicitlyWaitForObject(By by, long time) {
        WebDriverWait wait = new WebDriverWait(getDriver(), time);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void explicitlyWaitForElement(WebElement element, long time) {
        WebDriverWait wait = new WebDriverWait(getDriver(), time);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForObjectToDisappear(By by) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForElementToDisappear(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForObjectTextToDisappear(By by, String text) {
        getWait().until(ExpectedConditions.not(ExpectedConditions.textToBe(by, text)));
    }

    public void waitForElementTextToDisappear(WebElement element, String text) {
        getWait().until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, text)));
    }

    public void open(String url) {
        getDriver().get(url);
    }


}
