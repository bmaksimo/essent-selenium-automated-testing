package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.service_contracting.LeadPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

public class LeadSteps extends DwpScenario {

    @Before("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void tearDown(Scenario scenario) throws Exception {
        super.tearDown(scenario);
    }

    @When("^Add lead$")
    public void addLead() throws Throwable {
        LeadPage leadPage = new LeadPage(webDriver);
        leadPage.plusAddLead();
    }

    @And("^New lead is$")
    public void insertCompanyNameForCreatingLead(DataTable dbTabel) throws Throwable {
        LeadPage leadPage = new LeadPage(webDriver);
        List<List<String>> db = dbTabel.raw();

        leadPage.createLead(db);
    }

    @Then("^\"([^\"]*)\" lead was created$")
    public void leadWasCreated(String name) throws Throwable {
        LeadPage leadPage = new LeadPage(webDriver);
        leadPage.validateCreatingLead(name);
    }
}
