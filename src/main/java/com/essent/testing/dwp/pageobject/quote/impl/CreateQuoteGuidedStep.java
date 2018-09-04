package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.quote.Form;
import com.essent.testing.dwp.pageobject.quote.GuidedStep;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.essent.testing.dwp.DwpTimingParameters.NEXT_STEP;
import static com.essent.testing.dwp.pageobject.selector.BasicSelectors.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.selector.BasicSelectors.VIEW;

public abstract class CreateQuoteGuidedStep extends Component implements GuidedStep, Form {


    public CreateQuoteGuidedStep(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
    }

    public CreateQuoteGuidedStep(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW.getQuery())),
            seleniumDriver);
    }

    @Override
    public void next() {
        Model.Execution next = createExecution();
        next.
            element(NEXT_BUTTON.element())
            .step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds())
            .element(NEXT_BUTTON.name()));
        execute(next);
    }
}
