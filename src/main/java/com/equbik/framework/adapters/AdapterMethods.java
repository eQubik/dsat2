package com.equbik.framework.adapters;

import com.equbik.framework.adapters.exceptions.AdapterException;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.input_models.Scenario;
import com.equbik.framework.models.input_models.Step;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public abstract class AdapterMethods {

    /*
     * AdapterMethods class implements repeatable stepElements method used in inherited Adapter classes
     */

    private static final Logger logger = Logger.getLogger(AdapterMethods.class.getName());

    protected Map<String, List<Element>> stepElements(Scenario scenario, ElementsPerStep elementsPerStep) {
        Map<String, List<Element>> stepElements = new LinkedHashMap<>();
        for (Step step : scenario.getSteps()) {
            try {
                stepElements.put(step.getStepName(), elementsPerStep.getStepElements(scenario.getName(), step));
            } catch (Exception e){
                logger.warning("Forming Step elements map skipped due to: " + e.getMessage());
                throw new AdapterException("Forming Step elements map skipped due to: " + e.getMessage());
            }
        }
        return stepElements;
    }

}
