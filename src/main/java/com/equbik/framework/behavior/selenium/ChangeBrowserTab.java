package com.equbik.framework.behavior.selenium;

import com.equbik.framework.behavior.TakeAction;
import com.equbik.framework.behavior.selenium.advanced.AWebElement;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.executions.SeleniumBrowser;
import com.equbik.framework.models.artifact_model.Results;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.Status;
import com.equbik.framework.services.StatusMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ChangeBrowserTab implements TakeAction {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Element element;
    private final AWebElement aWebElement;
    private final Map<String, String> additionalMessage = new HashMap<>();

    public ChangeBrowserTab(Execution execution, Element element, AWebElement advancedElement) {
        SeleniumBrowser browser = (SeleniumBrowser) execution;
        this.driver = browser.getDriver();
        this.wait = browser.getWait();
        this.element = element;
        this.aWebElement = advancedElement;
    }

    @Override
    public Results takeAction() {
        Results result = new Results();
        StatusMessage statusMessage;
        try {
            Set<String> windows = driver.getWindowHandles();
            for(String window : windows){
                String previousWindow = StaticVariables.tabs.stream().findFirst().orElse(null);
                if (!window.equals(previousWindow)){
                    driver.switchTo().window(window);
                }
            }
            statusMessage = new StatusMessage(Status.Success, element, this.getClass().getSimpleName());
            return result.additional(Status.Success, statusMessage.getStatusMessage().trim(), content());
        } catch (Exception e) {
            additionalMessage.put("error", e.getMessage());
            statusMessage = new StatusMessage(Status.Failed, element, this.getClass().getSimpleName(), additionalMessage);
            return result.additional(Status.Failed, statusMessage.getStatusMessage().trim(), content());
        }
    }

    private Map<String, String> content() {
        return StaticVariables.driverContent(driver);
    }

}
