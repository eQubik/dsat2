package com.equbik.framework.models.input_models;

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
    private String name;
    private Executor executor;
    private List<Step> steps;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Executor {
        private String name;
        private boolean remote;
    }

}
