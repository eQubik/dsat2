package com.equbik.framework.executions;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public interface Execution {

    /*
     * Execution interface should be implemented by those classes that are being used as an Execution.
     * Means that you should provide implementation according to the config that was provided by Executor.
     * For instance, SeleniumBrowser class is an Execution class, it creates a WebDriver according to the
     * config, that was provided by Selenium class. Selenium class is an Executor.
     */

}
