package library.common;

public class Constants {

    public static final String ATTRIBUTE_FOR_TABLE_CELL_COMPARE = "fw.attributeForTableCellCompare";
    public static final String ATTRIBUTE_FOR_TABLE_HEADER_COMPARE = "fw.attributeForTableHeaderCompare";
    public static final String BASE_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String TESTDATA_PATH = BASE_PATH + "testdata/";
    public static final String TESTSUITE_PATH = BASE_PATH + "testsuite/";
    public static final String DRIVER_PATH = BASE_PATH + "drivers/";
    public static final String ENVIRONMENT_PATH = BASE_PATH + "config/environments/";
    public static final String SCREENSHOT_PATH = BASE_PATH + "screenshots/";
    public static final String ACTUAL_IMAGE_PATH = USER_DIR + "/target/actual-image/";
    public static final String EXPECTED_IMAGE_PATH = USER_DIR + "/expected-image/";
    public static final String TESTCASE_XLSX_FILE = BASE_PATH + "/src/test/resources/scripts/testcase.xlsx";
    public static final String RUNTIME_PROP_FILE = BASE_PATH + "config/properties/" + "runtime.properties";
    public static final String KEYBOARD_JSON_FILE = BASE_PATH + "config/keyboard/" + "keyboard.json";
    public static final String ENVIRONMENT_PROP_FILE = BASE_PATH + "config/environments/" + Property.getVariable("environment") + ".properties";
    public static final String ENVIRONMENT_SECURE_PROP_FILE = BASE_PATH + "config/environments/SecureText-" + Property.getVariable("environment") + ".properties";
    public static final String JSON_STACKS_FILE = BASE_PATH + "config/techstacks/" + Property.getVariable("techstack") + ".json";
    public static final String TECHSTACKS_PATH = BASE_PATH + "config/techstacks/";

    public static String getCurrentEnvFilePath() {
        String currentEnv = Property.getVariable("environment") != null ? Property.getVariable("environment") :
                Property.getProperty(RUNTIME_PROP_FILE, "environment").toUpperCase();
        return BASE_PATH + "config/environments/" + currentEnv + ".properties";

    }
}
