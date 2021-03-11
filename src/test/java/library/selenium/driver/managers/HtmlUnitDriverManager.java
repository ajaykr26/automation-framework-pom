package library.selenium.driver.managers;

import library.selenium.driver.factory.Capabilities;
import library.selenium.driver.factory.DriverContext;
import library.selenium.driver.factory.DriverManager;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class HtmlUnitDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());


    @Override
    public void setDriver() {
        Browser browser = Browser.valueOf(DriverContext.getInstance().getBrowserName().toLowerCase());
        switch (browser) {
            case chrome:
                driver = new HtmlUnitDriver(BrowserVersion.CHROME);
            case firefox:
                driver = new HtmlUnitDriver(BrowserVersion.FIREFOX);
            case iexplorer:
                driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER);
        }

    }

    @Override
    public void updateResults(String result) {

    }

    public enum Browser {
        chrome, iexplorer, firefox;
    }
}
