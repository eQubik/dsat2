package com.equbik.framework.behavior.restassured;

import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.output_models.ActionResult;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.StatusMessage;
import com.equbik.framework.services.dictionaries.HTTPMethods;
import com.equbik.framework.services.dictionaries.Status;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

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

    public TakeActionRestAssured(Element element) {
        this.element = element;
    }

    public ActionResult takeAction(HTTPMethods method, RequestSpecification request, String marker, String actionName) {
        ActionResult result = new ActionResult();
        StatusMessage statusMessage;
        ValidatableResponse validatableResponse;
        try {
            validatableResponse = sendRequest(method, request, marker).then();
            statusMessage = new StatusMessage(Status.Success, element, actionName,
                    ResponseVerify.responseData(additionalMessage, validatableResponse));
            return result.additional(Status.Success, statusMessage.getStatusMessage().trim(), content(validatableResponse));
        } catch (Exception e) {
            additionalMessage.put("failure", e.getMessage());
            statusMessage = new StatusMessage(Status.Failed, element, actionName, additionalMessage);
            return result.standard(Status.Failed, statusMessage.getStatusMessage().trim());
        }
    }

    private Map<String, String> content(ValidatableResponse validatableResponse) {
        return StaticVariables.connectionContent(validatableResponse);
    }

    private Response sendRequest(HTTPMethods method, RequestSpecification request, String marker) {
        if (method.equals(HTTPMethods.GET)) {
            return request.get(marker);
        } else if (method.equals(HTTPMethods.POST)) {
            Response response = request.post(marker);
            StaticVariables.ids.put(element.getId().toString(), response.body().jsonPath().getInt("id"));
            return response;
        } else if (method.equals(HTTPMethods.PUT)) {
            return request.put(marker);
        } else if (method.equals(HTTPMethods.DELETE)) {
            return request.delete(marker);
        } else {
            throw new RuntimeException("Wrong HTTP Method provided");
        }
    }

}
