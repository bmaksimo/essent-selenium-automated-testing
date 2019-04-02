package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service.ServicePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

public class ServiceStep extends DwpScenario {

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Then("^\"([^\"]*)\" is created$")
    public void isCreated(String input){
        ServicePage servicePage = new ServicePage();
        servicePage.validateCreatedTask(input);
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
