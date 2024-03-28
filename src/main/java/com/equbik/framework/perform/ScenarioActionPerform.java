package com.equbik.framework.perform;

import com.equbik.framework.adapters.Adapter;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.executions.ExecutionProvider;
import com.equbik.framework.executions.SeleniumBrowser;
import com.equbik.framework.executors.Executor;
import com.equbik.framework.models.artifact_model.ScenarioResult;
import com.equbik.framework.models.artifact_model.StepResult;
import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.models.json_model.Scenario;
import com.equbik.framework.models.json_model.Step;
import com.equbik.framework.services.Executions;
import com.equbik.framework.services.Status;

import java.util.*;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ScenarioActionPerform {

    /*
     * ScenarioActionPerform class is doing a lot of things. Creates a Steps map, initializes Execution and Adapter, doing post jobs
     */

    private static final Logger logger = Logger.getLogger(ScenarioActionPerform.class.getName());
    private final Scenario scenario;
    private final Adapter adapter;
    private final Execution execution;
    private final Environment environment;
    private final ScenarioResult scenarioResult = new ScenarioResult();

    public ScenarioActionPerform(Environment environment, Executor executor, Adapter adapter, Scenario scenario) {
        this.environment = environment;
        ExecutionProvider executionProvider = new ExecutionProvider(executor);
        this.execution = executionProvider.getExecution();
        this.adapter = adapter;
        this.scenario = scenario;
        logger.info("Performing scenario: " + this);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public ScenarioResult getScenarioResult() {
        return scenarioResult;
    }

    public void startScenario() {
        List<StepActionPerform> stepsList = new LinkedList<>();
        createStepsMap(scenario, stepsList);
        scenarioResult.setScenarioName(scenario.getFlowName());
        scenarioResult.setStepResultsList(executeSteps(stepsList));
        postJobs();
    }

    private Status previousStepResult() {
        try {
            //Map.Entry<String, List<ActionResult>> lastEntry = resultsMap.entrySet().stream().skip(resultsMap.size() - 1).findFirst().orElse(null);
            StepResult previousStepResult = scenarioResult.getStepResultsList().stream().skip(scenarioResult.getStepResultsList().size() - 1).findFirst().orElse(null);
            return previousStepResult.getActionResultsList().getLast().getStatus();
            //return lastEntry.getValue().get(lastEntry.getValue().size() - 1).getStatus();
        } catch (Exception e) {
            return Status.Null;
        }
    }

    private void createStepsMap(Scenario scenario, List<StepActionPerform> stepsList) {
        for (Step step : scenario.getSteps()) {
            stepsList.add(new StepActionPerform(
                    execution,
                    environment,
                    adapter.stepElements().get(step.getStepName()),
                    initializeStepVariables(step),
                    step.getStepName())
            );
        }
    }

    private LinkedList<StepResult> executeSteps(List<StepActionPerform> stepsList) {
        LinkedList<StepResult> results = new LinkedList<>();
        for (StepActionPerform stepAction : stepsList) {
            stepAction.process(previousStepResult());
            results.add(stepAction.getStepResult());
        }
        return results;
    }

    private void postJobs(){
        if(execution.getClass().getSimpleName().equals(Executions.SeleniumBrowser.toString())){
            SeleniumBrowser browser = (SeleniumBrowser) execution;
            if (browser.isQuit()) {
                logger.info("Selenium browser is being closed");
                browser.getDriver().quit();
            } else {
                logger.info("Selenium browser will stay active");
            }
        }
    }

    private Map<String, String> initializeStepVariables(Step step){
        Map<String, String> variables = new HashMap<>();
        try {
            variables.putAll(step.getVariables());
            logger.info("Variables for " + step.getStepName() + " initialized: " + step.getVariables());
            return variables;
        } catch (NullPointerException e) {
            logger.info("Skipping variables for " + step.getStepName());
        }
        return variables;
    }

    @Override
    public String toString() {
        return "ScenarioActionPerform{" +
                "scenario=" + scenario +
                ", adapter='" + adapter + '\'' +
                ", execution=" + execution +
                '}';
    }

}
