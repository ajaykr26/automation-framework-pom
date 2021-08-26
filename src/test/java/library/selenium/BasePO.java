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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasePO {

    protected static Logger logger = LogManager.getLogger(BasePO.class);
    private WebElement dropdown = null;
    private Select selectList = null;
    private WebElement element = null;

    public BasePO() {
    }

    public void launchApplication(String applicationName) {
        String url = Property.getProperty(Constants.getCurrentEnvFilePath(), applicationName);
        if (url != null) {
            getDriver().manage().window().maximize();
            logger.info("application {} launched in new window", url);
            getDriver().get(url);
            ExtentReporter.updateReport("launchApplication", "BasePO", Status.PASS, "application launched successfully");
        } else {
            ExtentReporter.updateReport("launchApplication", "BasePO", Status.FAIL, "application not launched successfully");
            logger.error("application url not defined in the file: " + Constants.getCurrentEnvFilePath());
        }
    }

    public void launchApplication(String applicationName, String browserName) {
        String url = Property.getProperty(Constants.getCurrentEnvFilePath(), applicationName);
        if (url != null) {
            getDriver(browserName).manage().window().maximize();
            logger.info("application {} launched in new window", url);
            getDriver().get(url);
            ExtentReporter.updateReport("Facebook_LoginPage", "launchApplication", Status.PASS, "application launched successfully");
        } else {
            ExtentReporter.updateReport("Facebook_LoginPage", "launchApplication", Status.FAIL, "application not launched successfully");
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

    public void selectOptionFromDropdown(String locatorType, String optionBy, String option, String locator) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        selectList = new org.openqa.selenium.support.ui.Select(dropdown);

        if (optionBy.equals("selectByIndex"))
            selectList.selectByIndex(Integer.parseInt(option) - 1);
        else if (optionBy.equals("value"))
            selectList.selectByValue(option);
        else if (optionBy.equals("text"))
            selectList.selectByVisibleText(option);
    }

    public void selectOptionFromDropdown(String optionBy, String option, By by) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(by));
        selectList = new org.openqa.selenium.support.ui.Select(dropdown);

        if (optionBy.equals("selectByIndex"))
            selectList.selectByIndex(Integer.parseInt(option) - 1);
        else if (optionBy.equals("value"))
            selectList.selectByValue(option);
        else if (optionBy.equals("text"))
            selectList.selectByVisibleText(option);
    }

    public void unselectAllOptionFromMultiselectDropdown(String locatorType, String locator) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        selectList = new org.openqa.selenium.support.ui.Select(dropdown);
        selectList.deselectAll();
    }

    public void deselectOptionFromDropdown(String locatorType, String optionBy, String option, String locator) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        selectList = new org.openqa.selenium.support.ui.Select(dropdown);

        if (optionBy.equals("selectByIndex"))
            selectList.deselectByIndex(Integer.parseInt(option) - 1);
        else if (optionBy.equals("value"))
            selectList.deselectByValue(option);
        else if (optionBy.equals("text"))
            selectList.deselectByVisibleText(option);
    }

    public void checkCheckbox(String locatorType, String locator) {
        WebElement checkbox = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        if (!checkbox.isSelected())
            checkbox.click();
    }

    public void uncheckCheckbox(String locatorType, String locator) {
        WebElement checkbox = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        if (checkbox.isSelected())
            checkbox.click();
    }

    public void toggleCheckbox(String locatorType, String locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator))).click();
    }

    public void selectRadioButton(String locatorType, String locator) {
        WebElement radioButton = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        if (!radioButton.isSelected())
            radioButton.click();
    }

    public void selectRadioButton(By by) {
        WebElement radioButton = getWait().until(ExpectedConditions.presenceOfElementLocated(by));
        if (!radioButton.isSelected())
            radioButton.click();
    }

    public void selectOptionFromRadioButtonGroup(String optionBy, String option, By by) {
        List<WebElement> radioButtonGroup = getDriver().findElements(by);
        for (WebElement rb : radioButtonGroup) {
            if (optionBy.equals("value")) {
                if (rb.getAttribute("value").equals(option) && !rb.isSelected())
                    rb.click();
            } else if (optionBy.equals("text")) {
                if (rb.getText().equals(option) && !rb.isSelected())
                    rb.click();
            }
        }
    }

    public void selectOptionFromRadioButtonGroup(String locatorType, String option, String by, String locator) {
        List<WebElement> radioButtonGroup = getDriver().findElements(getObjectByType(locatorType, locator));
        for (WebElement rb : radioButtonGroup) {
            if (by.equals("value")) {
                if (rb.getAttribute("value").equals(option) && !rb.isSelected())
                    rb.click();
            } else if (by.equals("text")) {
                if (rb.getText().equals(option) && !rb.isSelected())
                    rb.click();
            }
        }
    }


    public void click(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        element.click();
    }

    public void clickJS(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public void doubleClick(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));

        Actions action = new Actions(getDriver());
        action.moveToElement(element).doubleClick().perform();
    }


    public void clickForcefully(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public void enterText(String locatorType, String text, String locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        getDriver().findElement(getObjectByType(locatorType, locator)).sendKeys(text);
    }


    public void clearText(String locatorType, String locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        getDriver().findElement(getObjectByType(locatorType, locator)).clear();
    }


    public void selectElementFromDropdownByType(Select select_list, String byType, String option) {
        if (byType.equals("selectByIndex")) {
            int index = Integer.parseInt(option);
            select_list.selectByIndex(index - 1);
        } else if (byType.equals("value"))
            select_list.selectByValue(option);
        else if (byType.equals("text"))
            select_list.selectByVisibleText(option);
    }

    public void handleAlert(String decision) {
        if (decision.equals("accept"))
            getDriver().switchTo().alert().accept();
        else
            getDriver().switchTo().alert().dismiss();
    }

    public void navigateTo(String url) {
        getDriver().get(url);
    }

    public void navigate(String direction) {
        if (direction.equals("back"))
            getDriver().navigate().back();
        else
            getDriver().navigate().forward();
    }

    public void closeDriver() {
        getDriver().close();
    }

    public Keys getKey() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            return Keys.CONTROL;
        else if (os.contains("nux") || os.contains("nix"))
            return Keys.CONTROL;
        else if (os.contains("mac"))
            return Keys.COMMAND;
        else
            return null;
    }

    public void zoomInOut(String inOut) {
        WebElement Sel = getDriver().findElement(getObjectByType("tagName", "html"));
        if (inOut.equals("ADD"))
            Sel.sendKeys(Keys.chord(getKey(), Keys.ADD));
        else if (inOut.equals("SUBTRACT"))
            Sel.sendKeys(Keys.chord(getKey(), Keys.SUBTRACT));
        else if (inOut.equals("reset"))
            Sel.sendKeys(Keys.chord(getKey(), Keys.NUMPAD0));
    }

    public void zoomInOutTillElementDisplay(String accessType, String inOut, String accessName) {
        Actions action = new Actions(getDriver());
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(accessType, accessName)));
        while (true) {
            if (element.isDisplayed())
                break;
            else
                action.keyDown(getKey()).sendKeys(inOut).keyUp(getKey()).perform();
        }
    }

    public void resizeBrowser(int width, int height) {
        getDriver().manage().window().setSize(new Dimension(width, height));
    }

    public void maximizeBrowser() {
        getDriver().manage().window().maximize();
    }

    public void hoverOverElement(String accessType, String accessName) {
        Actions action = new Actions(getDriver());
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(accessType, accessName)));
        action.moveToElement(element).perform();
    }

    public void scrollToElement(String accessType, String accessName) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(accessType, accessName)));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void scrollPage(String to) throws Exception {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        if (to.equals("end"))
            executor.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
        else if (to.equals("top"))
            executor.executeScript("window.scrollTo(Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight),0);");
        else
            throw new Exception("Exception : Invalid Direction (only scroll \"top\" or \"end\")");
    }

    public void closeNewWindow() {
        getDriver().close();
    }

    public void switchFrame(String accessType, String accessName) {
        if (accessType.equalsIgnoreCase("index"))
            getDriver().switchTo().frame(accessName);
        else {
            element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(accessType, accessName)));
            getDriver().switchTo().frame(element);
        }
    }

    public void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    public WebElement getMatchingCellElement(String textToFind, String locatorType, String locator) {
        WebElement table = getElementByType(locatorType, locator);
        List<WebElement> table_rows = table.findElements(By.tagName("tr"));
        int rows_count = table_rows.size();
        int row = 0;
        int column = 0;
        int columns_count = 0;
        List<WebElement> columns_row = null;
        first:
        for (row = 0; row < rows_count; row++) {
            columns_row = table_rows.get(row).findElements(By.tagName("td"));
            columns_count = columns_row.size();
            for (column = 0; column < columns_count; column++) {
                if (columns_row.get(column).getText().equals(textToFind)) {
                    break first;
                }
            }
        }
        if (columns_row != null) {
            return columns_row.get(column);
        } else {
            return null;
        }
    }

    public WebElement getMatchingCellElement(String textToFind, String columnName, String locatorType, String locator) {
        WebElement table = getElementByType(locatorType, locator);
        List<WebElement> headers = table.findElements(By.tagName("th"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (int col = 0; col < headers.size(); col++) {
            if (headers.get(col).getText().trim().equals(columnName))
                for (WebElement element : rows) {
                    List<WebElement> columns = element.findElements(By.tagName("td"));
                    if (columns.size() > 0 && columns.get(col).getText().equals(textToFind)) {
                        return columns.get(col);
                    }
                }
        }
        return null;
    }

    public boolean valid_locator_type(String type) {
        return Arrays.asList("id", "class", "css", "name", "xpath").contains(type);
    }


    public void validateLocator(String type) throws Exception {
        if (!valid_locator_type(type))
            throw new Exception("Invalid locator type - " + type);
    }

    public boolean valid_option_by(String option_by) {
        return Arrays.asList("text", "value", "index").contains(option_by);
    }


    public void validateOptionBy(String optionBy) throws Exception {
        if (!valid_option_by(optionBy))
            throw new Exception("Invalid option by - " + optionBy);
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public void checkTitle(String title, boolean testCase) throws Exception {
        String pageTitle = getPageTitle();

        if (testCase) {
            if (!pageTitle.equals(title))
                throw new Exception("Page Title Not Matched, Actual Page Title : " + pageTitle);
        } else {
            if (pageTitle.equals(title))
                throw new Exception("Page Title Matched, Actual Page Title : " + pageTitle);
        }
    }

    public void checkPartialTitle(String partialTitle, boolean testCase) throws Exception {
        String pageTitle = getPageTitle();
        if (testCase) {
            if (!pageTitle.contains(partialTitle))
                throw new Exception("Partial Page Title Not Present, Actual Page Title : " + pageTitle);
        } else {
            if (pageTitle.contains(partialTitle))
                throw new Exception("Partial Page Title Present, Actual Page Title : " + pageTitle);
        }
    }

    public String getElementText(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return element.getText();

    }

    public void validateElementText(String matchType, String locatorType, String actualValue, String locator) throws Exception {
        String elementText = getElementText(locatorType, locator);
        switch (matchType) {
            case "partial-match":
                if (!elementText.contains(actualValue)) {
                    throw new Exception("Text Not Matched");
                } else if (elementText.contains(actualValue)) {
                    throw new Exception("Text Matched");
                }
            case "exact-match":
                if (!elementText.equals(actualValue)) {
                    throw new Exception("Text Not Matched");
                } else if (elementText.contains(actualValue)) {
                    throw new Exception("Text Matched");
                }
        }

    }

    public void checkElementText(String locatorType, String actualValue, String locator, boolean testCase) throws Exception {
        String elementText = getElementText(locatorType, locator);

        if (testCase) {
            if (!elementText.equals(actualValue))
                throw new Exception("Text Not Matched");
        } else {
            if (elementText.equals(actualValue))
                throw new Exception("Text Matched");
        }
    }

    public void checkElementPartialText(String locatorType, String actualValue, String locator, boolean testCase) throws Exception {
        String elementText = getElementText(locatorType, locator);

        if (testCase) {
            if (!elementText.contains(actualValue))
                throw new Exception("Text Not Matched");
        } else {
            if (elementText.contains(actualValue))
                throw new Exception("Text Matched");
        }
    }

    public boolean isElementEnabled(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return element.isEnabled();
    }

    public void checkElementEnable(String locatorType, String locator, boolean testCase) throws Exception {
        boolean result = isElementEnabled(locatorType, locator);
        if (testCase) {
            if (!result)
                throw new Exception("Element Not Enabled");
        } else {
            if (result)
                throw new Exception("Element Enabled");
        }
    }

    public String getElementAttribute(String locatorType, String locator, String attributeName) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return element.getAttribute(attributeName);
    }

    public void checkElementAttribute(String locatorType, String attributeName, String attributeValue, String locator, boolean testCase) throws Exception {
        String attrVal = getElementAttribute(locatorType, locator, attributeName);
        if (testCase) {
            if (!attrVal.equals(attributeValue))
                throw new Exception("Attribute Value Not Matched");
        } else {
            if (attrVal.equals(attributeValue))
                throw new Exception("Attribute Value Matched");
        }
    }

    public boolean isElementDisplayed(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return element.isDisplayed();
    }

    public void checkElementPresence(String locatorType, String locator, boolean testCase) throws Exception {
        if (testCase) {
            if (!isElementDisplayed(locatorType, locator))
                throw new Exception("Element Not Present");
        } else {
            try {
                if (isElementDisplayed(locatorType, locator))
                    throw new Exception("Present"); //since it is negative test and we found element
            } catch (Exception e) {
                if (e.getMessage().equals("Present")) //only raise if it present
                    throw new Exception("Element Present");
            }
        }
    }

    public void isCheckboxChecked(String locatorType, String locator, boolean shouldBeChecked) throws Exception {
        WebElement checkbox = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        if ((!checkbox.isSelected()) && shouldBeChecked)
            throw new Exception("Checkbox is not checked");
        else if (checkbox.isSelected() && !shouldBeChecked)
            throw new Exception("Checkbox is checked");
    }

    public void isRadioButtonSelected(String locatorType, String locator, boolean shouldBeSelected) throws Exception {
        WebElement radioButton = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        if ((!radioButton.isSelected()) && shouldBeSelected)
            throw new Exception("Radio Button not selected");
        else if (radioButton.isSelected() && !shouldBeSelected)
            throw new Exception("Radio Button is selected");
    }

    public void isOptionFromRadioButtonGroupSelected(String locatorType, String by, String option, String locator, boolean shouldBeSelected) throws Exception {
        List<WebElement> radioButtonGroup = getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(getObjectByType(locatorType, locator)));

        for (WebElement rb : radioButtonGroup) {
            if (by.equals("value")) {
                if (rb.getAttribute("value").equals(option)) {
                    if ((!rb.isSelected()) && shouldBeSelected)
                        throw new Exception("Radio Button not selected");
                    else if (rb.isSelected() && !shouldBeSelected)
                        throw new Exception("Radio Button is selected");
                }
            } else if (rb.getText().equals(option)) {
                if ((!rb.isSelected()) && shouldBeSelected)
                    throw new Exception("Radio Button not selected");
                else if (rb.isSelected() && !shouldBeSelected)
                    throw new Exception("Radio Button is selected");
            }
        }
    }

    public String getAlertText() {
        return getDriver().switchTo().alert().getText();
    }

    public void checkAlertText(String text) throws Exception {
        if (!getAlertText().equals(text))
            throw new Exception("Text on alert pop up not matched");
    }

    public static boolean compareScreenshot(File fileExpected, File fileActual) throws IOException {
        boolean matchFlag = true;
        try {
            BufferedImage bufferedImageActual = ImageIO.read(fileActual);
            BufferedImage bufferedImageExpected = ImageIO.read(fileExpected);
            DataBuffer dataBufferFileActual = bufferedImageActual.getData().getDataBuffer();
            DataBuffer dataBufferFileExpected = bufferedImageExpected.getData().getDataBuffer();

            int sizeFileActual = dataBufferFileActual.getSize();
            for (int i = 0; i < sizeFileActual; i++) {
                if (dataBufferFileActual.getElem(i) != dataBufferFileExpected.getElem(i)) {
                    matchFlag = false;
                    break;
                }
            }
        } catch (FileNotFoundException exception) {
            logger.error(exception.getMessage());
        }
        return matchFlag;
    }

    public static void getImageFromUrl(String imageUrl, String destinationFile, String filename) throws IOException {
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        File file = new File(destinationFile + filename);
        if (!new File(destinationFile).exists()) new File(destinationFile).mkdir();
        if (file.exists()) file.delete();
        Files.copy(inputStream, file.toPath());
        inputStream.close();
    }

    public void isOptionFromDropdownSelected(String locatorType, String by, String option, String locator, boolean shouldBeSelected) throws Exception {
        Select selectList = null;
        WebElement dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        selectList = new Select(dropdown);

        String actualValue = "";
        if (by.equals("text"))
            actualValue = selectList.getFirstSelectedOption().getText();
        else
            actualValue = selectList.getFirstSelectedOption().getAttribute("value");

        if ((!actualValue.equals(option)) && (shouldBeSelected))
            throw new Exception("Option Not Selected From Dropwdown");
        else if ((actualValue.equals(option)) && (!shouldBeSelected))
            throw new Exception("Option Selected From Dropwdown");
    }

    public void wait(String time) throws NumberFormatException, InterruptedException {
        Thread.sleep(Integer.parseInt(time) * 1000);
    }

    public void waitForElementToDisplay(String accessType, String accessName, String duration) {
        By byEle = getObjectByType(accessType, accessName);
        WebDriverWait wait = (new WebDriverWait(getDriver(), Integer.parseInt(duration) * 1000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(byEle));
    }

    public void waitForElementToClick(String accessType, String accessName, String duration) {
        By byEle = getObjectByType(accessType, accessName);
        WebDriverWait wait = (new WebDriverWait(getDriver(), Integer.parseInt(duration) * 1000));
        wait.until(ExpectedConditions.elementToBeClickable(byEle));
    }

}
