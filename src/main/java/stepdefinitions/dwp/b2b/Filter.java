package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.filter.DwpFilter;

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

    @When("^b2b Account is selected by using \"([^\"]*)\" in filter$")
    public void bBAccountIsSelectedByUsingInFilterOn(String key) throws Throwable {
        DwpFilter fp = new DwpFilter(webDriver);
        fp.clickOnFilterButton();
        switch(key.toLowerCase()){
            case "accountid":
            case "klantnummer":
                fp.searchByAccountNumberFieldClearAndClick();
                String accountId = "150638828"; //TO DO remove hardcoded accountId when generate contract is implemented
                fp.enterAccountId(accountId);
                Assert.assertTrue(fp.accountWithAppropriateId(accountId).isDisplayed());
                fp.clickOnAccountWithAppropriateId(accountId);
                break;
        }
    }

}
