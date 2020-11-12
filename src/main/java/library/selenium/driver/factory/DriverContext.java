package library.selenium.driver.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DriverContext {

    private static List<DriverContext> threads = new ArrayList<>();
    private Map<String, String> techStack = null;
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


    public void setDriverContext(Map<String, String> techStack) {
        setTechStack(techStack);
    }

    public Map<String, String> getTechStack() {
        return this.techStack;
    }

    public void setTechStack(Map<String, String> techStack) {
        this.techStack = techStack;
    }

    public String getBrowserName() {
        if (techStack == null) {
            return null;
        } else {
            return this.techStack.get("browserName") == null ? this.techStack.get("browser") : this.techStack.get("browserName");
        }
    }

    public String getBrowserVersion() {
        if (techStack == null) {
            return null;
        } else {
            return this.techStack.get("version") == null ? this.techStack.get("browser_version") : this.techStack.get("version");
        }
    }

    public String getPlatform() {
        if (techStack == null) {
            return null;
        } else {
            return this.techStack.get("platform") == null ? this.techStack.get("os") + "_" + this.techStack.get("os_version") : this.techStack.get("platform");
        }
    }

    public Boolean getKeepBrowserOpen() {
        return this.keepBrowserOpen;
    }

    public void setKeepBrowserOpen(Boolean keepBrowserOpen) {
        this.keepBrowserOpen = keepBrowserOpen;
    }
}


