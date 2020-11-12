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


public class FirefoxDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    protected void setDriver() {
        Capabilities capabilities = new Capabilities();
        PropertiesConfiguration propertiesConfiguration = Property.getProperties(Constants.RUNTIME_PROP_FILE);

        if (Property.getVariable("cukes.webdrivermanager") != null && Property.getVariable("cukes.webdrivermanager").equalsIgnoreCase("true")) {
            if (Property.getVariable("cukes.firefoxDriver") != null) {
                WebDriverManager.firefoxdriver().version(Property.getVariable("cukes.firefoxDriver")).setup();
            } else {
                WebDriverManager.firefoxdriver().setup();

            }
        } else {
            System.setProperty("webdriver.gecko.driver", getDriverPath("geckodriver"));
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        for (String option : propertiesConfiguration.getStringArray("options." + DriverContext.getInstance().getBrowserName().replaceAll("\\s", ""))) {
            firefoxOptions.addArguments(option);
        }
        firefoxOptions.merge(capabilities.getDesiredCapabilities());
        driver = new FirefoxDriver(firefoxOptions);
    }

    @Override
    public void updateResults(String result) {

    }
}
