package automation.library.common;

import automation.library.selenium.BaseTest;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;

import java.io.File;
import java.util.*;

public class TestContext {

    private static List<TestContext> threads = new ArrayList<>();
    protected Logger logger = LogManager.getLogger(this.getClass().getName());
    private SoftAssertions softAssert = null;
    private Map<String, Object> testdata = null;
    private long threadToEnvID;
    private Set<Class<?>> setOfPageObjects = null;
    private Set<File> setOfFeatureFiles = null;
    private Deque<String> windowHandles;
    private String activeWindowType;
    private Set<String> setOfAPI = null;

    private TestContext(long threadID) {
        this.threadToEnvID = threadID;
    }

    public static synchronized TestContext getInstance() {
        long currentRunningThreadID = Thread.currentThread().getId();
        for (TestContext thread : threads) {
            if (thread.threadToEnvID == currentRunningThreadID) {
                return thread;
            }
        }
        TestContext temp = new TestContext(currentRunningThreadID);
        threads.add(temp);
        return temp;
    }

    public Map<String, Object> testdata() {
        if (testdata == null)
            testdata = new HashMap<>();
        return testdata;
    }

    public void testdataPut(String key, Object value) {
        testdata.put(key, value);
    }

    public Object testdataGet(String key) {
        if (testdata.get(key) != null) {
            return testdata.get(key);
        } else if (testdata.get(key.toLowerCase()) != null) {
            logger.warn("exact key not found for the key '{}' please check the key name", key);
            return testdata.get(key.toLowerCase());
        }
        return testdata.get(key);
    }

    public SoftAssertions softAssertions() {
        if (softAssert == null)
            softAssert = new SoftAssertions();
        return softAssert;
    }

    public void resetSoftAssert() {
        softAssert = new SoftAssertions();
    }

    public Set<Class<?>> getSetOfPageObjects() {
        if (setOfPageObjects == null)
            setOfPageObjects = new HashSet<>();
        return setOfPageObjects;
    }

    public Set<Class<?>> setSetOfPageObjects() {
        if (setOfPageObjects == null)
            setOfPageObjects = new HashSet<>();
        return setOfPageObjects;
    }

    public Set<File> setOfFeatureFiles() {
        if (setOfFeatureFiles == null)
            setOfFeatureFiles = new HashSet<>();
        return setOfFeatureFiles;
    }
}
