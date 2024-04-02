package com.equbik.framework.behavior;

import com.equbik.framework.models.output_models.ActionResult;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public interface TakeAction {

    /*
     * TakeAction interface should be implemented by those classes that are being used as an Action
     */

    ActionResult takeAction();

}