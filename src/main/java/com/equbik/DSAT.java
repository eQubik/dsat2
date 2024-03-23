package com.equbik;

import com.equbik.framework.executors.Executor;
import com.equbik.framework.executors.ExecutorProvider;
import com.equbik.framework.models.artifact_model.Results;
import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.models.json_model.Scenario;
import com.equbik.framework.perform.ScenarioActionPerform;
import com.equbik.framework.services.JSONParser;
import com.equbik.framework.services.StaticVariables;

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

    //TODO
    // Multiple scenarios, remote execution, name each scenario inside a single execution(to share the state)

    private static final Logger logger = Logger.getLogger(DSAT.class.getName());
    private final Scenario scenario;
    private final Executor executor;
    private final boolean print;

    public DSAT(String scenarioPath, boolean print) {
        //Initializing scenario
        this.scenario = JSONParser.parseScenario(scenarioPath);
        Environment.Executor executorConfig = scenario.getEnvironment().getExecutor();
        Environment.Adapter adapter = scenario.getEnvironment().getAdapter();
        //Checking if executor and adapter are configured
        if (!isExecutorAndAdapterProvided(executorConfig, adapter))
            throw new RuntimeException("Executor and adapter are not provided, or not valid.");
        //Creating an instance of the Executor
        String executorIgnoreCase = StaticVariables.executors(executorConfig.getType());
        ExecutorProvider executorProvider = new ExecutorProvider(executorIgnoreCase, executorConfig);
        this.executor = executorProvider.getExecutor();
        this.print = print;
    }

    private boolean isExecutorAndAdapterProvided(Environment.Executor executorConfig, Environment.Adapter adapter) {
        return executorConfig.getType() != null && adapter != null;
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

}
