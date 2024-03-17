package com.equbik.framework.executions;

import com.equbik.framework.executors.Executor;
import com.equbik.framework.executors.Selenium;
import com.equbik.framework.executors.drivers.chrome.ChromeLocal;
import com.equbik.framework.executors.drivers.chrome.ChromeRemote;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class SeleniumBrowser implements Execution {

    /*
     * SeleniumBrowser class is used to initialize selected browser with all the necessary settings
     */

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final boolean isQuit;

    public SeleniumBrowser(Executor executor){
        Selenium selenium = (Selenium) executor;
        this.isQuit = selenium.isQuitDriver();
        this.driver = assignDriver(
                selenium.isRemote(),
                selenium.getDriverPath(),
                selenium.getChromiumPath(),
                selenium.getHostName(),
                selenium.getHostPort(),
                selenium.getArgs());
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(selenium.getWaitSec()));
        driver.get(selenium.getResourceUrl());
        driver.manage().window().maximize();
    }

    private WebDriver assignDriver(boolean isRemote, String driverPath, String chromiumPath, String hostName, String hostPort, List<String> args){
        if(isRemote){
            return remote(hostName, hostPort, args);
        } else {
            return local(driverPath, chromiumPath, args);
        }
    }

    public WebDriver local(String driverPath, String chromiumPath, List<String> args){
        ChromeLocal.initializeDriver(driverPath, chromiumPath, args);
        return ChromeLocal.getDriver();
    }

    public WebDriver remote(String hostName, String hostPort, List<String> args){
        ChromeRemote.initializeDriver(hostName, hostPort, args);
        return ChromeRemote.getDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public boolean isQuit() {
        return isQuit;
    }

}
