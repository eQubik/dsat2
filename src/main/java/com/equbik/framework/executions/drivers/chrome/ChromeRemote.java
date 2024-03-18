package com.equbik.framework.executions.drivers.chrome;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ChromeRemote {

    /*
     * ChromeRemote class is used to initialize the remote(selenium hub for example) chrome driver
     */

    private static final Logger logger = Logger.getLogger(ChromeRemote.class.getName());
    public static ThreadLocal<WebDriver> remoteChromeThreadLocal = new ThreadLocal<>();

    public static void initializeDriver(String hostName, String hostPort, List<String> args){
        ChromeOptions chromeOptions = new ChromeOptions();
        if (!args.isEmpty()) chromeOptions.addArguments(args);
        try {
            remoteChromeThreadLocal.set(new RemoteWebDriver(new URL(hostName + hostPort), chromeOptions));
        } catch (MalformedURLException e) {
            logger.warning("Failed to connect to remote driver. Execution is being skipped.");
            throw new RuntimeException("Failed to connect to remote driver");
        }
    }

    public static WebDriver getDriver(){
        return remoteChromeThreadLocal.get();
    }

}
