package library.selenium.driver.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DriverContext {

    private static List<DriverContext> threads = new ArrayList<>();
    private Map<String, String> techstack = null;
    private long currentEnvThreadId;
    private boolean keepBrowserOpen = false;

    private DriverContext() {
    }

    private DriverContext(long currentThreadId) {
        this.currentEnvThreadId = currentThreadId;
    }

    public static synchronized DriverContext getInstance() {
        long currentThreadId = Thread.currentThread().getId();
        for (DriverContext thread : threads) {
            if (thread.currentEnvThreadId == currentThreadId) {
                return thread;
            }
        }
        DriverContext temp = new DriverContext(currentThreadId);
        threads.add(temp);
        return temp;
    }


    public void setDriverContext(Map<String, String> techstack) {
        setTechStack(techstack);
    }

    public Map<String, String> getTechStack() {
        return this.techstack;
    }

    public void setTechStack(Map<String, String> techstack) {
        this.techstack = techstack;
    }

    public String getBrowserName() {
        if (techstack == null) {
            return null;
        } else {
            return this.techstack.get("browserName") == null ? this.techstack.get("browser") : this.techstack.get("browserName");
        }
    }

    public String getBrowserVersion() {
        if (techstack == null) {
            return null;
        } else {
            return this.techstack.get("version") == null ? this.techstack.get("browser_version") : this.techstack.get("version");
        }
    }

    public String getPlatform() {
        if (techstack == null) {
            return null;
        } else {
            return this.techstack.get("platform") == null ? this.techstack.get("os") + "_" + this.techstack.get("os_version") : this.techstack.get("platform");
        }
    }

    public Boolean getKeepBrowserOpen() {
        return this.keepBrowserOpen;
    }

    public void setKeepBrowserOpen(Boolean keepBrowserOpen) {
        this.keepBrowserOpen = keepBrowserOpen;
    }
}


