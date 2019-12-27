package stepdefinitions.jbilling.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.essent.testing.jbilling.pageobject.impl.filter.FilterPage;
import com.essent.testing.jbilling.scenario.JBillingScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class FilterElements extends JBillingScenario {

  @Before("@JBILLING or @B2B or @REGRESSION")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @When("^JBilling \"([^\"]*)\" input is \"([^\"]*)\"$")
  public void setInput(String label, String value) {
    if (value.startsWith("parameter:")) {
      value = parameterProvider.getValueOrParameterAsString(value);
    }

    FilterPage filterPage = new FilterPage();
    boolean success = filterPage.filterBy(label, value);

    assertThat(
        "Filter by: " + label + " with input value: " + value + " is not confirmed",
        success,
        is(true));
  }

  @When("^JBilling Click on \"([^\"]*)\" filter button$")
  public void clickFilterButton(String label) {
    FilterPage filterPage = new FilterPage();
    boolean success = filterPage.clickFilterButton(label);

    assertThat("Button: " + label + " is not clicked", success, is(true));
  }

  @Override
  @After("@JBILLING or @B2B or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
