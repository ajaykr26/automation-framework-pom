package library.selenium.common;

import library.selenium.BasePO;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConfigMethods extends BasePO {
    protected WebDriver driver;

    public ConfigMethods() {
        driver = getDriver();
    }

    public void printDesktopConfiguration() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        Calendar cal = Calendar.getInstance();

        System.out.println("Following are machine configurations : \n");
        System.out.println("Date (MM/DD/YYYY) and Time (HH:MM:SS) : " + dateFormat.format(cal.getTime()));

        Capabilities cap = (Capabilities) ((RemoteWebDriver) driver).getCapabilities();
        System.out.println("Browser : " + cap.getBrowserName());
        System.out.println("Platform : " + cap.getPlatform());
    }
}
