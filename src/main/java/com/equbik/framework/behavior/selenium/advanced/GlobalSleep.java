package com.equbik.framework.behavior.selenium.advanced;

import com.equbik.framework.models.input_models.Environment;

import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class GlobalSleep {

    /*
     * GlobalSleep class used to invoke Thread.sleep method before each action
     */

    private static final Logger logger = Logger.getLogger(GlobalSleep.class.getName());
    private Long sec;

    public GlobalSleep(Environment.Sleep sleep){
        this.sec = sleep.getSec();
        logger.info("Global sleep indicated: " + sec);
    }

    public GlobalSleep(){
        logger.info("Global sleep won't be used during execution");
    }

    public void threadSleep(){
        try {
            Thread.sleep(1000 * sec);
        } catch (Exception e) {
            //Just ignore the problem
        }
    }

}
