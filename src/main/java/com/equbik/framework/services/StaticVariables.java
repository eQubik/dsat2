package com.equbik.framework.services;

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

    //Information from Driver's methods can be added here and then will be added to the Results class
    public static Map<String, String> driverContent(WebDriver driver) {
        Map<String, String> content = new LinkedHashMap<>();
        content.put("title", driver.getTitle());
        content.put("current url", driver.getCurrentUrl());
        return content;
    }

    //Information from Response can be added here and then will be added to the Results class
    public static Map<String, String> connectionContent(ValidatableResponse validatableResponse) {
        Map<String, String> content = new LinkedHashMap<>();
        content.put("content_type", validatableResponse.extract().contentType());
        //content.put("headers", validatableResponse.extract().headers().toString());
        content.put("cookies", validatableResponse.extract().cookies().toString());
        return content;
    }

    //Generates the UUID where needed
    public static String uuidGeneration(){
        return UUID.nameUUIDFromBytes(LocalDateTime.now(ZoneOffset.UTC).toString().getBytes()).toString();
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
        actionsMap.put(17, "JSExecutor");
        //RestAssured
        actionsMap.put(18, "GETRequest");
        //Other executor's methods

        //Experimental things...
        actionsMap.put(6, "EnterByPIN");

        return actionsMap.get(action);
    }

    //Used to change browser tabs during the test execution
    public static Set<String> tabs = new LinkedHashSet<>();

    //When you start the app, you need to mention the appropriate executor's name
    //which is typically starts with uppercase because it's a Java class name, however you can add another forms of the
    //executor's names for easy of use or if you have an OCD
    public static String executors(String executor){
//        Uncomment this part to add other forms of the executors names
//        Map<List<String>, String> executorsMap = new HashMap<>();
//        List<String> selenium = new ArrayList<>();
//        selenium.add("SELENIUM");
//        selenium.add("selenium");
//        selenium.add("Selenium");
//        selenium.add("sel");
//        executorsMap.put(selenium, "Selenium");
//        if (selenium.contains(executor)) {
//            return executorsMap.get(selenium);
        if(executor.equalsIgnoreCase(Executions.selenium.toString())){
            return Executions.Selenium.toString();
        } else if(executor.equalsIgnoreCase(Executions.restassured.toString())){
            return Executions.RestAssured.toString();
        } else {
            return null;
        }
    }

}
