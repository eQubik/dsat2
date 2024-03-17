package com.equbik.framework.models.element_model;

import lombok.*;

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
public class Element {

    /*
     * Element class represents element described in Adapter implementation(csv file or any other data structure)
     */

    private Integer id;
    private String scenario;
    private String step;
    private String name;
    private String marker;
    private Action actionType;
    private String code;
    private String value;
    private String relatedElement;

    @Getter
    @Setter
    @AllArgsConstructor
    @Data
    public static class Action{

        private String actionName;
        private int actionType;

    }

}
