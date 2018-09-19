package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.service_contracting.LeadPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class LeadSteps extends DwpScenario {
    LeadPage leadPage = new LeadPage(webDriver);

    @Before("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @When("^Add lead$")
    public void addLead() throws Throwable {
        leadPage.plusAddLead();
    }

    @And("^Insert company name \"([^\"]*)\" for creating lead$")
    public void insertCompanyNameForCreatingLead(String companyName) throws Throwable {
        leadPage.createLead(companyName);
    }
}
