package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.guided_flow.lead_create.NewLeadPage;
import com.essent.testing.dwp.pageobject.sales_marketing.leads.LeadsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import io.cucumber.datatable.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

public class LeadSteps extends DwpScenario {

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @When("^Add lead$")
    public void addLead() {
        seleniumDriver.waitForRequestsToFinish();
        LeadsPage leadPage = new LeadsPage();
        leadPage.plusAddLead();
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^New lead is$")
    public void insertCompanyNameForCreatingLead(DataTable dbTabel) {
        seleniumDriver.waitForRequestsToFinish();
        NewLeadPage leadPage = new NewLeadPage();
        List<List<String>> db = dbTabel.asLists();
        leadPage.createLead(db);
        seleniumDriver.waitForRequestsToFinish();
    }

    @Then("^\"([^\"]*)\" lead was created$")
    public void leadWasCreated(String name) {
        seleniumDriver.waitForRequestsToFinish();
        LeadsPage leadPage = new LeadsPage();
        leadPage.validateCreatingLead(name);
        seleniumDriver.waitForRequestsToFinish();
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
