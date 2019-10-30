package stepdefinitions.odoo.page;

import com.essent.testing.odoo.pageobject.impl.page.OdooDunningPages;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.junit.Assert;

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

    @Override
    @After("@ODOO or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
