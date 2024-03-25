package com.equbik.framework.behavior.selenium.advanced;

import com.equbik.framework.models.json_model.Environment;
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

    public AWebElement(Environment.Highlight highlight, Environment.Sleep sleep) {
        this.globalHighlight = (highlight != null) ? new GlobalHighlight(highlight) : new GlobalHighlight();
        this.globalSleep = (sleep != null) ? new GlobalSleep(sleep) : new GlobalSleep();
    }

    public WebElement getElement(WebDriver driver, WebElement element) {
        globalHighlight.highlightElement(driver, element);
        globalSleep.threadSleep();
        return element;
    }

}
