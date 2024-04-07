package com.equbik.framework.perform;

import com.equbik.framework.adapters.Adapter;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.executions.ExecutionProvider;
import com.equbik.framework.executions.SeleniumBrowser;
import com.equbik.framework.executors.Executor;
import com.equbik.framework.models.input_models.Environment;
import com.equbik.framework.models.input_models.Scenario;
import com.equbik.framework.models.input_models.Step;
import com.equbik.framework.models.output_models.ScenarioResult;
import com.equbik.framework.models.output_models.StepResult;
import com.equbik.framework.services.dictionaries.Status;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ScenarioActionPerform {

    /*
     * ScenarioActionPerform class prepares steps for execution and doing post jobs
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
        scenarioResult.setScenarioName(scenario.getName());
        LinkedList<StepActionPerform> stepsList = new LinkedList<>();
        LinkedList<StepResult> stepsResults = new LinkedList<>();
        createStepsMap(scenario, stepsList);
        executeSteps(stepsList, stepsResults);
        scenarioResult.setStepResultsList(stepsResults);
        postJobs();
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

    private Status previousStepResult(LinkedList<StepResult> stepResults) {
        try {
            return stepResults.getLast().getActionResultsList().getLast().getStatus();
        } catch (Exception e) {
            return Status.Null;
        }
    }

    private void executeSteps(List<StepActionPerform> stepsList, LinkedList<StepResult> stepsResults) {
        for (StepActionPerform stepAction : stepsList) {
            stepAction.process(previousStepResult(stepsResults));
            stepsResults.add(stepAction.getStepResult());
        }
    }

    private void postJobs(){
        if(execution instanceof SeleniumBrowser browser){
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
