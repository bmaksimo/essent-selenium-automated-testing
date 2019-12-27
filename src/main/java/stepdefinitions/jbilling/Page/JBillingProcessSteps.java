package stepdefinitions.jbilling.Page;

import com.essent.testing.jbilling.pageobject.impl.page.BillingProcessPage;
import com.essent.testing.jbilling.scenario.JBillingScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class JBillingProcessSteps extends JBillingScenario {

  private String invoiceDate;

  @Before("@JBILLING or @B2B or @REGRESSION")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @Override
  @After("@JBILLING or @B2B or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }

  @And("^Choose on time billing process$")
  public void chooseOnTimeBillingProcess() {
    BillingProcessPage bp = new BillingProcessPage();
    bp.onTimeBillingProcess();
  }

  @And("^Select edit billing proces$")
  public void selectOnConfigurationPage() {
    BillingProcessPage bp = new BillingProcessPage();
    bp.editButton();
  }

  @And("^Invoice date is \"([^\"]*)\" days ago$")
  public void invoiceDateIsDaysAgo(String day) {
    BillingProcessPage bp = new BillingProcessPage();
    invoiceDate = bp.getInvoiceDate();
    bp.invoiceDateInPast(Integer.parseInt(day));
    bp.invoiceDateDatapicker();
  }

  @And("^Save billing proces$")
  public void saveBillingProces() {
    BillingProcessPage bp = new BillingProcessPage();
    bp.saveProccessBilling();
  }

  @And("^Cancel billing proces$")
  public void cancelBillingProces() {
    BillingProcessPage bp = new BillingProcessPage();
    bp.cancelProccessBilling();
  }

  @Then("^Error message is displayed$")
  public void errorMessageIsDisplayed() {
    BillingProcessPage bp = new BillingProcessPage();
    String msg =
        "The Billing Configuration has an error in the invoice Date field: The date entered is not valid.";
    Assert.assertTrue(bp.errorMsg().equalsIgnoreCase(msg));
  }

  @And("^Date in not changed$")
  public void dateInNotChanged() {
    BillingProcessPage bp = new BillingProcessPage();
    String date = bp.getInvoiceDate();
    Assert.assertTrue(date.equalsIgnoreCase(invoiceDate));
  }
}
