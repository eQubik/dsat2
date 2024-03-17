package com.equbik;

import com.equbik.framework.executors.Executor;
import com.equbik.framework.models.artifact_model.Results;
import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.models.json_model.Scenario;
import com.equbik.framework.perform.ScenarioActionPerform;
import com.equbik.framework.services.JSONParser;
import com.equbik.framework.services.StaticVariables;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class DSAT {

    /*
     * DSAT class is the main class of the framework. To start the app just provide the valid scenario.
     * Feel free to improve\delete\add anything
     */

    private static final Logger logger = Logger.getLogger(DSAT.class.getName());
    private final Scenario scenario;
    private final Executor executor;
    private final boolean print;

    public DSAT(String scenarioPath, boolean print) {
        //Initializing scenario
        this.scenario = JSONParser.parseScenario(scenarioPath);
        Environment.Executor executorConfig = scenario.getEnvironment().getExecutor();
        Environment.Adapter adapter = scenario.getEnvironment().getAdapter();
        String executorIgnoreCase = StaticVariables.executors(executorConfig.getType());
        //Checking if executor and adapter are configured
        if (!isExecutorAndAdapterProvided(executorConfig, adapter))
            throw new RuntimeException("Executor or adapter is not provided, or not valid.");
        //Creating an instance of the Executor
        Class<?> executorClass = getExecutorClass(executorIgnoreCase);
        Constructor<?> executorConstructor = getExecutorConstructor(executorClass);
        Object executorObject = getExecutor(executorConstructor, executorConfig);
        this.executor = (Executor) executorObject;
        this.print = print;
    }

    public void performScenario() {
        //Providing ScenarioActionPerform class with the proper config
        ScenarioActionPerform scenarioActions = new ScenarioActionPerform(scenario, executor);
        logger.fine("Scenario: " + scenarioActions.getScenario() + " is getting started");
        scenarioActions.startScenario();
        if (print) printResults(scenarioActions); //Will be replaced soon
    }

    public void printResults(ScenarioActionPerform scenario) {
        for (Map.Entry<String, List<Results>> entry : scenario.getResultsMap().entrySet()) {
            String stepName = entry.getKey();
            List<Results> resultsList = entry.getValue();
            System.out.println(stepName + ": ");
            for (Results result : resultsList) {
                System.out.println(result);
            }
        }
    }

    private boolean isExecutorAndAdapterProvided(Environment.Executor executorConfig, Environment.Adapter adapter) {
        return executorConfig.getType() != null && adapter != null;
    }

    private Class<?> getExecutorClass(String executor) {
        try {
            return Class.forName("com.equbik.framework.executors." + executor);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            logger.warning("Executor class not found. Execution is being skipped.");
            throw new RuntimeException("Executor class not found");
        }
    }

    private Constructor<?> getExecutorConstructor(Class<?> executorClass) {
        if (Executor.class.isAssignableFrom(executorClass)) {
            try {
                return executorClass.getDeclaredConstructor(Environment.Executor.class);
            } catch (NoSuchMethodException e) {
                logger.warning("Error creating executor's constructor. Execution is being skipped.");
                throw new RuntimeException("Error creating executor's constructor");
            }
        } else {
            logger.warning("Wrong executor type. Execution is being skipped.");
            throw new RuntimeException("Wrong executor type");
        }
    }

    private Object getExecutor(Constructor<?> executorConstructor, Environment.Executor executorConfig) {
        try {
            return executorConstructor.newInstance(executorConfig);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.warning("Error during executor Object creation. Execution is being skipped.");
            e.printStackTrace();
            throw new RuntimeException("Error during executor Object creation: " + e.getMessage());
        }
    }

}
