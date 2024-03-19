package com.equbik.framework.adapters.csv;

import com.equbik.framework.adapters.ElementsPerStep;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class CSVElementsPerStep extends ElementsPerStep {

    /*
     * CSVElementsPerStep class forms the list of elements from CSV file that is used as an adapter in the Scenario
     */

    private final List<Element> elementsList;

    public CSVElementsPerStep(MapCSVElements csvElements) {
        this.elementsList = csvElements.csvElements();
    }

    public List<Element> getStepElements(Step step) {
        return getStepElements(step, elementsList);
    }

}
