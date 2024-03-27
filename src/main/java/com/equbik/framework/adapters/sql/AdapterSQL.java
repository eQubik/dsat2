package com.equbik.framework.adapters.sql;

import com.equbik.framework.adapters.Adapter;
import com.equbik.framework.adapters.AdapterConfig;
import com.equbik.framework.adapters.AdapterMethods;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Scenario;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class AdapterSQL extends AdapterMethods implements Adapter {

    /*
     * AdapterSQL class provides Adapter implementation and forms the Elements list for each step
     */

    private static final Logger logger = Logger.getLogger(AdapterSQL.class.getName());
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
