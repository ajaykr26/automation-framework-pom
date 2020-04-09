package automation.library.selenium.driver.factory;

import automation.library.common.Constants;
import automation.library.common.Property;
import org.openqa.selenium.WebDriver;
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
        final int defaultWit = 10;
        int duration;
        try {
            duration = Property.getProperties(Constants.SELENIUM_RUNTIME_PATH).getInt("defaultWait");
        } catch (Exception e) {
            duration = defaultWit;
        }
        return duration;
    }

    public String getDriverPath(String driver) {
        String drivername = driver + System.getProperty(("os.name").split(" ")[0].equalsIgnoreCase("windows") ? ".exe" : " ");
        String driverPath = Property.getVariable("cuke.driverPath");
        return (driverPath == null ? Constants.DRIVER_PATH + System.getProperty("os.name").split(" ")[0].toLowerCase() + "/" + drivername : driverPath);
    }

    protected abstract void setDriver();

    public abstract void updateResults(String result);
}


