package library.selenium.driver.managers;

import library.selenium.driver.factory.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SaucelabsDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    protected void setDriver() {

    }

    @Override
    public void updateResults(String result) {

    }
}
