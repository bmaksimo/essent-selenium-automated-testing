package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ContractenSteps extends DwpScenario {

    private String eanCode = null;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }


    @And("^Search for ean code$")
    public void searchForEanCode() throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.searchForEanCode(eanCode);
    }

    @When("^Input in ([^\"]*) is \"([^\"]*)\"$")
    public void inputInModuleIs(String label, String input) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.fieldDropDownLabel(label, input);
    }

    @And("^Check toggle \"([^\"]*)\"$")
    public void checkToggle(String label) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.turnOnTestingAndMarketMock(label);
    }

    @When("^Find \"([^\"]*)\" contract$")
    public void findContract(String input) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        eanCode = contractenPage.findActiveContract(input);
    }

    @Then("^Confirm task was \"([^\"]*)\"$")
    public void confirmTaskWas(String input) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.confirmTaskStatus(input);
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^\"([^\"]*)\" input in omschrijving$")
    public void inputInOmschrijving(String text) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.inputText(text);
    }
}
