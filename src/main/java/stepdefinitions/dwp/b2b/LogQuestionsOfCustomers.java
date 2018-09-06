package stepdefinitions.dwp.b2b;

import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;

import com.essent.testing.dwp.pageobject.b2b_regression.DwpAccountOverviewPage;
import com.essent.testing.dwp.pageobject.b2b_regression.DwpServicePage;

import com.essent.testing.dwp.scenario.DwpScenario;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class LogQuestionsOfCustomers extends DwpScenario {


    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }


    @When("^b2b New case for customer is logged through plus icon on the top right side$")
    public void logAcaseForCustomer() throws Throwable {
        DwpAccountOverviewPage daop = new DwpAccountOverviewPage(webDriver);
		DwpServicePage dsp = new DwpServicePage(webDriver);
		daop.clickOnplusIcon();
		daop.clickOnServiceDropdownMenu();
		daop.clickOnLogAcaseForAccountOption();
        String complaintText = "TextForComplaint";
        String solutionText = "TextForSolution";
        daop.setAllForNewCaseForCustomer(complaintText, solutionText);
        webDriver.waitUntilAngularPageIsLoaded();
		dsp.clickOnFirstCaseInTheList();
        webDriver.waitUntilAngularPageIsLoaded();
		Assert.assertTrue(dsp.caseDetailsheader().isDisplayed());

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



}
