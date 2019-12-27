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

  @Before("@JBILLING or @B2B or @REGRESSION")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @When("^Invoice table is not empty$")
  public void checkInvoiceTableNotEmpty() {
    InvoicesPage invoicesPage = new InvoicesPage();
    boolean success = invoicesPage.checkInvoiceTableNotEmpty();

    assertThat("Rows in invoice table are empty", success, is(true));
  }

  @Override
  @After("@JBILLING or @B2B or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
