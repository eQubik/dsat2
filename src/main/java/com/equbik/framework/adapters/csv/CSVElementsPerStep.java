package com.equbik.framework.adapters.csv;

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

public class CSVElementsPerStep {

    /*
     * CSVElementsPerStep class forms the list of elements from CSV file that is used as an adapter in the Scenario
     */

    private final List<Element> elementsList;

    public CSVElementsPerStep(MapCSVElements toElement) {
        this.elementsList = toElement.csvToElementsList();
    }

    public List<Element> getStepElements(Step step) {
        List<Element> elementsPerStep = new ArrayList<>();
        List<String> elementsInStep;
        elementsInStep = step.getElements();
        for (String element : elementsInStep) {
            Optional<Element> matchingElement = elementsList.stream()
                    .filter(e -> e.getName().equals(element))
                    .findFirst();
            matchingElement.ifPresent(elementsPerStep::add);
        }
        return elementsPerStep;
    }

}
