package com.equbik.framework.behavior.restassured;

import com.equbik.framework.models.artifact_model.Results;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.Status;
import com.equbik.framework.services.StatusMessage;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class TakeActionRestAssured {

    private final Element element;
    private final Map<String, String> additionalMessage = new LinkedHashMap<>();

    public TakeActionRestAssured(Element element){
        this.element = element;
    }

    public Results takeAction(Response response, String actionName) {
        Results result = new Results();
        StatusMessage statusMessage;
        ValidatableResponse validatableResponse;
        try {
            validatableResponse = response.then();
            statusMessage = new StatusMessage(Status.Success, element, actionName,
                    ResponseVerify.responseData(additionalMessage, validatableResponse));
            return result.additional(Status.Success, statusMessage.getStatusMessage().trim(), content(validatableResponse));
        } catch (Exception e) {
            additionalMessage.put("error", e.getMessage());
            statusMessage = new StatusMessage(Status.Failed, element, actionName, additionalMessage);
            return result.standard(Status.Failed, statusMessage.getStatusMessage().trim());
        }
    }

    private Map<String, String> content(ValidatableResponse validatableResponse) {
        return StaticVariables.connectionContent(validatableResponse);
    }

}
