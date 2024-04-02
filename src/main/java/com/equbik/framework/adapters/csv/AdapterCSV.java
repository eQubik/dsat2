package com.equbik.framework.adapters.csv;

import com.equbik.framework.adapters.Adapter;
import com.equbik.framework.adapters.AdapterConfig;
import com.equbik.framework.adapters.AdapterMethods;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.input_models.Scenario;

import java.util.List;
import java.util.Map;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class AdapterCSV extends AdapterMethods implements Adapter {

    /*
     * AdapterCSV class provides CSV Adapter implementation to form the Elements' collection for the whole scenario
     */

    private final Map<String, List<Element>> stepElements;
    private final Scenario scenario;
    private final CSVElementsPerStep elementsPerStep;

    public AdapterCSV(Scenario scenario, AdapterConfig adapterConfig){
        this.scenario = scenario;
        MapCSVElements csvElements = (MapCSVElements) adapterConfig;
        this.elementsPerStep = new CSVElementsPerStep(scenario, csvElements);
        this.stepElements = stepElements();
    }

    @Override
    public Map<String, List<Element>> stepElements() {
        return stepElements(scenario, elementsPerStep);
    }

    public Map<String, List<Element>> getStepElements() {
        return stepElements;
    }

}
