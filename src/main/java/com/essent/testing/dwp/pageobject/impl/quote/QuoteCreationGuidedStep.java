package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.Form;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.quote.GuidedStep;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.NEXT_STEP;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.selector.CommonSelectors.VIEW;

public abstract class QuoteCreationGuidedStep extends Component implements GuidedStep, Form {


    public QuoteCreationGuidedStep(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
    }

    public QuoteCreationGuidedStep(SeleniumDriver seleniumDriver) {
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
