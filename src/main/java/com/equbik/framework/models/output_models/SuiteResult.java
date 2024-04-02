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
public class SuiteResult {

    /*
     * SuiteResult class represents the result of the execution of each scenario in the suite
     */

    private String suiteName;
    private LinkedList<ScenarioResult> scenarioResultsList;

}