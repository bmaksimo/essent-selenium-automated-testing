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

    @Before("@DWP, @REGRESSION")
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
}
