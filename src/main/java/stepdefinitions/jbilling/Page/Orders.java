package stepdefinitions.jbilling.Page;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.jbilling.pageobject.impl.page.OrdersPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Orders extends DwpScenario {

	@Before("@JBILLING, @B2B, @REGRESSION")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	@When("Order table is not empty$")
	public void checkOrderTableNotEmpty() throws Throwable {
		OrdersPage ordersPage = new OrdersPage(webDriver);
		boolean success = ordersPage.checkOrderTableNotEmpty();

		assertThat("Rows in order table are empty", success, is(true));
	}

    @When("JBilling Value next to label \"([^\"]*)\" is \"([^\"]*)\" in Inner Table$")
	public void checkValueNextToLabel(String label, String expectedValue) throws Throwable {

		OrdersPage orderPage = new OrdersPage(webDriver);
		String actualResult = orderPage.checkValueNextToLabel(label);
		assertThat("Value " + expectedValue + " is not shown next to label " + label, actualResult.equalsIgnoreCase(expectedValue), is(true));
	}

	@Override
	@After("@JBILLING, @B2B, @REGRESSION")
	public void tearDown() {
		super.tearDown();
	}
}
