package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service.ServicePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class ServiceStep extends DwpScenario {

    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Then("^\"([^\"]*)\" is created$")
    public void isCreated(String input){
        ServicePage servicePage = new ServicePage();
        servicePage.validateCreatedTask(input);
    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Interaction is created with Type \"([^\"]*)\" and Onderwerp \"([^\"]*)\"$")
    public void interactionIsCreatedWithTypeAndOnderwerpAndVerwanteCaseIs(String type, String onderwerp) {
        ServicePage sp = new ServicePage();
        Assert.assertEquals("Actual Interaction Type differs from expected",sp.getInteractionType(type),type);
        Assert.assertEquals("Actual Interaction Onderwerp differs from expected",sp.getInteractionOnderwerp(type),onderwerp);
    }

    @And("^Go to prospect$")
    public void goToProspect() {
        ServicePage servicePage = new ServicePage();
        servicePage.goToProspect();
    }

    @And("^Go to GLN account$")
    public void goToGLNAccount() {
        ServicePage servicePage = new ServicePage();
        servicePage.gotoGLNAccount();
    }
}
