package com.equbik.framework.models.json_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

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
public class Step {

    /*
     * Step class represents each step inside JSON scenario
     */

    @JsonProperty("step_name")
    private String stepName;
    private List<String> elements;
    private Map<String, String> variables;

}
