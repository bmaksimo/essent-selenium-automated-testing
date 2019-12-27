package com.essent.testing.dwp.pageobject.impl.quote;

import static com.billinghouse.testautomation.util.dsl.NumericUtil.amountAsInt;
import static com.essent.testing.dwp.autocrat.element.quote.BillingElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.dwp.pageobject.impl.elements.NonEditableImpl;
import java.util.Locale;
import stepdefinitions.dwp.tables.BillingInformation;
import stepdefinitions.dwp.tables.FieldDescriptor;

public class BillingDetailsPage extends QuoteCreationGuidedStep {

  private BillingInformation billingInformation;

  private NonEditable advancePaymentField;

  public void setBillingInformation(BillingInformation billingInformation) {
    this.billingInformation = billingInformation;
  }

  public BillingDetailsPage() {
    this.advancePaymentField = new NonEditableImpl();
  }

  public Integer getElectricityAdvancedPaymentAmount(FieldDescriptor descriptor) {
    String amount =
        advancePaymentField.getValue(descriptor.getCardName(), descriptor.getFieldName());
    return amountAsInt(amount, new Locale("nl", "BE"));
  }

  public Integer getGasAdvancedPaymentAmount(FieldDescriptor descriptor) {
    return getElectricityAdvancedPaymentAmount(descriptor);
  }

  @Override
  public boolean fillInFormData() {
    Model.Execution execution = createExecution();
    String paymentMethod = billingInformation.getPaymentMethod();
    String eban = billingInformation.getEban();
    String bic = billingInformation.getBic();
    execution
        .element(PAYMENT_METHOD.element())
        .element(PAYMENT_IBAN.element())
        .element(PAYMENT_BIC.element())
        .step(createStep(Action.SELECT).element(PAYMENT_METHOD.name()).value(paymentMethod))
        .step(
            createStep(Action.TYPING).element(PAYMENT_IBAN.name()).value(eban),
            INPUT.getSleepInMillis())
        .step(
            createStep(Action.TYPING).element(PAYMENT_BIC.name()).value(bic),
            INPUT.getSleepInMillis());
    return execute(execution);
  }
}
