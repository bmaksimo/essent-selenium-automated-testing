package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.guided_flow.lead_create.NewLeadPage;
import com.essent.testing.dwp.pageobject.sales_marketing.leads.LeadsPage;
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

  @Before("@DWP, @REGRESSION")
  public void setupTest(Scenario scenario) {
    registerActiveScenario(scenario);
  }

  @When("^Add lead$")
  public void addLead() {
    LeadsPage leadPage = new LeadsPage();
    leadPage.plusAddLead();
  }

  @And("^New lead is$")
  public void insertCompanyNameForCreatingLead(DataTable dbTabel) {
    NewLeadPage leadPage = new NewLeadPage();
    List<List<String>> db = dbTabel.raw();

    leadPage.createLead(db);
  }

  @Then("^\"([^\"]*)\" lead was created$")
  public void leadWasCreated(String name) {
    LeadsPage leadPage = new LeadsPage();
    leadPage.validateCreatingLead(name);
  }

  @Override
  @After("@DWP, @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
