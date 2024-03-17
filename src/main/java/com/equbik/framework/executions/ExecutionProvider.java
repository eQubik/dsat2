package com.equbik.framework.executions;

import com.equbik.framework.executors.Executor;
import com.equbik.framework.services.Executions;

import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ExecutionProvider {

    /*
     * ExecutionProvider class is used to provide the Execution according to the mentioned Executor
     */

    private static final Logger logger = Logger.getLogger(ExecutionProvider.class.getName());
    private final Execution execution;

    public ExecutionProvider(Executor executor){
        this.execution = provideExecution(executor);
    }

    private Execution provideExecution(Executor executor){
        if(executor.getClass().getSimpleName().equals(Executions.Selenium.toString())){
            return new SeleniumBrowser(executor);
        } else if (executor.getClass().getSimpleName().equals(Executions.RestAssured.toString())){//STATIC VARIABLE
            return new RestAssuredSetup(executor);
        } else {
            logger.warning("Execution for mentioned Executor is not available. Execution is being Skipped.");
            throw new RuntimeException("Execution for mentioned Executor is not available");
        }
    }

    public Execution getExecution() {
        return execution;
    }

}
