package com.equbik.framework.adapters;

import com.equbik.framework.models.element_model.Element;

import java.util.List;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public interface AdapterConfig {

    /*
     * AdapterConfig interface should be implemented by those classes that will provide the list of all accessible
     * elements in your Data Structure
     */

    List<Element> elementsList();

}
