package library.selenium.common;

import library.selenium.BasePO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class SelectMethods extends BasePO {
    private WebElement dropdown = null;
    private org.openqa.selenium.support.ui.Select selectList = null;

    public void selectelementfromdropdownbytype(org.openqa.selenium.support.ui.Select select_list, String bytype, String option) {
        if (bytype.equals("selectByIndex")) {
            int index = Integer.parseInt(option);
            select_list.selectByIndex(index - 1);
        } else if (bytype.equals("value"))
            select_list.selectByValue(option);
        else if (bytype.equals("text"))
            select_list.selectByVisibleText(option);
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
}
