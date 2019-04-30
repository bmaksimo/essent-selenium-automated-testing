package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import stepdefinitions.dwp.tables.SalesChannel;

import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.SALES_CHANNEL_FIELD;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

public class QuoteDetailsPage extends QuoteCreationGuidedStep {

  private boolean regularisation;

  private SalesChannel salesChannel;

  public void setRegularisation(boolean regularisation) {
    this.regularisation = regularisation;
  }

  public void setSalesChannel(SalesChannel salesChannel) {
    this.salesChannel = salesChannel;
  }

  @Override
  public boolean fillInFormData() {
    Model.Execution toggleReguCheckbox = createExecution();
    toggleReguCheckbox
        .element(SALES_CHANNEL_FIELD.element())
        .step(
            createStep(Action.SELECT)
                .element(SALES_CHANNEL_FIELD.name())
                .value(salesChannel.getLabel()),
            INPUT.getSleepInMillis());
    seleniumDriver.takeScreenshot("quoteDetails");
    return execute(toggleReguCheckbox);
  }
}
