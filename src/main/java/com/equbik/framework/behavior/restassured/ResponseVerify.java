package com.equbik.framework.behavior.restassured;

import io.restassured.response.ValidatableResponse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ResponseVerify {

    public static Map<String, String> responseData(
            Map<String, String> additionalMessage,
            ValidatableResponse validatableResponse){
        String responseCode = String.valueOf(validatableResponse.extract().response().getStatusCode());
        String timing = String.valueOf(validatableResponse.extract().response().timeIn(TimeUnit.MILLISECONDS));
        String body = validatableResponse.extract().response().body().asPrettyString();
        additionalMessage.put("response_code", responseCode);
        additionalMessage.put("timing", timing);
        additionalMessage.put("body", body);
        return additionalMessage;
    }

}
