package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import stepdefinitions.dwp.tables.SalesChannel;

import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.SALES_CHANNEL_FIELD;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

public class QuoteDetailsPage extends CreateQuoteGuidedStep {

    public QuoteDetailsPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    private boolean      regularisation;

    private SalesChannel salesChannel;

    public boolean isRegularisation() {
        return regularisation;
    }

    public void setRegularisation(boolean regularisation) {
        this.regularisation = regularisation;
    }

    public SalesChannel getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(SalesChannel salesChannel) {
        this.salesChannel = salesChannel;
    }
    @Override
    public boolean fillInFormData() {
        Model.Execution toggleReguCheckbox = createExecutuin();
        toggleReguCheckbox.
            element(SALES_CHANNEL_FIELD.element()).
            step(createStep(Action.SELECT).element(SALES_CHANNEL_FIELD.name()).value(salesChannel.getLabel()), INPUT.getSleepInMillis());
        return execute(toggleReguCheckbox);
    }
}
