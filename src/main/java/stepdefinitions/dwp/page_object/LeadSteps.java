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

public class LeadSteps extends DwpScenario {

    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @When("Add lead")
    public void addLead() {
        new LeadsPage().plusAddLead();
    }

    @And("^New lead is$")
    public void insertCompanyNameForCreatingLead(DataTable leadDataTable) {
        new NewLeadPage().createLead(leadDataTable.asMaps(String.class, String.class));
    }

    @Then("^\"([^\"]*)\" lead was created$")
    public void leadWasCreated(String name) {
        new LeadsPage().validateCreatingLead(name);
    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
