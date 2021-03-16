package library.common;

public class Constants {

    public static final String ATTRIBUTE_FOR_TABLE_CELL_COMPARE = "fw.attributeForTableCellCompare";
    public static final String ATTRIBUTE_FOR_TABLE_HEADER_COMPARE = "fw.attributeForTableHeaderCompare";
    public static final String XPATH_FOR_LABELS = "fw.xPathForLabels";
    public static final String XPATH_FOR_LABELS_DELIMETER = "--XPATH--";
    public static final String BASE_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String TESTDATA_PATH = BASE_PATH + "testdata/";
    public static final String TESTSUITE_PATH = BASE_PATH + "testsuite/";
    public static final String DRIVER_PATH = BASE_PATH + "drivers/";
    public static final String ENVIRONMENT_PATH = BASE_PATH + "config/environment/";
    public static final String SCREENSHOT_PATH = BASE_PATH + "screenshots/";
    public static final String ACTUAL_IMAGE_PATH = USER_DIR + "/target/actual-image/";
    public static final String EXPECTED_IMAGE_PATH = USER_DIR + "/expected-image/";
    public static final String TESTCASE_XLSX_FILE = BASE_PATH + "/src/test/resources/scripts/testcase.xlsx";
    public static final String RUNTIME_PROP_FILE = BASE_PATH + "config/selenium/properties/" + "runtime.properties";
    public static final String KEYBOARD_JSON_FILE = BASE_PATH + "config/keyboard/" + "keyboard.json";
    public static final String ENVIRONMENT_PROP_FILE = BASE_PATH + "config/environment/" + Property.getVariable("environment") + ".properties";
    public static final String ENVIRONMENT_SECURE_PROP_FILE = BASE_PATH + "config/environment/SecureText-" + Property.getVariable("environment") + ".properties";
    public static final String JSON_STACKS_FILE = BASE_PATH + "config/selenium/techstacks/" + Property.getVariable("techStack") + ".json";
    public static final String TECKSTACKS_PATH = BASE_PATH + "config/selenium/techstacks/";

    public static String getCurrentEnvFilePath() {
        String currentEnv = Property.getVariable("environment") != null ? Property.getVariable("environment") :
                Property.getProperty(RUNTIME_PROP_FILE, "environment").toUpperCase();
        return BASE_PATH + "config/environment/" + currentEnv + ".properties";

    }
}
