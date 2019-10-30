package stepdefinitions.odoo.page;

import com.essent.testing.odoo.pageobject.impl.page.OdooDunningPages;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import static org.hamcrest.Matchers.equalTo;

public class OdooDunningSteps extends OdooScenario {
    @Before("@ODOO or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @Then("^Dunning bundle is present$")
    public void dunningBundleIsPresent(){
        OdooDunningPages odp = new OdooDunningPages();
        Assert.assertTrue("Dunning bundle is not pressent", odp.getBundleIdElement().isDisplayed());
    }

    @Then("^Dunning Instance Status is \"([^\"]*)\"$$")
    public void dunningInstanceStatus(String status){
        String message = String.format("Dunning instance status is not \"%s\"", status);
        Assert.assertThat(message, new OdooDunningPages().getDunningInstanceStatus(), equalTo(status));
    }

    @Then("^Dunning invoice number is same as \"([^\"]*)\"$")
    public void dunningInvoiceNumber(String invoiceNumber){
        String dunningInvoiceNumber = parameterProvider.getValueOrParameterAsString(invoiceNumber);
        String message = String.format("Dunning invoice number is not \"%s\"", dunningInvoiceNumber);
        Assert.assertThat(message, new OdooDunningPages().getDunningInvoiceNumber(), equalTo(dunningInvoiceNumber));
    }

    @Override
    @After("@ODOO or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
