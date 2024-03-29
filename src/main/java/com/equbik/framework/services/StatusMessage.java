package com.equbik.framework.services;

import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.dictionaries.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class StatusMessage {

    /*
     * StatusMessage class is used to form the information about the action execution. You can modify it as you need
     */

    private final Status status;
    private final Element element;
    private final String action;
    private final Map<String, String> additionalMessages = new HashMap<>();

    public StatusMessage(Status status, Element element, String action, Map<String, String> additionalMessages) {
        this.status = status;
        this.element = element;
        this.action = action;
        this.additionalMessages.putAll(additionalMessages);
    }

    public StatusMessage(Status status, Element element, String action) {
        this.status = status;
        this.element = element;
        this.action = action;
    }

    public String getStatusMessage(){
        switch (status){
            case Success -> {
                return successMessage();
            }
            case Skipped -> {
                return skipMessage();
            }
            case Failed -> {
                return failMessage();
            }
            default -> {
                return "No status message";
            }
        }
    }

    private String failMessage() {
        return "Action " + action + " failed: " + "\n" +
                getElementAttributes() +
                "Fail message: " + additionalMessages.entrySet();
    }

    private String skipMessage() {
        if(!additionalMessages.isEmpty()) {
            return "Action " + action + " skipped: " + "\n" +
                    getElementAttributes() +
                    "Context: " + additionalMessages.entrySet();
        } else {
            return "Action " + action + " skipped: " + "\n" +
                    getElementAttributes();
        }
    }

    private String successMessage() {
        if(!additionalMessages.isEmpty()) {
            return "Action " + action + " performed: " + "\n" +
                    getElementAttributes() +
                    "Context: " + additionalMessages.entrySet();
        } else {
            return "Action " + action + " performed: " + "\n" +
                    getElementAttributes();
        }
    }

    private String getElementAttributes() {
        StringBuilder message = new StringBuilder();
        if(element.getId() != null){
            message.append("element id: ").append(element.getId()).append(",\n");
        }
        if(!Objects.equals(element.getName(), "")){
            message.append("element name: ").append(element.getName()).append(",\n");
        }
        if(!Objects.equals(element.getMarker(), "")){
            message.append("element marker: ").append(element.getMarker()).append("\n");
        }
        return String.valueOf(message);
    }

}
