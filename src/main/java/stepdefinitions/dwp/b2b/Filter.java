package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.DwpFilter;

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


//    @When("^b2b Account is selected by using filter on account page$")
//    public void pgoAccountIsSelected() throws Throwable {
//        DwpHomePage dhp = new DwpHomePage(webDriver);
//		dhp.clickOnsalesMarketingLink();
//		webDriver.waitUntilAngularPageIsLoaded();
//		dhp.clickOnAccountsListLink();
//		dhp.clickOnFilterButton();
//		dhp.searchByAccountNumberFieldClearAndClick();
//		String accountId = "150638828"; //TO DO remove hardcoded accountId when gerate contract is implemented
//		dhp.enterAccountId(accountId);
//        webDriver.waitUntilAngularPageIsLoaded();
//		Assert.assertTrue(dhp.accountWithAppropriateId(accountId).isDisplayed());
//		dhp.clickOnAccountWithAppropriateId(accountId);
//    }

    @When("^b2b Account is selected by using \"([^\"]*)\" in filter$")
    public void bBAccountIsSelectedByUsingInFilterOn(String key) throws Throwable {
        DwpFilter fp = new DwpFilter(webDriver);
        fp.clickOnFilterButton();
        switch(key.toLowerCase()){
            case "accountid":
                fp.searchByAccountNumberFieldClearAndClick();
                String accountId = "150638828"; //TO DO remove hardcoded accountId when generate contract is implemented
                fp.enterAccountId(accountId);
                webDriver.waitUntilAngularPageIsLoaded();
                Assert.assertTrue(fp.accountWithAppropriateId(accountId).isDisplayed());
                fp.clickOnAccountWithAppropriateId(accountId);
                break;


        }








    }






}
