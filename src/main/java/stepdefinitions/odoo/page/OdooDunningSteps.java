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

    @Then("^Dunning Instance State is \"([^\"]*)\"$$")
    public void dunningInstanceStatus(String state){
        String message = String.format("Dunning instance state is not \"%s\"", state);
        Assert.assertThat(message, new OdooDunningPages().getDunningInstanceState().getText(), equalTo(state));
    }

    @Then("^Dunning invoice number is same as \"([^\"]*)\"$")
    public void dunningInvoiceNumber(String invoiceNumber){
        String dunningDWPInvoiceNumber = parameterProvider.getValueOrParameterAsString(invoiceNumber);
        String message = String.format("Dunning invoice number is not \"%s\"", dunningDWPInvoiceNumber);
        String dunningOdooInvoiceNumber = new OdooDunningPages().getDunningInvoiceNumber().getText();

        Assert.assertThat(message, dunningOdooInvoiceNumber.substring(0, dunningOdooInvoiceNumber.indexOf(" (")), equalTo(dunningDWPInvoiceNumber));
    }

    @Override
    @After("@ODOO or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
