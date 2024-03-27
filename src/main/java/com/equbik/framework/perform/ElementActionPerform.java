package com.equbik.framework.perform;

import com.equbik.framework.behavior.selenium.advanced.AWebElement;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.models.artifact_model.ActionResult;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.services.Executions;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.Status;
import com.equbik.framework.services.StatusMessage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ElementActionPerform {

    /*
     * ElementActionPerform class is used to invoke the necessary Action's takeAction method depending on the Execution
     */

    private static final Logger logger = Logger.getLogger(ElementActionPerform.class.getName());
    private final Execution execution;
    private final String executionName;
    private final Environment environment;
    private final Element element;
    private final String action;
    private final Status previousStatus;

    public ElementActionPerform(Execution execution, Environment environment, Element element, Status status) {
        this.execution = execution;
        this.executionName = execution.getClass().getSimpleName();
        this.environment = environment;
        this.element = element;
        this.action = element.getActionType().getActionName();
        this.previousStatus = status;
        logger.info("Element action is being performed: " + this);
    }

    public ActionResult getAction() {
        ActionResult result = new ActionResult();
        if (previousStatus.equals(Status.Success) || previousStatus.equals(Status.Null)) {
            logger.fine("Previous result is: " + previousStatus + ". Continue performing...");
            return invokeAction();
        } else {
            return skippingPart(result);
        }
    }

    private ActionResult invokeAction() {
        try {
            Class<?> actionClass = getActionClass();
            Object instance = getActionInstance(actionClass);
            ActionResult result = (ActionResult) actionClass.getMethod("takeAction").invoke(instance);
            logger.info("Result is: " + result);
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.warning("Action invocation failure. Execution is being Skipped.");
            throw new RuntimeException("Action invocation failure");
        }
    }

    private ActionResult skippingPart(ActionResult result) {
        StatusMessage statusMessage = new StatusMessage(Status.Skipped, element, element.getActionType().getActionName());
        logger.warning(element.getId() + " element action " + element.getActionType().getActionName() + " is being skipped");
        return result.standard(Status.Skipped, statusMessage.getStatusMessage().trim());
    }

    private Class<?> getActionClass() {
        try {
            if (isSelenium()) {
                return Class.forName("com.equbik.framework.behavior.selenium." + action);
            } else if (isRestAssured()) {
                return Class.forName("com.equbik.framework.behavior.restassured." + action);
            } else {
                logger.warning("There is no any available action for the mentioned Execution. Execution is being Skipped.");
                throw new RuntimeException("There is no any available action for the mentioned Execution");
            }
        } catch (ClassNotFoundException e) {
            logger.warning("Mentioned Action is not available in the current Execution. Execution is being Skipped.");
            throw new RuntimeException("Mentioned Action is not available in the current Execution");
        }
    }

    private Object getActionInstance(Class<?> actionClass) {
        try {
            if (isSelenium()) {
                Constructor<?> constructor = actionClass.getDeclaredConstructor(Execution.class, Element.class, AWebElement.class);
                return constructor.newInstance(execution, element, advancedElement());
            } else if (isRestAssured()) {
                setHTTPMarkerOnDemand();
                Constructor<?> constructor = actionClass.getDeclaredConstructor(Execution.class, Element.class);
                return constructor.newInstance(execution, element);
            } else {
                logger.warning("There is no any available action for the mentioned Execution. Execution is being Skipped.");
                throw new RuntimeException("There is no any available action for the mentioned Execution");
            }
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            logger.warning("Action class construction creation error. Execution is being Skipped.");
            throw new RuntimeException("Action class construction creation error");
        }
    }

    private AWebElement advancedElement() {
        return new AWebElement(environment);
    }

    private boolean isSelenium() {
        return executionName.equals(Executions.SeleniumBrowser.toString());
    }

    private boolean isRestAssured() {
        return executionName.equals(Executions.RestAssuredSetup.toString());
    }

    private void setHTTPMarkerOnDemand() {
        if (element.getRelatedElement() != null) {
            StaticVariables.isConfigured.putIfAbsent(element, false);
            if (StaticVariables.isConfigured.get(element).equals(false)) {
                try {
                    int relatedId = StaticVariables.ids.get(element.getRelatedElement());
                    element.setMarker(element.getMarker() + "/" + relatedId);
                    StaticVariables.isConfigured.put(element, true);
                    logger.fine(element.getId() + " element is preconfigured: " + element.getMarker());
                } catch (Exception e) {
                    logger.fine(element.getId() + " element won't preconfigured on demand");
                }
                logger.fine(element.getId() + " element is preconfigured: " + element.getMarker());
            }
        }
    }

    @Override
    public String toString() {
        return "ElementActionPerform{" +
                "executionName='" + executionName + '\'' +
                ", element=" + element +
                ", action='" + action + '\'' +
                '}';
    }

}
