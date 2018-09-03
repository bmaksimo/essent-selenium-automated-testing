package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import stepdefinitions.dwp.tables.BillingInformation;

import static com.essent.testing.dwp.autocrat.element.quote.BillingElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;
public class BillingDetailsPage extends QuoteCreationGuidedStep {

    private BillingInformation billingInformation;

    public void setBillingInformation(BillingInformation billingInformation) {
        this.billingInformation = billingInformation;
    }
    public BillingDetailsPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }
    @Override
    public boolean fillInFormData() {
        Model.Execution execution = createExecutuin();
        String paymentMethod = billingInformation.getPaymentMethod();
        String eban = billingInformation.getEban();
        String bic = billingInformation.getBic();
        execution.
        element(PAYMENT_METHOD.element()).
            element(PAYMENT_IBAN.element()).
            element(PAYMENT_BIC.element()).
            step(createStep(Action.SELECT).element(PAYMENT_METHOD.name()).value(paymentMethod)).
            step(createStep(Action.TYPING).element(PAYMENT_IBAN.name()).value(eban), INPUT.getSleepInMillis()).
            step(createStep(Action.TYPING).element(PAYMENT_BIC.name()).value(bic), INPUT.getSleepInMillis());
        return execute(execution);
    }
}
