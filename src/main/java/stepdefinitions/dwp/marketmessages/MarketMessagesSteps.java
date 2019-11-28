package stepdefinitions.dwp.marketmessages;

import com.essent.testing.dwp.pageobject.impl.modal.market_messages.SearchContractLinesDialog;
import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.workflows.MarketMessagesPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class MarketMessagesSteps extends DwpScenario {

    @Before("@DWP or @REGRESSION or @E2E")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @When("^Dialog search input is current \"([^\"]*)\"$")
    public void runDialogSearch(String searchInput) {
        String currentSearchInputValue = parameterProvider.getValueOrParameterAsString(searchInput);
        new SearchContractLinesDialog().searchContractLine(currentSearchInputValue);
    }

    @When("^Select Contractline dialog is confirmed$")
    public void clickConfirmButton() {
        new SearchContractLinesDialog().confirm(parameterProvider.getScenarioInfo());
    }

    @Override
    @After("@DWP or @REGRESSION or @E2E")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Copy task number with modul \"([^\"]*)\" in list \"([^\"]*)\"$")
    public void copyTaskNumberWithModul(String modul, String list)  {
        MarketMessagesPage mmp = new MarketMessagesPage();
        String taskNumber = mmp.getTaskNumber(modul, list);
        parameterProvider.put("taskNumber", taskNumber.substring(0,taskNumber.indexOf(' ')));
    }

    @And("^Click on \"([^\"]*)\"$")
    public void clickOn(String marketMessage) {
        new MarketMessagesPage().chooseMarketMessageButton(marketMessage);
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
