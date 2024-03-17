package com.equbik.framework.models.json_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

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
public class Scenario {

    /*
     * Scenario class represents JSON scenario
     */

    private String description;
    @JsonProperty("flow_name")
    private String flowName;
    private Environment environment;
    private List<Step> steps;

}
