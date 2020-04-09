package automation.library.common;


public class Constants {

    public static final String ATTRIBUTE_FOR_TABLE_CELL_COMPARE = "fw.attributeForTableCellCompare";
    public static final String ATTRIBUTE_FOR_TABLE_HEADER_COMPARE = "fw.attributeForTableHeaderCompare";
    public static final String XPATH_FOR_LABELS = "fw.xPathForLabels";
    public static final String XPATH_FOR_LABELS_DELIMETER = "--XPATH--";
    public static final String BASE_PATH = System.getProperty("user.dir") + "/src/main/resources/";
    public static final String SELENIUM_RUNTIME_PATH = BASE_PATH + "config/selenium/properties/" + "runtime.properties";
    public static final String SELENIUM_STACKS_PATH = BASE_PATH + "config/selenium/techstacks/" + Property.getVariable("cukes.techStack")+".json";
    public static final String TESTDATA_PATH = BASE_PATH + "testdata/";
    public static final String TESTCASE_PATH = BASE_PATH + "/src/main/resources/scripts/testcase.xlsx"+ Property.getVariable("cukes.techStack")+".json";
    public static final String KEYBOARD_JSON = BASE_PATH + "config/keyboard/" + "keyboard.json";
    public static final String SECURETEXT_FILE_PATH = BASE_PATH + "config/environments/";
    public static final String ENVIRONMENT_PATH = BASE_PATH + "config/environments/";
    public static final String ENVIRONMENT_PROP_FILE = BASE_PATH + "config/environments/" + Property.getVariable("cukes.env")+".properties";
    public static final String DRIVER_PATH = BASE_PATH + "config/drivers/";
    public static final String SCREENSHOT_PATH = BASE_PATH + "screenshots/";

}
