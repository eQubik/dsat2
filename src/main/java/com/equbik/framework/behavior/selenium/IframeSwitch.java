package com.equbik.framework.behavior.selenium;

import com.equbik.framework.behavior.TakeAction;
import com.equbik.framework.behavior.selenium.advanced.AWebElement;
import com.equbik.framework.executions.Execution;
import com.equbik.framework.executions.SeleniumBrowser;
import com.equbik.framework.models.artifact_model.ActionResult;
import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.StaticVariables;
import com.equbik.framework.services.Status;
import com.equbik.framework.services.StatusMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class IframeSwitch implements TakeAction {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Element element;
    private final AWebElement aWebElement;
    private final Map<String, String> additionalMessage = new HashMap<>();

    public IframeSwitch(Execution execution, Element element, AWebElement advancedElement) {
        SeleniumBrowser browser = (SeleniumBrowser) execution;
        this.driver = browser.getDriver();
        this.wait = browser.getWait();
        this.element = element;
        this.aWebElement = advancedElement;
    }

    @Override
    public ActionResult takeAction() {
        ActionResult result = new ActionResult();
        StatusMessage statusMessage;
        try {
            WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element.getMarker())));
            WebElement modifiedWebElement = aWebElement.getElement(driver, webElement);
            driver.switchTo().frame(modifiedWebElement);
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
