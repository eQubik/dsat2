package com.equbik.framework.behavior.selenium.advanced;

import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.services.Executions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class AWebElement {

    /*
     * AWebElement class ...
     */

    private final GlobalHighlight globalHighlight;
    private final GlobalSleep globalSleep;

    public AWebElement(Environment environment){
        Environment.Highlight highlight = environment.getExecutor().get(Executions.selenium.toString()).getHighlight();
        Environment.Sleep sleep = environment.getExecutor().get(Executions.selenium.toString()).getSleep();
        this.globalHighlight = (highlight != null) ? new GlobalHighlight(highlight) : new GlobalHighlight();
        this.globalSleep = (sleep != null) ? new GlobalSleep(sleep) : new GlobalSleep();
    }

    public WebElement getElement(WebDriver driver, WebElement element) {
        globalHighlight.highlightElement(driver, element);
        globalSleep.threadSleep();
        return element;
    }

}
