package com.equbik.framework.perform;

import com.equbik.framework.executions.Execution;
import com.equbik.framework.models.artifact_model.Results;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.Status;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class StepActionPerform {

    /*
     * StepActionPerform class configures each element(if needed) before execution and stores step's results
     */

    private static final Logger logger = Logger.getLogger(StepActionPerform.class.getName());
    private final Execution execution;
    private final Environment environment;
    private final String stepName;
    private final List<Element> elements;
    private final Map<String, String> variables;
    private final List<Results> results = new LinkedList<>();

    public StepActionPerform(Execution execution, Environment environment, List<Element> elements, Map<String, String> variables, String stepName) {
        this.execution = execution;
        this.environment = environment;
        this.elements = elements;
        this.variables = variables;
        this.stepName = stepName;
        logger.info(stepName + " step is being performed " + this);
    }

    public void process(Status previousStepResult) {
        preConfigElements(elements);
        getStepResults(previousStepResult, execution);
    }

    public String getStepName() {
        return stepName;
    }

    public List<Results> getResults() {
        return results;
    }

    private void preConfigElements(List<Element> elements) {
        for (Element element : elements) {
            int typeId = element.getActionType().getActionType();
            if ((typeId == 2 || typeId == 8 || typeId == 13) && variables.containsKey(element.getValue())){
                setValue(element);
            } else if ((typeId == 7 || typeId == 12) && variables.containsKey(element.getValue())){
                setMarker(element);
            } else if(typeId == 19){
                setCode(element);
            } else if ((typeId == 20 || typeId == 22) ){
                setHTTPMarker(element);
            } else if (typeId == 21 ){
                setHTTPMarker(element);
                setCode(element);
            }
        }
    }

    private void setValue(Element element){
        //Used for direct value injection. For example(type "wiki" on the google main page)
        element.setValue(variables.get(element.getValue()));
        logger.fine(element.getId() + " element is preconfigured: " + element.getValue());
    }

    private void setMarker(Element element){
        //Badass action types used to replace pattern in marker value with the provided value before execution
        element.setMarker(element.getMarker().replace(element.getValue(), variables.get(element.getValue())));
        logger.fine(element.getId() + " element is preconfigured: " + element.getMarker());
    }

    private void setCode(Element element){
        for (String key: variables.keySet()){
            if(element.getCode().contains(key)) {
                element.setCode(element.getCode().replace(key, variables.get(key)));
            }
        }
        logger.fine(element.getId() + " element is preconfigured: " + element.getCode());
    }

    private void setHTTPMarker(Element element){
        //todo
        // error before execution
        if(element.getRelatedElement() == null) {
            element.setValue(variables.get(element.getValue()));
            element.setMarker(element.getMarker() + "/" + element.getValue());
            System.out.println(element.getName() + " 1");
        } else {
            logger.info(element.getId() + " priority set to related element");
            try {
                System.out.println(StaticVariables.ids);
                int relatedId = StaticVariables.ids.get(element.getRelatedElement());
                System.out.println(relatedId);
                System.out.println(element.getName() + " 2");
                element.setMarker(element.getMarker() + "/" + relatedId);
                logger.fine(element.getId() + " element is preconfigured: " + element.getMarker());
            } catch (Exception e) {
                System.out.println(element.getName() + " 3");
                logger.info(element.getId() + " element preconfiguring failed. Setting up with provided value");
                element.setMarker(element.getMarker() + "/" + element.getValue());
            }
        }
        logger.fine(element.getId() + " element is preconfigured: " + element.getMarker());
    }

    private void getStepResults(Status previousStepResult, Execution execution) {
        logger.info("Previous step result is: " + previousStepResult);
        if (results.isEmpty()) {
            try {
                ElementActionPerform elementAction = new ElementActionPerform(execution, environment, elements.get(0), previousStepResult);
                results.add(elementAction.getAction());
            } catch (IndexOutOfBoundsException e){
                logger.warning("There is no available elements provided. Execution is being Skipped.");
                throw new RuntimeException("There is no available elements provided");
            }
        }
        for (int i = 1; i < elements.size(); i++) {
            ElementActionPerform elementAction = new ElementActionPerform(execution, environment, elements.get(i), results.get(results.size() - 1).getStatus());
            results.add(elementAction.getAction());
        }
    }

    @Override
    public String toString() {
        return "StepActionPerform{" +
                "stepName='" + stepName + '\'' +
                ", elements=" + elements +
                ", variables=" + variables +
                '}';
    }

}
