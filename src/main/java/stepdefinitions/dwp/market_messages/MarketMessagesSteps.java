package stepdefinitions.dwp.market_messages;

import com.essent.testing.dwp.pageobject.impl.modal.market_messages.SearchContractLinesDialog;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class MarketMessagesSteps extends DwpScenario {

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Dialog search input is current \"([^\"]*)\"$")
    public void runDialogSearch(String searchInput) {
        String currentSearchInputValue = parameterProvider.getValueOrParameterAsString(searchInput);
        SearchContractLinesDialog dialog = new SearchContractLinesDialog(webDriver);
        dialog.searchContractLine(currentSearchInputValue);
    }

    @When("^Select Contractline dialog is confirmed$")
    public void clickConfirmButton() {
        SearchContractLinesDialog dialog = new SearchContractLinesDialog(webDriver);
        dialog.confirm();
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
