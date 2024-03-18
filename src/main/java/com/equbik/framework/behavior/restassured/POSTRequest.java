package com.equbik.framework.behavior.restassured;

import com.equbik.framework.behavior.TakeAction;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.executions.RestAssuredSetup;
import com.equbik.framework.models.artifact_model.Results;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.Status;
import com.equbik.framework.services.StatusMessage;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class POSTRequest implements TakeAction {

    private final Element element;
    private final Map<String, String> additionalMessage = new LinkedHashMap<>();

    public POSTRequest(Execution execution, Element element) {
        RestAssuredSetup restAssuredSetup = (RestAssuredSetup) execution;
        this.element = element;
    }

    @Override
    public Results takeAction() {
        Results result = new Results();
        StatusMessage statusMessage;
        ValidatableResponse validatableResponse;
        try {
            RequestSpecification request = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(element.getCode());
            Response response = request.post(element.getMarker());
            validatableResponse = response.then();
            String responseCode = String.valueOf(validatableResponse.extract().response().getStatusCode());
            String timing = String.valueOf(validatableResponse.extract().response().timeIn(TimeUnit.MILLISECONDS));
            String body = validatableResponse.extract().response().body().asPrettyString();
            additionalMessage.put("response_code", responseCode);
            additionalMessage.put("timing", timing);
            additionalMessage.put("body", body);
            statusMessage = new StatusMessage(Status.Success, element, this.getClass().getSimpleName(), additionalMessage);
            return result.additional(Status.Success, statusMessage.getStatusMessage().trim(), content(validatableResponse));
        } catch (Exception e) {
            additionalMessage.put("error", e.getMessage());
            statusMessage = new StatusMessage(Status.Failed, element, this.getClass().getSimpleName(), additionalMessage);
            return result.standard(Status.Failed, statusMessage.getStatusMessage().trim());
        }
    }

    private Map<String, String> content(ValidatableResponse validatableResponse) {
        return StaticVariables.connectionContent(validatableResponse);
    }

}
