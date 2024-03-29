package com.equbik.framework.adapters.sql;

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

public class AdapterSQL extends AdapterMethods implements Adapter {

    /*
     * AdapterSQL class provides SQLite Adapter implementation to form the Elements' collection for the whole scenario
     */

    private final Map<String, List<Element>> stepElements;
    private final Scenario scenario;
    private final SQLElementsPerStep elementsPerStep;

    public AdapterSQL(Scenario scenario, AdapterConfig adapterConfig){
        this.scenario = scenario;
        MapSQLiteElements sqlElements = (MapSQLiteElements) adapterConfig;
        this.elementsPerStep = new SQLElementsPerStep(scenario, sqlElements);
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
