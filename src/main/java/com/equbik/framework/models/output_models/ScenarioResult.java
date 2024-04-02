package com.equbik.framework.models.output_models;

import lombok.*;

import java.util.LinkedList;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScenarioResult {

    /*
     * ScenarioResult class represents the result of the execution of each step of the scenario
     */

    private String scenarioName;
    private LinkedList<StepResult> stepResultsList;

}
