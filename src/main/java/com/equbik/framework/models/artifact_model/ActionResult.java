package com.equbik.framework.models.artifact_model;

import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.Status;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

@Getter
@Data
public class ActionResult {

    /*
     * Results class represents the result of the execution of each element during the execution
     */

    private final Map<String, String> executionContent = new LinkedHashMap<>();
    private Status status;
    private String transaction;
    private String startTime;
    private String endTime;

    public ActionResult() {
        this.startTime = LocalDateTime.now(ZoneOffset.UTC).toString();
    }

    public ActionResult additional(Status status, String message, Map<String, String> content){
        this.executionContent.put("actionInfo", message);
        this.status = status;
        this.transaction = StaticVariables.uuidGeneration();
        this.executionContent.putAll(content);
        this.endTime = LocalDateTime.now(ZoneOffset.UTC).toString();
        return this;
    }

    public ActionResult standard(Status status, String message) {
        this.executionContent.put("actionInfo", message);
        this.status = status;
        this.transaction = StaticVariables.uuidGeneration();
        this.endTime = LocalDateTime.now(ZoneOffset.UTC).toString();
        return this;
    }

    @Override
    public String toString() {
        return "Result {" +
                "executionContent=" + executionContent +
                ", status=" + status +
                ", transaction='" + transaction + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

}
