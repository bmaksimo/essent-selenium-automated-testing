package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.workflows.MarketMessagesPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

public class Marketberichten extends DwpScenario {

    public static String EAN;

    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);

    }

    @And("^Select \"([^\"]*)\" on Marktberichten page$")
    public void selectOnMarktberichtenPage(String element) {
        MarketMessagesPage mb = new MarketMessagesPage();
        EAN = mb.getEanFromTheFirstTransaction();
        mb.clickOnListActionsElemet(element);
        mb.clickOnSelectNewContractlineButton();
        mb.enterContractNumber(EAN);
        mb.clickOnSearchButton();
        mb.clickOnTheFirstContract();
        mb.clickOnSubmitButton();
    }

    @And("^Click on \"([^\"]*)\"$")
    public void clickOn(String newMarktbericht) {
        MarketMessagesPage mp = new MarketMessagesPage();
        mp.chooseMarketMessageButton(newMarktbericht);
    }

    @And("^Search by \"([^\"]*)\"$")
    public void searchBy(String str) {
        String ean = parameterProvider.getValueOrParameterAsString(str);
        MarketMessagesPage mb = new MarketMessagesPage();
        mb.enterContractNumber(ean);
        seleniumDriver.waitForRequestsToFinish();
        mb.clickOnSearchButton();
        seleniumDriver.waitForRequestsToFinish();
        mb.clickOnTheFirstContract();
        mb.clickOnSubmitButton();
    }
}
