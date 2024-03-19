package com.equbik.framework.adapters;

import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElementsPerStep {

    public List<Element> getStepElements(Step step, List<Element> elementsList) {
        List<Element> elementsPerStep = new ArrayList<>();
        List<String> elementsInStep;
        elementsInStep = step.getElements();
        for (String element : elementsInStep) {
            Optional<Element> matchingElement = elementsList.stream()
                    .filter(e -> e.getStep().equals(step.getStepName()))
                    .filter(e -> e.getName().equals(element))
                    .findFirst();
            matchingElement.ifPresent(elementsPerStep::add);
        }
        return elementsPerStep;
    }

}
