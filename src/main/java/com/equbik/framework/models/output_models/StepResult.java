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
public class StepResult {

    /*
     * StepResult class represents the result of the execution of each element of the step
     */

    private String stepName;
    private LinkedList<ActionResult> actionResultsList;

}
