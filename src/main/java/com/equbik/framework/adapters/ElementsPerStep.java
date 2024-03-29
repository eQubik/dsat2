package com.equbik.framework.adapters;

import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.input_models.Step;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ElementsPerStep {

    /*
     * ElementsPerStep class is used to filter the whole Elements' list to provide Scenario Step elements
     */

    private final List<Element> elementsList;

    public ElementsPerStep(List<Element> elementsList){
        this.elementsList = elementsList;
    }

    public List<Element> getStepElements(String scenario, Step step) {
        List<Element> elementsPerStep = new LinkedList<>();
        List<String> elementsInStep;
        elementsInStep = step.getElements();
        for (String element : elementsInStep) {
            Optional<Element> matchingElement = elementsList.stream()
                    .filter(e -> e.getScenario().equals(scenario))
                    .filter(e -> e.getStep().equals(step.getStepName()))
                    .filter(e -> e.getName().equals(element))
                    .findFirst();
            matchingElement.ifPresent(elementsPerStep::add);
        }
        return elementsPerStep;
        //TODO
        // Exception if no elements presented?
    }

}
