package library.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;

public class Property {

    protected static Logger logger = LogManager.getLogger(Property.class);

    private Property() {

    }

    public static PropertiesConfiguration getProperties(String propsFilePath) {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        try {
            File propsFile = new File(propsFilePath);
            if (propsFile.exists()) {
                FileInputStream inputStream = new FileInputStream(propsFilePath);
                propertiesConfiguration.load(inputStream);
                inputStream.close();
                return propertiesConfiguration;
            }

        } catch (Exception e) {
            logger.debug(e.getMessage());
            return null;
        }
        return null;
    }

    public static void setProperty(String propFilePath, String key, String value) {
        PropertiesConfiguration propertiesConfiguration = getProperties(propFilePath);
        if (propertiesConfiguration != null) {
            propertiesConfiguration.setProperty(key, value);
            try {
                propertiesConfiguration.save(propFilePath);
            } catch (ConfigurationException e) {
                logger.warn("unable to set property of '{}'. {} ", key, e.getMessage());
            }
        }

    }

    public static String getProperty(String propFilePath, String key) {
        PropertiesConfiguration propertiesConfiguration = getProperties(propFilePath);
        String keyValue = null;
        if (propertiesConfiguration != null) {
            keyValue = propertiesConfiguration.getString(key);

        }
        return keyValue;
    }

    public static String[] getPropertyArray(String propFilePath, String key) {
        PropertiesConfiguration propertiesConfiguration = getProperties(propFilePath);
        String[] keyValue = null;
        if (propertiesConfiguration != null) {
            keyValue = propertiesConfiguration.getStringArray(key);

        }
        return keyValue;
    }

    public static String getVariable(String propName) {
        String val = System.getProperty(propName, null);
        val = (val == null ? System.getenv(propName) : val);
        return val;
    }

    public static void setRuntimeProperties() {
        PropertiesConfiguration props = Property.getProperties(Constants.RUNTIME_PROP_FILE);
        String environment = props.getString("environment");
        String seleniumServer = props.getString("seleniumServer");
        String browserName = props.getString("browserName");
        String defaultWait = props.getString("defaultWait");
        String sendEmail = props.getString("sendEmail");
        String emailSender = props.getString("emailSender");
        String emailRecipient = props.getString("emailRecipient");
        String emailSubject = props.getString("emailSubject");

        if (environment != null) {
            System.setProperty("fw.environment", environment);
        }

        if (seleniumServer != null) {
            System.setProperty("fw.seleniumServer", seleniumServer);
        }

        if (browserName != null) {
            System.setProperty("fw.browserName", browserName);
        }

        if (defaultWait != null) {
            System.setProperty("fw.defaultWait", defaultWait);
        }

        if (sendEmail != null && sendEmail.equalsIgnoreCase("true")) {

            System.setProperty("fw.sendEmail", sendEmail);

            if (emailSender != null) {
                System.setProperty("fw.emailSender", emailSender);
            }

            if (emailRecipient != null) {
                System.setProperty("fw.emailRecipient", emailRecipient);
            }

            if (emailSubject != null) {
                System.setProperty("fw.emailSubject", emailSubject);
            }
        }


    }

}
