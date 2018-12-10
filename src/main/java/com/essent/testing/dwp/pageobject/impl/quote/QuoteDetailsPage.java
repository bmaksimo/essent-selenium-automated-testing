package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import stepdefinitions.dwp.tables.LegalForm;
import stepdefinitions.dwp.tables.SalesChannel;

import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.SALES_CHANNEL_FIELD;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

public class QuoteDetailsPage extends QuoteCreationGuidedStep {

    public QuoteDetailsPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    private boolean      regularisation;

    private SalesChannel salesChannel;
    private LegalForm legalForm;

    public void setRegularisation(boolean regularisation) {
        this.regularisation = regularisation;
    }

    public void setSalesChannel(SalesChannel salesChannel) {
        this.salesChannel = salesChannel;
    }

    public void setLegalForm(LegalForm legalForm) {
        this.legalForm = legalForm;
    }

    @Override
    public boolean fillInFormData() {
        Model.Execution toggleReguCheckbox = createExecution();
        toggleReguCheckbox.
            element(SALES_CHANNEL_FIELD.element()).
            step(createStep(Action.SELECT).element(SALES_CHANNEL_FIELD.name()).value(salesChannel.getLabel()), INPUT.getSleepInMillis());
        return execute(toggleReguCheckbox);
    }




    public void SaveInitialQuote()
    {
        waitForRequestsToFinish();
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//*[@id=\"primaryButton\"]")));
    }



}
