package com.equbik.framework.models.artifact_model;

import lombok.*;

import java.util.LinkedList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScenarioResult {

    private String scenarioName;
    private LinkedList<StepResult> stepResultsList;

}
