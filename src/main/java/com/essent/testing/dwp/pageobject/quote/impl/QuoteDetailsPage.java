package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import stepdefinitions.dwp.tables.SalesChannel;

import static com.essent.testing.dwp.DwpTimingParameters.INPUT;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.SALES_CHANNEL_FIELD;

public class QuoteDetailsPage extends CreateQuoteGuidedStep {

    public QuoteDetailsPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
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
        Model.Execution toggleReguCheckbox = newExecution();
        toggleReguCheckbox.
            element(SALES_CHANNEL_FIELD.element()).
            step(createStep(Action.SELECT).element(SALES_CHANNEL_FIELD.name()).value(salesChannel.getLabel()), INPUT.getSleepInMillis());
            return execute(toggleReguCheckbox);
    }
}
