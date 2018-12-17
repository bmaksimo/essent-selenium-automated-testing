package stepdefinitions.jbilling.filter;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.jbilling.pageobject.impl.filter.FilterPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FilterElements extends DwpScenario {

	@Before("@JBILLING, @B2B, @REGRESSION")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	@When("JBilling \"([^\"]*)\" input is \"([^\"]*)\"$")
	public void setInput(String label, String value) throws Throwable {
		if(value.startsWith("parameter:")) {
			value = parameterProvider.getValueOrParameterAsString(value);
		}

		FilterPage filterPage = new FilterPage(webDriver);
        boolean success = filterPage.filterBy(label, value);

        assertThat("Filter by: " + label + " with input value: " + value + " is not confirmed", success, is(true));
	}

	@When("JBilling Click on \"([^\"]*)\"$")
	public void clickApplyFilters(String label) throws Throwable {
		FilterPage filterPage = new FilterPage(webDriver);
        boolean success = filterPage.clickApplyFilters(label);

        assertThat("Button: " + label + " is not clicked", success, is(true));
	}

	@Override
	@After("@JBILLING, @B2B, @REGRESSION")
	public void tearDown() {
		super.tearDown();
	}

}
