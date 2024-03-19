package com.equbik.framework.adapters.sql;

import com.equbik.framework.adapters.ElementsPerStep;
import com.equbik.framework.adapters.csv.MapCSVElements;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Scenario;
import com.equbik.framework.models.json_model.Step;
import com.equbik.framework.services.Fields;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class SQLElementsPerStep extends ElementsPerStep {

    /*
     * SQLElementsPerStep class ...
     */

    private final List<Element> elementsList;

    public SQLElementsPerStep(MapSQLiteElements sqlElements) {
        this.elementsList = sqlElements.dbElements();
    }

    public List<Element> getStepElements(Step step) {
        return getStepElements(step, elementsList);
    }

}
