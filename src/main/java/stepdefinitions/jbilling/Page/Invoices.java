package stepdefinitions.jbilling.Page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.jbilling.pageobject.impl.page.InvoicesPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class Invoices extends DwpScenario {

	@Before("@JBILLING, @B2B, @REGRESSION")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}
	
	@When("Invoices table is not empty$")
	public void checkOrderTableNotEmpty() throws Throwable {
		InvoicesPage invoicesPage = new InvoicesPage(webDriver);
		boolean isNotEmpty = invoicesPage.checkInvoicesTableNotEmpty();
		
		assertThat("Rows in invoices table are empty", isNotEmpty, is(true));
	}

	@Override
	@After("@JBILLING, @B2B, @REGRESSION")
	public void tearDown() {
		super.tearDown();
	}
}
