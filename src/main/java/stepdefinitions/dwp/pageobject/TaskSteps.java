package stepdefinitions.dwp.pageobject;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.contracts.ContractPage;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service.ServicePage;
import com.essent.testing.dwp.pageobject.werkbakken.TasksPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TaskSteps extends DwpScenario {

    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @When("^Plus action and \"([^\"]*)\" of first customer from list$")
    public void plusActionAndOfFirstCustomerFromList(String action){
        BaseObjectPage baseObject = new BaseObjectPage();
        baseObject.clickOnPlus();
        baseObject.plusSubAction(action);
    }

    @When("^Plus action and \"([^\"]*)\" of first customer from list waiting for (\\d+) seconds$")
    public void plusActionAndOfFirstCustomerFromList(String action, int waitingTime){
        BaseObjectPage baseObject = new BaseObjectPage();
        Sleeper.sleepTightInSeconds(waitingTime);
        baseObject.clickOnPlusNow();
        Sleeper.sleepTightInSeconds(waitingTime);
        baseObject.plusSubactionNow(action);
    }

    @When("^Plus action and Mark As Done/Markeren Als Verwerkt of first customer from list$")
    public void plusActionAndOfFirstCustomerFromList(){
        BaseObjectPage baseObject = new BaseObjectPage();
        baseObject.clickOnPlus();
        seleniumDriver.waitForRequestsToFinish();
        baseObject.clickOnMarkAsDonePlusMenuSubAction();
    }

    @When("^Plus action and Mark As Done/Markeren Als Verwerkt of first customer from list waiting for (\\d+) seconds$")
    public void plusActionAndOfFirstCustomerFromList(int waitingTime){
        BaseObjectPage baseObject = new BaseObjectPage();
        Sleeper.sleepTightInSeconds(waitingTime);
        baseObject.clickOnPlusNow();
        Sleeper.sleepTightInSeconds(waitingTime);
        baseObject.clickOnMarkAsDonePlusMenuSubActionNow();
    }

    @And("^Resolution input is \"([^\"]*)\"$")
    public void resolutionInputIs(String text) {
        TasksPage tp = new TasksPage();
        tp.inputResolution(text);
    }

    @And("^Resolution input is \"([^\"]*)\" waiting for (\\d+) seconds$")
    public void resolutionInputIs(String text, int waitingTime) {
        TasksPage tp = new TasksPage();
        tp.inputResolution(text, waitingTime);
    }

    @Then("^Task was marked as done$")
    public void taskWasMarkedAsDone() {
        TasksPage tp = new TasksPage();
        tp.findTaskId(parameterProvider.getValueOrParameterAsString("parameter:contractNumber"));
    }

    @And("^Search for task id$")
    public void searchForTaskId() {
        ContractPage contractenPage = new ContractPage();
        contractenPage.searchForTaskId(parameterProvider.getValueOrParameterAsString("parameter:contractNumber"));
    }

    @Then("^\"([^\"]*)\" was rejection reason$")
    public void wasRejectionReason(String input) {
        ServicePage dsp = new ServicePage();
        dsp.findRejectionReason(input);
    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
