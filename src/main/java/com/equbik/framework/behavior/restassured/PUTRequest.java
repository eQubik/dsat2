package com.equbik.framework.behavior.restassured;

import com.equbik.framework.behavior.TakeAction;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.models.output_models.ActionResult;
import com.equbik.framework.services.dictionaries.HTTPMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class PUTRequest extends TakeActionRestAssured implements TakeAction {

    private final Element element;

    public PUTRequest(Execution execution, Element element) {
        super(element);
        this.element = element;
    }

    @Override
    public ActionResult takeAction() {
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(element.getCode());
        return takeAction(HTTPMethods.PUT, request, element.getMarker(), this.getClass().getSimpleName());
    }

}
