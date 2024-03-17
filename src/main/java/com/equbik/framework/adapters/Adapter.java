package com.equbik.framework.adapters;

import com.equbik.framework.models.element_model.Element;

import java.util.List;
import java.util.Map;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public interface Adapter {

    /*
     * Adapter interface should be implemented by those classes that are being used as an Adapter
     * of your Data Structure used to store the elements
     */

    Map<String, List<Element>> stepElements();

}
