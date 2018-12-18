package stepdefinitions.jbilling.Page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.essent.testing.jbilling.pageobject.impl.page.InvoicesPage;

import com.essent.testing.jbilling.scenario.JBillingScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class Invoices extends JBillingScenario {

	@Before("@JBILLING, @B2B, @REGRESSION")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	@When("Invoice table is not empty$")
	public void checkInvoiceTableNotEmpty() throws Throwable {
		InvoicesPage invoicesPage = new InvoicesPage(webDriver);
		boolean success = invoicesPage.checkInvoiceTableNotEmpty();

		assertThat("Rows in invoice table are empty", success, is(true));
	}

	@Override
	@After("@JBILLING, @B2B, @REGRESSION")
	public void tearDown() {
		super.tearDown();
	}
}
