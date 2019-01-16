package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.page.MarktBerichtenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

public class Marketberichten extends DwpScenario {

    public static String EAN;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @And("^Select \"([^\"]*)\" on Marktberichten page$")
    public void selectOnMarktberichtenPage(String element) throws Throwable {
        MarktBerichtenPage mb = new MarktBerichtenPage(webDriver);
        EAN = mb.getEanFromTheFirstTransaction();
        mb.clickOnListActionsElemet(element);
        mb.clickOnSelectNewContractlineButton();
        mb.enterContractNumber(EAN);
        mb.clickOnSearchButton();
        mb.clickOnTheFirstContract();
        mb.clickOnSubmitButton();
    }

    @And("^Click on \"([^\"]*)\"$")
    public void clickOn(String newMarktbericht) throws Throwable {
        MarktBerichtenPage mp = new MarktBerichtenPage(webDriver);
        mp.createNewMarktBericht(newMarktbericht);
    }

    @And("^Search by \"([^\"]*)\"$")
    public void searchBy(String str) throws Throwable {
        String ean = parameterProvider.getValueOrParameterAsString(str);
        MarktBerichtenPage mb = new MarktBerichtenPage(webDriver);
        mb.enterContractNumber(ean);
        webDriver.waitForRequestsToFinish();
        mb.clickOnSearchButton();
        webDriver.waitForRequestsToFinish();
        mb.clickOnTheFirstContract();
        mb.clickOnSubmitButton();
    }
}
