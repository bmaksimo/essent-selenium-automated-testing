package stepdefinitions.dwp.market_messages;

import com.essent.testing.dwp.pageobject.impl.modal.market_messages.SearchContractLinesDialog;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class MarketMessagesSteps extends DwpScenario {

    @Before("@DWP or @REGRESSION or @E2E")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @When("^Dialog search input is current \"([^\"]*)\"$")
    public void runDialogSearch(String searchInput) {
        String currentSearchInputValue = parameterProvider.getValueOrParameterAsString(searchInput);
        SearchContractLinesDialog dialog = new SearchContractLinesDialog();
        dialog.searchContractLine(currentSearchInputValue);
    }

    @When("^Select Contractline dialog is confirmed$")
    public void clickConfirmButton() {
        SearchContractLinesDialog dialog = new SearchContractLinesDialog();
        dialog.confirm();
    }

    @Override
    @After("@DWP or @REGRESSION or @E2E")
    public void tearDown() {
        super.tearDown();
    }
}
