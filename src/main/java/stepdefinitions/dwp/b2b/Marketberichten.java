package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.page.MarketberichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class Marketberichten extends DwpScenario {

    public  static String EAN;

    @Before("@B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^Select \"([^\"]*)\" on Marktberichten page$")
    public void selectOnMarktberichtenPage(String element) throws Throwable {
        MarketberichtenPage mb = new MarketberichtenPage(webDriver);
        EAN =mb.getEanFromTheFirstTransaction();
        mb.clickOnListActionsElemet(element);
        mb.clickOnSelectNewContractlineButton();
        mb.enterContractNumber(EAN);
        mb.clickOnSearchButton();
        mb.clickOnTheFirstContract();
        mb.clickOnSubmitButton();
    }

    @And("^New martetberich is created$")
    public void newMartetberichIsCreated() throws Throwable {
        MarketberichtenPage mb = new MarketberichtenPage(webDriver);
        mb.clickOnModuleDropdownMenu();
        mb.clickOnInitiateStopAccessOption();
        mb.clickOnLabelDropdownMenu();
        mb.clickOnNonResidentialEndOfContractOption();
        mb.clickOnDropBudgetMeterOption();
        mb.clickOnConfirmButton();
    }



    @Then("^Proper Module and label status is displayed$")
    public void properModuleAndLabelStatusIsDisplayed() throws Throwable {

    }

}
