package com.equbik.framework.executions.drivers.chrome;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ChromeLocal {

    /*
     * ChromeLocal class is used to initialize the local(on your machine for example) chrome driver
     */

    public static ThreadLocal<ChromeDriver> localChromeThreadLocal = new ThreadLocal<>();

    public static void initializeDriver(String driver, String chromium, List<String> args){
        System.setProperty("webdriver.chrome.driver", driver);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary(chromium);
        if (!args.isEmpty()) chromeOptions.addArguments(args);
        localChromeThreadLocal.set(new ChromeDriver(chromeOptions));
    }

    public static ChromeDriver getDriver(){
        return localChromeThreadLocal.get();
    }

}
