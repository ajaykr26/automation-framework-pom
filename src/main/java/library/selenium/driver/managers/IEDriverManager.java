package library.selenium.driver.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import library.common.Constants;
import library.common.Property;
import library.selenium.driver.factory.Capabilities;
import library.selenium.driver.factory.DriverContext;
import library.selenium.driver.factory.DriverManager;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;


public class IEDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    protected void setDriver() {
        Capabilities capabilities = new Capabilities();
        PropertiesConfiguration propertiesConfiguration = Property.getProperties(Constants.RUNTIME_PROP_FILE);

        if (Property.getVariable("cukes.webdrivermanager") != null && Property.getVariable("cukes.webdrivermanager").equalsIgnoreCase("true")) {
            if (Property.getVariable("cukes.iexploreDriver") != null) {
                WebDriverManager.iedriver().version(Property.getVariable("cukes.iexploreDriver")).setup();
            } else {
                WebDriverManager.iedriver().setup();

            }
        } else {
            System.setProperty("webdriver.ie.driver", getDriverPath("IEDriverServer"));
        }
        System.setProperty("webdriver.ie.driver.silent", "true");
        System.setProperty("ie.ensureCleanSession", "true");

        driver = new InternetExplorerDriver(new InternetExplorerOptions().merge(capabilities.getDesiredCapabilities()));
    }

    @Override
    public void updateResults(String result) {

    }
}
