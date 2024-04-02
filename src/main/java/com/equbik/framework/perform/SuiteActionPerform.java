package com.equbik.framework.perform;

import com.equbik.framework.adapters.AdapterConfig;
import com.equbik.framework.adapters.AdapterConfigProvider;
import com.equbik.framework.executors.Executor;
import com.equbik.framework.executors.ExecutorProvider;
import com.equbik.framework.models.input_models.Environment;
import com.equbik.framework.models.input_models.Scenario;
import com.equbik.framework.perform.exceptions.ScenarioActionException;
import com.equbik.framework.services.JSONParser;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.dictionaries.Executions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class SuiteActionPerform {

    /*
     * SuiteActionPerform class is used to parse the environment config and scenarios and provide the according objects
     */

    private final Environment environment;
    private final LinkedList<Scenario> scenarios;
    private final HashMap<String, Executor> executors;
    private final AdapterConfig adapterConfig;

    public SuiteActionPerform(String environmentPath, String... scenarios) {
        this.scenarios = scenarios(scenarios);
        this.environment = JSONParser.parseEnvironment(environmentPath);
        this.executors = executors(environment);
        AdapterConfigProvider adapterConfigProvider = new AdapterConfigProvider(environment);
        this.adapterConfig = adapterConfigProvider.getAdapterConfig();
    }

    public Environment getEnvironment() {
        return environment;
    }

    public LinkedList<Scenario> getScenarios() {
        return scenarios;
    }

    public HashMap<String, Executor> getExecutors() {
        return executors;
    }

    public AdapterConfig getAdapterConfig() {
        return adapterConfig;
    }

    private LinkedList<Scenario> scenarios(String... scenariosPath) {
        LinkedList<Scenario> scenarios = new LinkedList<>();
        for (String scenarioPath : scenariosPath) {
            Scenario scenario = JSONParser.parseScenario(scenarioPath);
            if (scenario.getName() != null &&
                    scenario.getExecutor().getName() != null &&
                    !scenario.getSteps().isEmpty()) {
                scenarios.add(scenario);
            } else {
                throw new ScenarioActionException("Scenario fields are not provided or not valid");
            }
        }
        return scenarios;
    }

    private HashMap<String, Executor> executors(Environment environment) {
        HashMap<String, Environment.Executor> executorConfigs = new HashMap<>();
        return setExecutors(setExecutorsConfig(environment, executorConfigs));
    }

    private HashMap<String, Environment.Executor> setExecutorsConfig(Environment environment,
                                                                     HashMap<String, Environment.Executor> executorConfigs) {
        List<Environment.Executor> executors = environment.getExecutor().values().stream().toList();
        executorConfigs.putAll(seleniumRemote(executors, executorConfigs));
        executorConfigs.putAll(seleniumLocal(executors, executorConfigs));
        executorConfigs.putAll(restassured(executors, executorConfigs));
        return executorConfigs;
    }

    private HashMap<String, Environment.Executor> seleniumRemote(List<Environment.Executor> executors,
                                                                 HashMap<String, Environment.Executor> executorConfigs) {
        executors.stream()
                .filter(executor -> executor.getType().equalsIgnoreCase(Executions.selenium.toString()) &&
                        executor.isRemote())
                .findFirst().ifPresent(executor -> executorConfigs.put(Executions.selenium + "." + Executions.remote, executor));
        return executorConfigs;
    }

    private HashMap<String, Environment.Executor> seleniumLocal(List<Environment.Executor> executors,
                                                                HashMap<String, Environment.Executor> executorConfigs) {
        executors.stream()
                .filter(executor -> executor.getType().equalsIgnoreCase(Executions.selenium.toString()) &&
                        !executor.isRemote())
                .findFirst().ifPresent(executor -> executorConfigs.put(Executions.selenium + "." + Executions.local, executor));
        return executorConfigs;
    }

    private HashMap<String, Environment.Executor> restassured(List<Environment.Executor> executors,
                                                              HashMap<String, Environment.Executor> executorConfigs) {
        executors.stream()
                .filter(executor -> executor.getType().equalsIgnoreCase(Executions.restassured.toString()))
                .findFirst().ifPresent(executor -> executorConfigs.put(Executions.restassured.toString(), executor));
        return executorConfigs;
    }

    private HashMap<String, Executor> setExecutors(HashMap<String, Environment.Executor> executorConfigs) {
        HashMap<String, Executor> executors = new HashMap<>();
        for (Map.Entry<String, Environment.Executor> executor : executorConfigs.entrySet()) {
            String executorIgnoreCase = StaticVariables.executors(executor.getValue().getType());
            ExecutorProvider executorProvider = new ExecutorProvider(executorIgnoreCase, executor.getValue());
            executors.put(executor.getKey(), executorProvider.getExecutor());
        }
        return executors;
    }

}
