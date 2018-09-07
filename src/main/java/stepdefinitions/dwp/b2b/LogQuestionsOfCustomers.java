package stepdefinitions.dwp.b2b;

import cucumber.api.java.en.And;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;

import com.essent.testing.dwp.pageobject.impl.Page.DwpAccountOverviewPage;
import com.essent.testing.dwp.pageobject.impl.Page.DwpServicePage;

import com.essent.testing.dwp.scenario.DwpScenario;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class LogQuestionsOfCustomers extends DwpScenario {


    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @Then("^b2b Case details are visible when case is opened$")
    public void checkSuccess() throws Throwable {
        //Thread.sleep(3000);
        DwpAccountOverviewPage daop = new DwpAccountOverviewPage(webDriver);
        String complaintText = "TextForComplaint";
        String solutionText = "TextForSolution";
        Assert.assertTrue(daop.getComplaintText().equalsIgnoreCase(complaintText));
        Assert.assertTrue(daop.getSolutionFieldText().equalsIgnoreCase(solutionText));
        Assert.assertTrue(daop.checkIfPriorityIsHigh());

    }

    @And("^b2b New case for account is created$")
    public void bBNewCaseForAccountIsCreated() throws Throwable {
        DwpAccountOverviewPage daop = new DwpAccountOverviewPage(webDriver);
        DwpServicePage dsp = new DwpServicePage(webDriver);
        String complaintText = "TextForComplaint";
        String solutionText = "TextForSolution";
        daop.setAllForNewCaseForCustomer(complaintText, solutionText);
        webDriver.waitUntilAngularPageIsLoaded();
        dsp.clickOnFirstCaseInTheList();
        webDriver.waitUntilAngularPageIsLoaded();
        Assert.assertTrue(dsp.caseDetailsheader().isDisplayed());
    }

}
