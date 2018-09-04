package stepdefinitions.dwp.pgo;

import static com.essent.testing.dwp.DwpConstant.BASE_URL;


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


    @When("^pgo New case for customer is logged$")
    public void logAcaseForCustomer() throws Throwable {
        System.out.println("---HHH1 logAcaseForCustomer");
        //DwpHomePage dhp = new DwpHomePage(seleniumDriver);
        DwpAccountOverviewPage daop = new DwpAccountOverviewPage(webDriver);
		DwpServicePage dsp = new DwpServicePage(webDriver);
//		dhp.clickOnsalesMarketingLink();
//		dhp.clickOnAccountsListLink();
//		dhp.clickOnFilterButton();
//		dhp.searchByAccountNumberFieldClearAndClick();
//		String accountId = DwpPropertiesHelper.getEssentDwpProperty("AccountNumberB2BClient");
//		dhp.enterAccountId(accountId);
//		Assert.assertTrue(dhp.accountWithAppropriateId(accountId).isDisplayed());
//		dhp.clickOnAccountWithAppropriateId(accountId);
//		daop.clickOnplusIcon();
//		daop.clickOnServiceDropdownMenu();
//		daop.clickOnLogAcaseForAccountOption();
//		String complaintText = DwpPropertiesHelper.getEssentDwpProperty("TextForComplaint");
//		String solutionText = DwpPropertiesHelper.getEssentDwpProperty("TextForSolution");

        String complaintText = "TextForComplaint";
        String solutionText = "TextForSolution";
        daop.setAllForNewCaseForCustomer(complaintText, solutionText);
		dsp.clickOnFirstCaseInTheList();
		Assert.assertTrue(dsp.caseDetailsheader().isDisplayed());

    }

    @Then("^pgo Case details are visible when case is opened$")
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
