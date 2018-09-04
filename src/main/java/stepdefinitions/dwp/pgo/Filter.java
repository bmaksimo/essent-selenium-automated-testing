package stepdefinitions.dwp.pgo;

import com.essent.testing.dwp.pageobject.b2b_regression.DwpHomePage;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;


import com.essent.testing.dwp.scenario.DwpScenario;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class Filter extends DwpScenario {

    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }


    @When("^pgo Account is selected$")
    public void pgoAccountIsSelected() throws Throwable {
        DwpHomePage dhp = new DwpHomePage(webDriver);
		dhp.clickOnsalesMarketingLink();
		dhp.clickOnAccountsListLink();
		dhp.clickOnFilterButton();
		dhp.searchByAccountNumberFieldClearAndClick();
		String accountId = "150638828";
		dhp.enterAccountId(accountId);
		Assert.assertTrue(dhp.accountWithAppropriateId(accountId).isDisplayed());
		dhp.clickOnAccountWithAppropriateId(accountId);
    }

}
