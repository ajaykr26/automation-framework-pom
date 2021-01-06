package library.selenium.common;

import library.selenium.FactoryMethod;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ValidationMethods extends FactoryMethod {

    private WebElement element = null;


    public String getPageTitle() {
        return getDriver().getTitle();
    }


    public void checkTitle(String title, boolean testCase) throws TestFailed {
        String pageTitle = getPageTitle();

        if (testCase) {
            if (!pageTitle.equals(title))
                throw new TestFailed("Page Title Not Matched, Actual Page Title : " + pageTitle);
        } else {
            if (pageTitle.equals(title))
                throw new TestFailed("Page Title Matched, Actual Page Title : " + pageTitle);
        }
    }


    public void checkPartialTitle(String partialTitle, boolean testCase) throws TestFailed {
        String pageTitle = getPageTitle();
        if (testCase) {
            if (!pageTitle.contains(partialTitle))
                throw new TestFailed("Partial Page Title Not Present, Actual Page Title : " + pageTitle);
        } else {
            if (pageTitle.contains(partialTitle))
                throw new TestFailed("Partial Page Title Present, Actual Page Title : " + pageTitle);
        }
    }


    public String getElementText(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return element.getText();

    }


    public void validateElementText(String matchType, String locatorType, String actualValue, String locator) throws TestFailed {
        String elementText = getElementText(locatorType, locator);
        switch (matchType) {
            case "partial-match":
                if (!elementText.contains(actualValue)) {
                    throw new TestFailed("Text Not Matched");
                } else if (elementText.contains(actualValue)) {
                    throw new TestFailed("Text Matched");
                }
            case "exact-match":
                if (!elementText.equals(actualValue)) {
                    throw new TestFailed("Text Not Matched");
                } else if (elementText.contains(actualValue)) {
                    throw new TestFailed("Text Matched");
                }
        }

    }


    public void checkElementText(String locatorType, String actualValue, String locator, boolean testCase) throws TestFailed {
        String elementText = getElementText(locatorType, locator);

        if (testCase) {
            if (!elementText.equals(actualValue))
                throw new TestFailed("Text Not Matched");
        } else {
            if (elementText.equals(actualValue))
                throw new TestFailed("Text Matched");
        }
    }


    public void checkElementPartialText(String locatorType, String actualValue, String locator, boolean testCase) throws TestFailed {
        String elementText = getElementText(locatorType, locator);

        if (testCase) {
            if (!elementText.contains(actualValue))
                throw new TestFailed("Text Not Matched");
        } else {
            if (elementText.contains(actualValue))
                throw new TestFailed("Text Matched");
        }
    }


    public boolean isElementEnabled(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return element.isEnabled();
    }


    public void checkElementEnable(String locatorType, String locator, boolean testCase) throws TestFailed {
        boolean result = isElementEnabled(locatorType, locator);
        if (testCase) {
            if (!result)
                throw new TestFailed("Element Not Enabled");
        } else {
            if (result)
                throw new TestFailed("Element Enabled");
        }
    }


    public String getElementAttribute(String locatorType, String locator, String attributeName) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return element.getAttribute(attributeName);
    }


    public void checkElementAttribute(String locatorType, String attributeName, String attributeValue, String locator, boolean testCase) throws TestFailed {
        String attrVal = getElementAttribute(locatorType, locator, attributeName);
        if (testCase) {
            if (!attrVal.equals(attributeValue))
                throw new TestFailed("Attribute Value Not Matched");
        } else {
            if (attrVal.equals(attributeValue))
                throw new TestFailed("Attribute Value Matched");
        }
    }


    public boolean isElementDisplayed(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        return element.isDisplayed();
    }


    public void checkElementPresence(String locatorType, String locator, boolean testCase) throws TestFailed {
        if (testCase) {
            if (!isElementDisplayed(locatorType, locator))
                throw new TestFailed("Element Not Present");
        } else {
            try {
                if (isElementDisplayed(locatorType, locator))
                    throw new Exception("Present"); //since it is negative test and we found element
            } catch (Exception e) {
                if (e.getMessage().equals("Present")) //only raise if it present
                    throw new TestFailed("Element Present");
            }
        }
    }


    public void isCheckboxChecked(String locatorType, String locator, boolean shouldBeChecked) throws TestFailed {
        WebElement checkbox = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        if ((!checkbox.isSelected()) && shouldBeChecked)
            throw new TestFailed("Checkbox is not checked");
        else if (checkbox.isSelected() && !shouldBeChecked)
            throw new TestFailed("Checkbox is checked");
    }


    public void isRadioButtonSelected(String locatorType, String locator, boolean shouldBeSelected) throws TestFailed {
        WebElement radioButton = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        if ((!radioButton.isSelected()) && shouldBeSelected)
            throw new TestFailed("Radio Button not selected");
        else if (radioButton.isSelected() && !shouldBeSelected)
            throw new TestFailed("Radio Button is selected");
    }

    public void isOptionFromRadioButtonGroupSelected(String locatorType, String by, String option, String locator, boolean shouldBeSelected) throws TestFailed {
        List<WebElement> radioButtonGroup = getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(getObjectByType(locatorType, locator)));

        for (WebElement rb : radioButtonGroup) {
            if (by.equals("value")) {
                if (rb.getAttribute("value").equals(option)) {
                    if ((!rb.isSelected()) && shouldBeSelected)
                        throw new TestFailed("Radio Button not selected");
                    else if (rb.isSelected() && !shouldBeSelected)
                        throw new TestFailed("Radio Button is selected");
                }
            } else if (rb.getText().equals(option)) {
                if ((!rb.isSelected()) && shouldBeSelected)
                    throw new TestFailed("Radio Button not selected");
                else if (rb.isSelected() && !shouldBeSelected)
                    throw new TestFailed("Radio Button is selected");
            }
        }
    }


    public String getAlertText() {
        return getDriver().switchTo().alert().getText();
    }


    public void checkAlertText(String text) throws TestFailed {
        if (!getAlertText().equals(text))
            throw new TestFailed("Text on alert pop up not matched");
    }


    public void isOptionFromDropdownSelected(String locatorType, String by, String option, String locator, boolean shouldBeSelected) throws TestFailed {
        Select selectList = null;
        WebElement dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        selectList = new Select(dropdown);

        String actualValue = "";
        if (by.equals("text"))
            actualValue = selectList.getFirstSelectedOption().getText();
        else
            actualValue = selectList.getFirstSelectedOption().getAttribute("value");

        if ((!actualValue.equals(option)) && (shouldBeSelected))
            throw new TestFailed("Option Not Selected From Dropwdown");
        else if ((actualValue.equals(option)) && (!shouldBeSelected))
            throw new TestFailed("Option Selected From Dropwdown");
    }
}
