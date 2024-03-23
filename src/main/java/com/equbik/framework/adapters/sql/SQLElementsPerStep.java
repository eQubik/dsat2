package com.equbik.framework.adapters.sql;

import com.equbik.framework.adapters.ElementsPerStep;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Step;

import java.util.List;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class SQLElementsPerStep extends ElementsPerStep {

    /*
     * SQLElementsPerStep class ...
     */

    public SQLElementsPerStep(MapSQLiteElements sqlElements) {
        super(sqlElements.dbElements());
    }

    public List<Element> getStepElements(Step step) {
        return super.getStepElements(step);
    }

}
