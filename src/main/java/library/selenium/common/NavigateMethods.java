package library.selenium.common;

import library.selenium.BasePO;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class NavigateMethods extends BasePO {
    private WebElement element = null;
    private String old_win = null;
    private String lastWinHandle;


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

    public void switchToNewWindow() {
        old_win = getDriver().getWindowHandle();
        for (String winHandle : getDriver().getWindowHandles())
            lastWinHandle = winHandle;
        getDriver().switchTo().window(lastWinHandle);
    }

    public void switchToOldWindow() {
        getDriver().switchTo().window(old_win);
    }

    public void switchToWindowByLocator(String locatorType, String locator) throws Exception {
        //System.out.println("++"+windowTitle+"++");
        old_win = getDriver().getWindowHandle();
        boolean winFound = false;
        for (String winHandle : getDriver().getWindowHandles()) {
            element = getDriver().switchTo().window(winHandle).findElement(getObjectByType(locatorType, locator));
            if (element != null) {
                winFound = true;
                break;
            }
        }
        if (!winFound)
            throw new Exception("Window having" + locatorType + ":" + locator + " not found");
    }

    public void switchToWindowByTitle(String windowTitle) throws Exception {

//		Set<String> handles = driver.getWindowHandles();
//		String originalWindow = driver.getWindowHandle();
//
//		Iterator <String> iterator = handles.iterator();
//		while(iterator.hasNext()) {
//
//			String newWindow = iterator.next();
//
//			if(!originalWindow.equalsIgnoreCase(newWindow)){
//
//				driver.switchTo().window(newWindow);
//
//			}
        old_win = getDriver().getWindowHandle();
        boolean winFound = false;
        for (String winHandle : getDriver().getWindowHandles()) {
            String str = getDriver().switchTo().window(winHandle).getTitle();
            //System.out.println("**"+str+"**");
            if (str.equals(windowTitle)) {
                winFound = true;
                break;
            }
        }
        if (!winFound)
            throw new Exception("Window having title " + windowTitle + " not found");
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
}
