package stepdefinitions.dwp.assignment;

import com.billinghouse.javascript.testrunner.dwp.menu.MenuTests;
import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Autocrat;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
public class AssignmentScenario extends DwpScenario {

    private String latestAssignment;

    @Before("@ASSIGNMENT")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @After({"@ASSIGNMENT"})
    public void tearDown() throws Exception {
        tidyUp();
    }

    @When("^Category is ([^\"]*)")
    public void select_assignment_category(String itemName) throws  Throwable {
        executeJsTest(MenuTests.SELECT_ASSIGNMENT_CATEGORY.getTest(), itemName);
    }

    @When("^Assignment is ([^\"]*)")
    public void select_assignment_type(String itemName) throws  Throwable {
        executeJsTest(MenuTests.SELECT_ASSIGNMENT_TYPE.getTest(), itemName);
    }

    @Then("The tab ([^\"]*) is visible and enabled")
    public void tab_is_visible_and_enabled(DwpSalesMarketingTopMenuEnum tab) throws Throwable {
        Model.Execution execution = new Model.Execution();
        switch (tab) {
            case ACCOUNTS:
                execution.element("DWP_TOP_MENU_ITEM",
                    new Model.Element().search("XPATH").query("//a[@id='accounts-list-link']"));
                break;
            default:
                break;
        }
        Autocrat.ExecutionContext context =
            new Autocrat.ExecutionContext(webDriver.getDriver(), execution);
        Autocrat.executeStep(context,
            new Model.Step().action(Action.CLICK).element("DWP_TOP_MENU_ITEM"));
    }
}
