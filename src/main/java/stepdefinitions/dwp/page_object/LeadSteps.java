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
import stepdefinitions.dwp.tables.LeadInfo;

import java.util.List;

public class LeadSteps extends DwpScenario {

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
        LeadPage leadPage = new LeadPage(webDriver);
        leadPage.plusAddLead();
    }

    @And("^New lead is$")
    public void insertCompanyNameForCreatingLead(DataTable lead) throws Throwable {
        List<List<String>> db = lead.raw();
        LeadPage leadPage = new LeadPage(webDriver);
        leadPage.createLead(db);

        //Below is Gherkin standard
        List<LeadInfo> list = lead.asList(LeadInfo.class);
        leadPage.setLead(list.get(0));
        //below is page object alternative if your test needs to analyse in between info if the execution failed
        //leadPage.fillInFormData();
        //leadPage.saveLead();
    }

    @Then("^\"([^\"]*)\" lead was created$")
    public void leadWasCreated(String name) throws Throwable {
        LeadPage leadPage = new LeadPage(webDriver);
        leadPage.validateCreatingLead(name);
    }
}
