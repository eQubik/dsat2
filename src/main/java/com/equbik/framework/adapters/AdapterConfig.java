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
     * Adapter interface should be implemented by those classes that are being used as an Adapter
     * of your Data Structure used to store the elements
     */

    List<Element> elementsList();

}
