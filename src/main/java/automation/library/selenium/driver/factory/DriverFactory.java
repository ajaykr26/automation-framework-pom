package automation.library.selenium.driver.factory;

import automation.library.common.TestContext;
import automation.library.selenium.driver.managers.ChromeDriverManager;
import automation.library.selenium.driver.managers.FirefoxDriverManager;
import automation.library.selenium.driver.managers.IEDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverFactory {

    private static DriverFactory instance = new DriverFactory();
    protected Logger logger = LogManager.getLogger(this.getClass().getName());
    protected TestContext context = TestContext.getInstance();
    ThreadLocal<DriverManager> driverManager = new ThreadLocal<DriverManager>() {
        protected DriverManager initialValue() {
            return setDriverManager();
        }
    };

    protected DriverFactory() {
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

    public WebDriverWait getWait() {
        return driverManager.get().getWait();
    }

    public DriverManager setDriverManager() {
        String browser = DriverContext.getInstance().getBrowserName();

        switch (browser) {
            case "CHROME":
                driverManager.set(new ChromeDriverManager());
                break;
            case "FIREFOX":
                driverManager.set(new FirefoxDriverManager());
                break;
            case "IE":
                driverManager.set(new IEDriverManager());
                break;

        }
        return driverManager.get();
    }

    public void quit() {
        driverManager.get().quitDriver();
        driverManager.remove();
    }


}


