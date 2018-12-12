package stepdefinitions.jbilling.filter;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.jbilling.pageobject.impl.filter.FilterPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class FilterElements extends DwpScenario {

	@Before("@JBILLING, @B2B, @REGRESSION")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	@When("JBilling \"([^\"]*)\" input is \"([^\"]*)\"$")
	public void setInput(String label, String value) throws Throwable {
		//String inputValue = parameterProvider.getValueOrParameterAsString(value);

		FilterPage filterPage = new FilterPage(webDriver);
		filterPage.filterBy(label, value);
	}
	
	@When("JBilling Click on \"([^\"]*)\"$")
	public void clickApplyFilters(String label) throws Throwable {
		FilterPage filterPage = new FilterPage(webDriver);
		filterPage.clickApplyFilters(label);
	}

	@Override
	@After("@JBILLING, @B2B, @REGRESSION")
	public void tearDown() {
		super.tearDown();
	}

}
