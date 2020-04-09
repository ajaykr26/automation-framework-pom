package automation.library.selenium.common;

import automation.library.selenium.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ClickElementsMethods extends BasePage implements MethodObjects {

    private WebElement element = null;

    public void click(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        element.click();
    }

    public void clickForcefully(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public void doubleClick(String locatorType, String locator) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectByType(locatorType, locator)));

        Actions action = new Actions(getDriver());
        action.moveToElement(element).doubleClick().perform();
    }
}