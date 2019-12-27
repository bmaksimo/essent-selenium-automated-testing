package com.essent.testing.dwp.pageobject.impl.quote;

import static com.essent.automation.autocrat.Action.TYPING;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.EMAIL;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.MOBILE_NR;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

import com.billinghouse.testautomation.util.random.CustomerRandomDataGenerator;
import com.essent.automation.autocrat.Model;
import stepdefinitions.dwp.tables.CustomerDetails;

public class ContactDetailsPage extends QuoteCreationGuidedStep {

  private CustomerDetails customer;

  public ContactDetailsPage(CustomerDetails customer) {
    setCustomer(customer);
  }

  public void setCustomer(CustomerDetails customer) {
    this.customer = customer;
  }

  @Override
  public boolean fillInFormData() {
    return fillInContactDetails();
  }

  private boolean fillInContactDetails() {
    String mobilePhone = CustomerRandomDataGenerator.getMobilePhone();
    Model.Execution initializeFields = createExecution();
    initializeFields.element(EMAIL.element()).element(MOBILE_NR.element());
    seleniumDriver.waitForRequestsToFinish();
    initializeFields.step(
        createStep(TYPING).element(EMAIL.name()).value(customer.getEmail()).timeoutInSeconds(4),
        INPUT.getSleepInMillis());
    seleniumDriver.waitForRequestsToFinish();
    initializeFields.step(
        createStep(TYPING).element(MOBILE_NR.name()).value(mobilePhone).timeoutInSeconds(4),
        INPUT.getSleepInMillis());
    seleniumDriver.waitForRequestsToFinish();

    return execute(initializeFields);
  }
}
