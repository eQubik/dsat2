package com.equbik;

import com.equbik.framework.adapters.Adapter;
import com.equbik.framework.adapters.AdapterConfig;
import com.equbik.framework.adapters.AdapterProvider;
import com.equbik.framework.executors.Executor;
import com.equbik.framework.executors.exceptions.ExecutorException;
import com.equbik.framework.models.input_models.Environment;
import com.equbik.framework.models.input_models.Scenario;
import com.equbik.framework.models.output_models.ScenarioResult;
import com.equbik.framework.models.output_models.SuiteResult;
import com.equbik.framework.perform.ScenarioActionPerform;
import com.equbik.framework.perform.SuiteActionPerform;
import com.equbik.framework.services.JSONParser;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.dictionaries.Executions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class DSAT {

    /*
     * DSAT class is the main class of the framework. To start the app just provide the valid environment and scenario/scenarios.
     */

    //TODO
    // Remote execution, name each scenario inside a single suite(to control the flow)

    private static final Logger logger = Logger.getLogger(DSAT.class.getName());
    private final Environment environment;
    private final HashMap<String, Executor> executors;
    private final Map<Scenario, Adapter> scenariosElements;
    private final SuiteResult suiteResult = new SuiteResult();

    public DSAT(String environmentPath, String... scenarios) {
        SuiteActionPerform suiteActions = new SuiteActionPerform(environmentPath, scenarios);
        this.environment = suiteActions.getEnvironment();
        this.executors = suiteActions.getExecutors();
        LinkedList<Scenario> scenariosList = suiteActions.getScenarios();
        AdapterConfig adapterConfig = suiteActions.getAdapterConfig();
        this.scenariosElements = scenariosElements(scenariosList, adapterConfig);
    }

    public SuiteResult getSuiteResult() {
        JSONParser.convertSuite(suiteResult);
        return suiteResult;
    }

    public void performSuite() {
        this.suiteResult.setSuiteName(StaticVariables.uuidGeneration());
        LinkedList<ScenarioResult> scenarioResultsList = new LinkedList<>();
        for(Map.Entry<Scenario, Adapter> scenario : scenariosElements.entrySet()){
            ScenarioActionPerform scenarioActions = new ScenarioActionPerform(
                    environment, provideExecutor(scenario.getKey()), scenario.getValue(), scenario.getKey());
            logger.fine("Scenario: " + scenarioActions.getScenario() + " is getting started");
            scenarioActions.startScenario();
            scenarioResultsList.add(scenarioActions.getScenarioResult());
        }
        this.suiteResult.setScenarioResultsList(scenarioResultsList);
    }

    private Map<Scenario, Adapter> scenariosElements(LinkedList<Scenario> scenariosList, AdapterConfig adapterConfig){
        Map<Scenario, Adapter> scenariosElements = new LinkedHashMap<>();
        for(Scenario scenario : scenariosList){
            AdapterProvider adapterProvider = new AdapterProvider(adapterConfig, scenario);
            scenariosElements.put(scenario, adapterProvider.getAdapter());
        }
        return scenariosElements;
    }

    private Executor provideExecutor(Scenario scenario){
        //TODO
        // Exceptions and remove hardcode
        if (scenario.getExecutor().getName().equalsIgnoreCase(Executions.selenium.toString()) && scenario.getExecutor().isRemote()) {
            return executors.get(Executions.selenium + "." + Executions.remote);
        } else if (scenario.getExecutor().getName().equalsIgnoreCase(Executions.selenium.toString()) && !scenario.getExecutor().isRemote()){
            return executors.get(Executions.selenium + "." + Executions.local);
        } else if (scenario.getExecutor().getName().equalsIgnoreCase(Executions.restassured.toString())){
            return executors.get(Executions.restassured.toString());
        } else {
            throw new ExecutorException("There is no supported executor");
        }
    }

}
