package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import stepdefinitions.dwp.tables.SalesChannel;

import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.SALES_CHANNEL_FIELD;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

public class PerformCustomerAcceptanceChecksPage extends QuoteCreationGuidedStep {


    @Override
    public boolean fillInFormData() {
       seleniumDriver.takeScreenshot("customerAcceptanceChecks");
        return true;
    }

}
