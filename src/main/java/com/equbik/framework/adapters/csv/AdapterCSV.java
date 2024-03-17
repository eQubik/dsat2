package com.equbik.framework.adapters.csv;

import com.equbik.framework.adapters.Adapter;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Scenario;
import com.equbik.framework.models.json_model.Step;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class AdapterCSV implements Adapter {

    /*
     * AdapterCSV class provides Adapter implementation and forms the Elements list for each step
     */

    private static final Logger logger = Logger.getLogger(AdapterCSV.class.getName());
    private final Map<String, List<Element>> stepElements;
    private final Scenario scenario;
    private final CSVElementsPerStep elementsPerStep;

    public AdapterCSV(Scenario scenario, MapCSVElements csvElements){
        this.scenario = scenario;
        this.elementsPerStep = new CSVElementsPerStep(csvElements);
        this.stepElements = stepElements();
    }

    @Override
    public Map<String, List<Element>> stepElements() {
        Map<String, List<Element>> stepElements = new LinkedHashMap<>();
        for (Step step : scenario.getSteps()) {
            try {
                stepElements.put(step.getStepName(), elementsPerStep.getStepElements(step));
            } catch (Exception e){
                logger.warning("Skipping execution due to: " + e.getMessage());
                throw new RuntimeException("Skipping execution due to: " + e.getMessage());
            }
        }
        return stepElements;
    }

    public Map<String, List<Element>> getStepElements() {
        return stepElements;
    }

}
