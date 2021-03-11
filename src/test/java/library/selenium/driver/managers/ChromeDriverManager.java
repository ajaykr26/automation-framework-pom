package library.selenium.driver.managers;


import library.common.Constants;
import library.common.Property;
import library.selenium.driver.factory.DriverContext;
import library.selenium.driver.factory.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ChromeDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void setDriver(){
        PropertiesConfiguration propertiesConfiguration = Property.getProperties(Constants.RUNTIME_PROP_FILE);

        if(Property.getVariable("cukes.webdrivermanager") != null && Property.getVariable("cukes.webdrivermanager").equalsIgnoreCase("true")){
            if(Property.getVariable("cukes.chromedriver") != null){
                WebDriverManager.chromedriver().version(Property.getVariable("cukes.chromedriver")).setup();
            }else {
                WebDriverManager.chromedriver().setup();

            }
        }else {
            System.setProperty("webdriver.chrome.driver", getDriverPath("chromedriver"));
        }
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeOptions chromeOptions = new ChromeOptions();

        for(String option: propertiesConfiguration.getStringArray("options." + DriverContext.getInstance().getBrowserName().replaceAll("\\s", ""))){
            chromeOptions.addArguments(option);
        }

        if (propertiesConfiguration.getString("options.chrome.useAutomationExtension") != null &&
                propertiesConfiguration.getString("options.chrome.useAutomationExtension").equalsIgnoreCase("false")) {
                chromeOptions.setExperimentalOption("options.chrome.useAutomationExtension", false);

        }

        if(DriverContext.getInstance().getBrowserName().contains("kiosk")){
            chromeOptions.addArguments("--kiosk");
        }
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @Override
    public void updateResults(String result) {

    }
}


