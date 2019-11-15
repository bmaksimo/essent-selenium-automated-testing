package stepdefinitions.dwp.pageobject;

import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service.ServicePage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.junit.Assert;

public class ServiceStep extends DwpScenario {

    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Interaction is created with Type \"([^\"]*)\" and Onderwerp \"([^\"]*)\"$")
    public void interactionIsCreatedWithTypeAndOnderwerpAndVerwanteCaseIs(String type, String onderwerp) {
        ServicePage sp = new ServicePage();
        Assert.assertTrue("Actual Interaction Type differs from expected",sp.getInteractionType(type).equalsIgnoreCase(type));
        Assert.assertTrue("Actual Interaction Onderwerp differs from expected",sp.getInteractionOnderwerp(type).equalsIgnoreCase(onderwerp));
    }

    @And("^Go to prospect$")
    public void goToProspect() {
        seleniumDriver.waitForRequestsToFinish();
        ServicePage servicePage = new ServicePage();
        servicePage.goToProspect();
    }

    @And("^Go to GLN account$")
    public void goToGLNAccount() {
        ServicePage servicePage = new ServicePage();
        servicePage.gotoGLNAccount();
    }
}
