package com.equbik.framework.behavior.restassured;

import com.equbik.framework.behavior.TakeAction;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.models.artifact_model.Results;
import com.equbik.framework.models.element_model.Element;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PUTRequest extends TakeActionRestAssured implements TakeAction {

    private final Element element;

    public PUTRequest(Execution execution, Element element) {
        super(element);
        this.element = element;
    }

    @Override
    public Results takeAction() {
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(element.getCode());
        Response response = request.put(element.getMarker());
        return takeAction(response, this.getClass().getSimpleName());
    }

}
