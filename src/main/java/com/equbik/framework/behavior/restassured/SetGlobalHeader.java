package com.equbik.framework.behavior.restassured;

import com.equbik.framework.behavior.TakeAction;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.output_models.ActionResult;
import com.equbik.framework.services.StatusMessage;
import com.equbik.framework.services.dictionaries.Status;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class SetGlobalHeader implements TakeAction {

    private final Element element;
    private final Map<String, String> additionalMessage = new LinkedHashMap<>();

    public SetGlobalHeader(Execution execution, Element element) {
        this.element = element;
    }

    @Override
    public ActionResult takeAction() {
        ActionResult result = new ActionResult();
        StatusMessage statusMessage;
        try {
            RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
            requestSpecBuilder.addHeader(element.getMarker(), element.getValue());
            RestAssured.requestSpecification = requestSpecBuilder.build();
            statusMessage = new StatusMessage(Status.Success, element, this.getClass().getSimpleName());
            return result.standard(Status.Success, statusMessage.getStatusMessage().trim());
        } catch (Exception e) {
            additionalMessage.put("error", e.getMessage());
            statusMessage = new StatusMessage(Status.Failed, element, this.getClass().getSimpleName(), additionalMessage);
            return result.standard(Status.Failed, statusMessage.getStatusMessage().trim());
        }
    }

}
