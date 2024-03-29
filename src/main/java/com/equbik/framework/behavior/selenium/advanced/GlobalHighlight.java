package com.equbik.framework.behavior.selenium.advanced;

import com.equbik.framework.models.input_models.Environment;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class GlobalHighlight {

    /*
     * GlobalHighlight class uses JS executor to adjust custom style to web element
     */

    private static final Logger logger = Logger.getLogger(GlobalHighlight.class.getName());
    private int border;
    private String colorName;

    public GlobalHighlight(Environment.Highlight highlight){
        this.border = highlight.getBorder();
        this.colorName = highlight.getColor();
        logger.info("Global highlight created");
    }

    public GlobalHighlight(){
        logger.info("Global highlight won't be used during execution");
    }

    public void highlightElement(WebDriver driver, WebElement element){
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='" + border + "px solid " + colorName + "'", element);
        } catch (Exception e){
            //Just ignore the problem
            logger.warning("Global highlight can't be added: " + e.getMessage());
        }
    }

}
