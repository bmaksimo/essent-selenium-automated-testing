package com.essent.testing.dwp.pageobject.impl.quote;

import com.billinghouse.random.RandomUser;
import com.essent.automation.autocrat.Model;

import static com.essent.automation.autocrat.Action.TYPING;
import static com.essent.testing.datagenerator.phone.PhoneNumberGenerator.getMobilePhone;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.EMAIL;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.MOBILE_NR;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

public class ContactDetailsPage extends QuoteCreationGuidedStep {

  private RandomUser customer;

  public void setRandomUser(RandomUser randomUser) {
    this.customer = randomUser;
  }

  @Override
  public boolean fillInFormData() {
    return fillInContactDetails();
  }

  private boolean fillInContactDetails() {
    String mobilePhone = getMobilePhone();
    Model.Execution initializeFields = createExecution();
    initializeFields.element(EMAIL.element()).element(MOBILE_NR.element());
    initializeFields.step(createStep(TYPING).element(EMAIL.name()).value(customer.getEmail()).timeoutInSeconds(4), INPUT.getSleepInMillis());
    initializeFields.step(createStep(TYPING).element(MOBILE_NR.name()).value(mobilePhone).timeoutInSeconds(4), INPUT.getSleepInMillis());

    return execute(initializeFields);
  }
}
