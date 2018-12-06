package stepdefinitions.jbilling.Page;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.jbilling.pageobject.impl.page.BillingProcessPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class JBillingProcessSteps extends DwpScenario {

    String invoiceDate;

    @Before("@B2B,@JBILLING @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@B2B,@JBILLING @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
    @And("^Choose on time billing process$")
    public void chooseOnTimeBillingProcess() throws Throwable {
        BillingProcessPage bp = new BillingProcessPage(webDriver);
        bp.onTimeBillingProcess();
    }

    @And("^Select edit billing proces$")
    public void selectOnConfigurationPage() throws Throwable {
        BillingProcessPage bp = new BillingProcessPage(webDriver);
        bp.editButton();
    }

    @And("^Invoice date is \"([^\"]*)\" days ago$")
    public void invoiceDateIsDaysAgo(String day) throws Throwable {
        BillingProcessPage bp = new BillingProcessPage(webDriver);
        invoiceDate= bp.getInvoiceDate();
        bp.invoiceDateInPast(Integer.parseInt(day));
        bp.invoiceDateDatapicker();
    }

    @And("^Save billing proces$")
    public void saveBillingProces() throws Throwable {
        BillingProcessPage bp = new BillingProcessPage(webDriver);
        bp.saveProccessBilling();
    }
    @And("^Cancel billing proces$")
    public void cancelBillingProces() throws Throwable {
        BillingProcessPage bp = new BillingProcessPage(webDriver);
        bp.cancelProccessBilling();
    }

    @Then("^Error message is displayed$")
    public void errorMessageIsDisplayed() throws Throwable {
        BillingProcessPage bp = new BillingProcessPage(webDriver);
        String msg = "The Billing Configuration has an error in the invoice Date field: The date entered is not valid.";
        Assert.assertEquals(msg,bp.errorMsg());
    }

    @And("^Date in not changed$")
    public void dateInNotChanged() throws Throwable {
        BillingProcessPage bp = new BillingProcessPage(webDriver);
        String date = bp.getInvoiceDate();
        Assert.assertEquals(date, invoiceDate);
    }
}
