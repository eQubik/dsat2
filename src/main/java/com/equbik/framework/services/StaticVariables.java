package com.equbik.framework.services;

import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.dictionaries.Executions;
import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class StaticVariables {

    /*
     * Use StaticVariables class when you need to share information across different parts of the execution process
     */

    //Generates the UUID where needed
    public static String uuidGeneration(){
        return UUID.nameUUIDFromBytes(LocalDateTime.now(ZoneOffset.UTC).toString().getBytes()).toString();
    }

    public static String uuidGeneration(String data){
        return UUID.nameUUIDFromBytes(data.getBytes()).toString();
    }

    //This map was added for backward compatibility because I already use previous version of this framework.
    //You can use action names directly in your data structure
    public static String action(int action){
        Map<Integer, String> actionsMap = new HashMap<>();
        //Selenium methods
        actionsMap.put(1, "ClickElement");
        actionsMap.put(2, "SendKeys");
        actionsMap.put(3, "GetSimpleText");
        actionsMap.put(4, "ClickEnter");
        actionsMap.put(5, "FocusAndClick");
        actionsMap.put(7, "GetSimpleText");
        actionsMap.put(8, "ThreadSleep");
        actionsMap.put(9, "IframeSwitch");
        actionsMap.put(10, "DefaultFrame");
        actionsMap.put(11, "ClickTab");
        actionsMap.put(12, "ClickElement");
        actionsMap.put(13, "UploadFile");
        actionsMap.put(14, "SetCurrentWindow");
        actionsMap.put(15, "ChangeBrowserTab");
        actionsMap.put(16, "ClickJS");
        //Experimental things...
        actionsMap.put(6, "EnterByPIN");
        actionsMap.put(23, "GetLocalStorage");
        actionsMap.put(17, "JSExecutor");
        //RestAssured
        actionsMap.put(18, "GETRequest");
        actionsMap.put(19, "POSTRequest");
        actionsMap.put(20, "GETRequest");
        actionsMap.put(21, "PUTRequest");
        actionsMap.put(22, "DELETERequest");
        actionsMap.put(24, "SetGlobalHeader");
        //Other executor's methods

        return actionsMap.get(action);
    }

    //When you start the app, you need to mention the appropriate executor's name
    //which is typically starts with the uppercase because it's a Java class name
    public static String executors(String executor){
        if(executor.equalsIgnoreCase(Executions.selenium.toString())){
            return Executions.Selenium.toString();
        } else if(executor.equalsIgnoreCase(Executions.restassured.toString())){
            return Executions.RestAssured.toString();
        } else {
            return null;
        }
    }

    //TODO
    // Will be removed or optimized during further development:

    //I use this Set to switch the browser tabs
    public static Set<String> tabs = new LinkedHashSet<>();

    //I use this Map to share the IDs of the inserted entities
    public static Map<String, Integer> ids = new HashMap<>();

    //I use this Map to save the state of the configured elements(RestAssured - relatedElements)
    public static Map<Element, Boolean> isConfigured = new HashMap<>();

    //I use this Map to share the data across various scenarios inside single suite
    public static Map<String, String> sharedData = new HashMap<>();

    //I use this Map to add the Selenium WebDriver information to the ActionResult class
    public static Map<String, String> driverContent(WebDriver driver) {
        Map<String, String> content = new LinkedHashMap<>();
        content.put("title", driver.getTitle());
        content.put("current url", driver.getCurrentUrl());
        return content;
    }

    //I use this Map to add the RestAssured Response information to the ActionResult class
    public static Map<String, String> connectionContent(ValidatableResponse validatableResponse) {
        Map<String, String> content = new LinkedHashMap<>();
        content.put("content_type", validatableResponse.extract().contentType());
        content.put("headers", validatableResponse.extract().headers().toString());
        content.put("cookies", validatableResponse.extract().cookies().toString());
        return content;
    }

}
