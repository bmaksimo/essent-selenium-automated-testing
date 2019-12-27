package com.essent.testing.dwp.pageobject.impl.quote;

import static com.essent.automation.autocrat.Action.SELECT;
import static com.essent.automation.autocrat.Action.TYPING;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

import com.billinghouse.testautomation.util.gherkin.DateTimeFormatUtil;
import com.essent.automation.autocrat.Model;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.tables.CustomerDetails;

public class PersonalDetailsPage extends QuoteCreationGuidedStep {

  private static final String SALUTATION_FEMALE = "Mevr.";

  private CustomerDetails customer;

  public PersonalDetailsPage(CustomerDetails customer) {
    setCustomerDetails(customer);
  }

  private void setCustomerDetails(CustomerDetails customer) {
    this.customer = customer;
  }

  @Override
  public boolean fillInFormData() {
    return fillInCustomerDetails();
  }

  private boolean fillInCustomerDetails() {
    String firstName = StringUtils.capitalize(customer.getFirstName());
    String lastName = StringUtils.capitalize(customer.getLastName());
    String birthDate = DateTimeFormatUtil.getBirthDate(customer.getBirthDate());
    String mobilePhone = "+3168" + (int) (Math.floor(Math.random() * 9000000) + 1000000);

    Model.Execution initializeFields = createExecution();
    initializeFields
        .element(SALUTATION.element())
        .element(FIRST_NAME.element())
        .element(BIRTHDAY.element())
        .element(LAST_NAME.element())
        .element(EMAIL.element())
        .element(MOBILE_NR.element());

    seleniumDriver.waitForRequestsToFinish();
    initializeFields.step(
        createStep(SELECT).element(SALUTATION.name()).value(SALUTATION_FEMALE).timeoutInSeconds(10),
        INPUT.getSleepInMillis());
    seleniumDriver.waitForRequestsToFinish();
    initializeFields.step(
        createStep(TYPING).element(FIRST_NAME.name()).value(firstName).timeoutInSeconds(10),
        INPUT.getSleepInMillis());
    seleniumDriver.waitForRequestsToFinish();
    initializeFields.step(
        createStep(TYPING).element(LAST_NAME.name()).value(lastName).timeoutInSeconds(15),
        INPUT.getSleepInMillis());
    seleniumDriver.waitForRequestsToFinish();
    initializeFields.step(
        createStep(TYPING).element(EMAIL.name()).value(customer.getEmail()).timeoutInSeconds(10),
        INPUT.getSleepInMillis());
    seleniumDriver.waitForRequestsToFinish();
    initializeFields.step(
        createStep(TYPING).element(MOBILE_NR.name()).value(mobilePhone).timeoutInSeconds(10),
        INPUT.getSleepInMillis());
    seleniumDriver.waitForRequestsToFinish();
    initializeFields.step(
        createStep(TYPING).element(BIRTHDAY.name()).value(birthDate).timeoutInSeconds(10),
        INPUT.getSleepInMillis());
    seleniumDriver.waitForRequestsToFinish();

    return execute(initializeFields);
  }
}
