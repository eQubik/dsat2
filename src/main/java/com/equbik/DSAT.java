package com.equbik;

import com.equbik.framework.adapters.Adapter;
import com.equbik.framework.adapters.AdapterConfig;
import com.equbik.framework.adapters.AdapterProvider;
import com.equbik.framework.executors.Executor;
import com.equbik.framework.models.artifact_model.ScenarioResult;
import com.equbik.framework.models.artifact_model.SuiteResult;
import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.models.json_model.Scenario;
import com.equbik.framework.perform.ScenarioActionPerform;
import com.equbik.framework.perform.SuiteActionPerform;
import com.equbik.framework.services.Executions;
import com.equbik.framework.services.JSONParser;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
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
    // Remote execution, name each scenario inside a single execution(to share the state)

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
        this.suiteResult.setSuiteName(LocalDateTime.now(ZoneOffset.UTC).toString());
    }

    public SuiteResult getSuiteResult() {
        JSONParser.convertSuite(suiteResult);
        return suiteResult;
    }

    public void performSuite() {
        LinkedList<ScenarioResult> scenarioResultsList = new LinkedList<>();
        for(Map.Entry<Scenario, Adapter> scenario : scenariosElements.entrySet()){
            ScenarioActionPerform scenarioActions = new ScenarioActionPerform(
                    environment, provideExecutor(scenario.getKey()), scenario.getValue(), scenario.getKey());
            logger.fine("Scenario: " + scenarioActions.getScenario() + " is getting started");
            scenarioActions.startScenario();
            scenarioResultsList.add(scenarioActions.getScenarioResult());
        }
        suiteResult.setScenarioResultsList(scenarioResultsList);
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
        if (scenario.getExecutor().getName().equalsIgnoreCase(Executions.selenium.toString()) && scenario.getExecutor().isRemote()) {
            return executors.get(Executions.selenium + "|remote");
        } else if (scenario.getExecutor().getName().equalsIgnoreCase(Executions.selenium.toString()) && !scenario.getExecutor().isRemote()){
            return executors.get(Executions.selenium + "|local");
        } else if (scenario.getExecutor().getName().equalsIgnoreCase(Executions.restassured.toString())){
            return executors.get(Executions.restassured.toString());
        } else {
            throw new RuntimeException("There is no supported executor");
        }
    }

}
