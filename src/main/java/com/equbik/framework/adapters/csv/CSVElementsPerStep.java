package com.equbik.framework.adapters.csv;

import com.equbik.framework.adapters.ElementsPerStep;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Step;

import java.util.List;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class CSVElementsPerStep extends ElementsPerStep {

    /*
     * CSVElementsPerStep class forms the list of elements from CSV file that is used as an adapter in the Scenario
     */

    public CSVElementsPerStep(MapCSVElements csvElements) {
        super(csvElements.csvElements());
    }

    public List<Element> getStepElements(Step step) {
        return super.getStepElements(step);
    }

}
