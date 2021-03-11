package library.selenium.driver.factory;

import library.common.Constants;
import library.common.Property;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class DriverManager {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected DriverManager() {
    }

    public WebDriver getDriver() {
        if (driver == null) {
            setDriver();
        }
        return driver;
    }

    public WebDriver getDriver(String browser) {
        if (driver == null) {
            setDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, getWaitDuration());
        }
        return wait;
    }

    public int getWaitDuration() {
        final int defaultWait = 30;
        int duration;
        try {
            duration = Property.getProperties(Constants.RUNTIME_PROP_FILE).getInt("defaultWait");
        } catch (Exception e) {
            duration = defaultWait;
        }
        return duration;
    }

    public String getDriverPath(String driver) {
        String extention = System.getProperty("os.name").split(" ")[0].toLowerCase().equalsIgnoreCase("windows") ? ".exe" : " ";
        String drivername = driver + extention;
        String driverPath = Property.getVariable("cuke.driverPath");
        return (driverPath == null ? Constants.DRIVER_PATH + System.getProperty("os.name").split(" ")[0].toLowerCase() + "/" + drivername : driverPath);
    }

    protected abstract void setDriver();

    public abstract void updateResults(String result);
}


