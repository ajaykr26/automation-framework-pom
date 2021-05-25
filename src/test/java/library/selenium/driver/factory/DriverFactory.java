package library.selenium.driver.factory;

import library.common.TestContext;
import library.selenium.driver.factory.DriverContext;
import library.selenium.driver.factory.DriverManager;
import library.selenium.driver.managers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverFactory {

    private static DriverFactory instance = new DriverFactory();
    protected Logger logger = LogManager.getLogger(this.getClass().getName());
    protected TestContext context = TestContext.getInstance();
    private ThreadLocal<DriverManager> driverManager = new ThreadLocal<DriverManager>() {
        protected DriverManager initialValue() {
            return setDriverManager();
        }
    };

    private DriverFactory() {
    }

    public static DriverFactory getInstance() {
        return instance;
    }

    public DriverManager driverManager() {
        return driverManager.get();
    }

    public WebDriver getDriver() {
        return driverManager.get().getDriver();
    }

    public WebDriver getDriver(String browser) {
        return driverManager.get().getDriver(browser);
    }

    public WebDriverWait getWait() {
        return driverManager.get().getWait();
    }

    private DriverManager setDriverManager() {
        Server server = Server.valueOf(DriverContext.getInstance().getTechStack().get("seleniumServer").toLowerCase());
        Browser browser = Browser.valueOf(DriverContext.getInstance().getBrowserName().toLowerCase());
        switch (server) {
            case remote_htmlunit:
                driverManager.set(new HtmlUnitDriverManager());
                break;
            case remote_phantomjs:
                driverManager.set(new PantomJSDriverManager());
                break;
            case local:
                switch (browser) {
                    case chrome:
                        driverManager.set(new ChromeDriverManager());
                        break;
                    case firefox:
                        driverManager.set(new FirefoxDriverManager());
                        break;
                    case iexplorer:
                        driverManager.set(new IEDriverManager());
                        break;
                    case edge:
                        driverManager.set(new EdgeDriverManager());
                        break;
                }
        }

        return driverManager.get();
    }

    public void quit() {
        driverManager.get().quitDriver();
        driverManager.remove();
    }

    public enum Server {
        local, grid, saucelabs, appium, browserstack, remote_htmlunit, remote_phantomjs;
    }

    public enum Browser {
        chrome, iexplorer, edge, safari, firefox;
    }


}


