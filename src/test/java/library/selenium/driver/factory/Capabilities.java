package library.selenium.driver.factory;


import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Capabilities {

    private DesiredCapabilities desiredCapabilities;

    public Capabilities() {
        Map<String, String> map = DriverContext.getInstance().getTechStack();

        desiredCapabilities = new DesiredCapabilities();

        if (!map.get("seleniumServer").equalsIgnoreCase("grid")) {
            desiredCapabilities.setCapability("name", TestContext.getInstance().testdataGet("fw.testDescription"));
        }
        if (TestContext.getInstance().testdata().containsKey("fw.projectName")) {
            desiredCapabilities.setCapability("project", TestContext.getInstance().testdataGet("fw.projectName"));
        }
        if (TestContext.getInstance().testdata().containsKey("fw.buildNumber")) {
            desiredCapabilities.setCapability("build", TestContext.getInstance().testdataGet("fw.buildNumber"));
        }
        if (TestContext.getInstance().testdata().containsKey("fw.appPackage")) {
            desiredCapabilities.setCapability("appPackage", TestContext.getInstance().testdataGet("fw.appPackage"));
        }
        if (TestContext.getInstance().testdata().containsKey("fw.appActivity")) {
            desiredCapabilities.setCapability("appActivity", TestContext.getInstance().testdataGet("fw.appActivity"));
        }
        for (Map.Entry<String, String> pair : map.entrySet()) {
            if (!pair.getKey().equalsIgnoreCase("seleniumServer") && !pair.getKey().equalsIgnoreCase("description"))
                if (pair.getValue().equals("iexplorer")) {
                    desiredCapabilities.setCapability(pair.getKey(), "internet explorer");
                } else {
                    desiredCapabilities.setCapability(pair.getKey(), pair.getValue());

                }
        }

        PropertiesConfiguration props = Property.getProperties(Constants.RUNTIME_PROP_FILE);
        List<String> desiredCapsList = Arrays.asList(props.getStringArray("desiredCapabilities" + DriverContext.getInstance().getBrowserName().replaceAll("\\s", "")));

        desiredCapsList.forEach(desiredCap -> {
            String[] par = desiredCap.split("==");
            if (par[1].trim().equalsIgnoreCase("true") || par[1].trim().equalsIgnoreCase("false"))
                desiredCapabilities.setCapability(par[0].trim(), Boolean.parseBoolean(par[1].trim()));
            else
                desiredCapabilities.setCapability(par[0].trim(), par[1].trim());
        });

        if (Boolean.parseBoolean(props.getString("useProxy"))) {
            Proxy proxy = new Proxy();
            if (props.getString("httpProxyURL") != null && props.getString("sslProxyURL") != null) {
                proxy.setHttpProxy(props.getString("httpProxyURL")).setSslProxy("sslProxyURL");
                desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
            } else {
                throw new IllegalArgumentException("seProxy set to true, but the value not provided for httpProxyURL or sslProxyURL in runtime.properties file");
            }
        }
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }

}




