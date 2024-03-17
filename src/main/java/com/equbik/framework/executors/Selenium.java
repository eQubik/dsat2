package com.equbik.framework.executors;

import com.equbik.framework.models.json_model.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class Selenium implements Executor {

    /*
     * Selenium class is used to provide the chosen Execution with the proper settings
     */

    private static final Logger logger = Logger.getLogger(Selenium.class.getName());
    private final boolean remote;
    private final String resourceUrl;
    private final String waitSec;
    private final String hostName;
    private final String hostPort;
    private final String driverPath;
    private final boolean isChrome;
    private final String chromiumPath;
    private final boolean quitDriver;
    private final List<String> args;

    public Selenium(Environment.Executor executor){
        this.remote = executor.isRemote();
        this.resourceUrl = executor.getResourceUrl();
        this.waitSec = executor.getWait();
        this.hostName = executor.getHostName();
        this.hostPort = executor.getHostPort();
        this.driverPath = executor.getDriver();
        this.isChrome = isChrome();
        this.chromiumPath = executor.getChromium();
        this.quitDriver = executor.isQuitDriver();
        this.args = nullValueList(executor.getArgs());
    }

    private List<String> nullValueList(List<String> list){
        return list != null ? list : new ArrayList<>();
    }

    private boolean isChrome(){
        if(!remote){
            try {
                return driverPath.contains("chrome");
            } catch (NullPointerException e){
                return false;
            }
        } else {
            return false;
        }
    }

    public String getResourceUrl(){
        if (resourceUrl != null){
            return resourceUrl;
        } else {
            logger.warning("Resource url can't be a null value. Execution is being skipped.");
            throw new RuntimeException("Resource url can't be a null value");
        }
    }

    public Long getWaitSec() {
        if (waitSec != null){
            try {
                return Long.parseLong(waitSec);
            } catch (NumberFormatException e){
                logger.warning("Wait should be a number value. Execution is being skipped.");
                throw new RuntimeException("Wait should be a number value");
            }
        } else {
            logger.warning("Wait can't be a null value. Execution is being skipped.");
            throw new RuntimeException("Wait can't be a null value");
        }
    }

    public String getDriverPath() {
        if(!remote){
            if (driverPath != null){
                return driverPath;
            } else {
                logger.warning("Driver path can't be a null value when remote set false. Execution is being skipped.");
                throw new RuntimeException("Driver path can't be a null value when remote set false");
            }
        } else {
            return null;
        }
    }

    public String getChromiumPath() {
        if(!remote && isChrome){
            if (chromiumPath != null){
                return chromiumPath;
            } else {
                logger.warning("Chromium path can't be a null value when driver is chrome. Execution is being skipped.");
                throw new RuntimeException("Chromium path can't be a null value when driver is chrome");
            }
        } else {
            return null;
        }
    }

    public String getHostName() {
        if(remote){
            if (hostName != null){
                return hostName;
            } else {
                logger.warning("Hub's hostname can't be a null value when remote set true. Execution is being skipped.");
                throw new RuntimeException("Hub's hostname can't be a null value when remote set true");
            }
        } else {
            return null;
        }
    }

    public String getHostPort() {
        if(remote){
            if (hostPort != null){
                return hostPort;
            } else {
                logger.warning("Hub's port can't be a null value when remote set true. Execution is being skipped.");
                throw new RuntimeException("Hub's port can't be a null value when remote set true");
            }
        } else {
            return null;
        }
    }

    public boolean isQuitDriver() {
        return quitDriver;
    }

    public boolean isRemote() {
        return remote;
    }

    public List<String> getArgs() {
        return args;
    }

}
