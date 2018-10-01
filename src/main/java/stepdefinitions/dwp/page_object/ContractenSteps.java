package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.BaseObject;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractenPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ContractenSteps extends DwpScenario {

    ContractenPage contractenPage = new ContractenPage(webDriver);
    private String eanCode;

    @Before("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @RENEWAL, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }


    @And("^Search for ean code$")
    public void searchForEanCode() throws Throwable {
        contractenPage.searchForEanCode(eanCode);
    }

    @When("^Input in ([^\"]*) is \"([^\"]*)\"$")
    public void inputInModuleIs(String label, String input) throws Throwable {
        System.out.println("INPUT : " + input);
        contractenPage.fieldDropDownLabel(label, input);
    }

    @And("^Check toggle \"([^\"]*)\"$")
    public void checkToggle(String label) throws Throwable {
        contractenPage.turnOnCheckBox(label);
    }

    @When("^Find \"([^\"]*)\" contract$")
    public void findContract(String input) throws Throwable {
        System.out.println("input is : " + input);
        eanCode = contractenPage.findActiveContract(input);
    }

    @Then("^Confirm task was \"([^\"]*)\"$")
    public void confirmTaskWas(String input) throws Throwable {
        contractenPage.confirmTaskStatus(input);
    }
}
