package com.equbik.framework.behavior.restassured;

import com.equbik.framework.behavior.TakeAction;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.models.artifact_model.ActionResult;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.HTTPMethods;
import com.equbik.framework.services.StaticVariables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class POSTRequest extends TakeActionRestAssured implements TakeAction {

    private final Element element;

    public POSTRequest(Execution execution, Element element) {
        super(element);
        this.element = element;
    }

    @Override
    public ActionResult takeAction() {
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(element.getCode());
        return takeAction(HTTPMethods.POST, request, element.getMarker(), this.getClass().getSimpleName());
    }

}
