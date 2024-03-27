package com.equbik.framework.adapters;

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

public class ElementsPerStep {

    /*
     * AdapterMethods class ...
     */

    private final List<Element> elementsList;

    public ElementsPerStep(List<Element> elementsList){
        this.elementsList = elementsList;
    }

    public List<Element> getStepElements(String flow, Step step) {
        List<Element> elementsPerStep = new ArrayList<>();
        List<String> elementsInStep;
        elementsInStep = step.getElements();
        for (String element : elementsInStep) {
            Optional<Element> matchingElement = elementsList.stream()
                    .filter(e -> e.getScenario().equals(flow))
                    .filter(e -> e.getStep().equals(step.getStepName()))
                    .filter(e -> e.getName().equals(element))
                    .findFirst();
            matchingElement.ifPresent(elementsPerStep::add);
        }
        return elementsPerStep;
    }

}
