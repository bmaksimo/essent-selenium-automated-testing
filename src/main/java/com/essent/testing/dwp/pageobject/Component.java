package com.essent.testing.dwp.pageobject;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import com.essent.testing.util.AutocratExecutionAdapter;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.essent.testing.dwp.pageobject.selector.BasicSelectors.SIBLING_OVERLAYING_ICONS;

public abstract  class Component {

    protected static final String APPLICATION_SELECTOR =  "//dwp-app";

    protected WebElement     element;

    protected SeleniumDriver seleniumDriver;

    private final Logger    logger = Logger.getLogger(Component.class);

    protected Logger logger() {
        return logger;
    }

    public Component(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public Component(WebElement element, SeleniumDriver seleniumDriver) {
        logger.info("STEP:");
        logger.info(" - ACTION: LOAD_PAGE_OBJECT");

        if(element == null) {
            logger.error(" - RESULT: FAILED");
            logger.error(" - REASON: " + getClass() + "{null}: Web element was not found. ");
            throw new IllegalArgumentException(getClass() + ": Web element was not found.");
        }

        logger.info(" - RESULT: " + element);
        this.element = element;
        this.seleniumDriver = seleniumDriver;
    }


    protected Model.Execution createExecution() {
        return AutocratExecutionAdapter.newExecution();
    }

    protected Model.Step    createStep(Action action) {
        return new Model.Step().action(action);
    }

    protected boolean execute(final Model.Execution execution) {
        return AutocratExecutionAdapter.execute(seleniumDriver.getDriver(), execution);
    }

    public class HideIconOverlays implements Model.Callback {
        @Override
        public void onAccess(Autocrat.ExecutionContext context, Model.Step step, WebElement value) {
            JavascriptExecutor jsExec = (JavascriptExecutor) context.driver;
            List<WebElement> elements = value.findElements(By.xpath(SIBLING_OVERLAYING_ICONS.getQuery()));
            elements.forEach(siblingIcon -> {
                String setProperty = "style = 'display:none'";
                logger().info("Executing javascript " + setProperty + " on target element");
                jsExec.executeScript("arguments[0]." + setProperty, siblingIcon);
            });
        }
    }
}
