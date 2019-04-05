package stepdefinitions.dwp.page_object;

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
    private String taskId;
    private TasksPage tp;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @When("^Plus action of first customer from list$")
    public void plusActionOfFirstCustomerFromList() {
    }

    @When("^Plus action and \"([^\"]*)\" of first customer from list$")
    public void plusActionAndOfFirstCustomerFromList(String action) throws Throwable {
        BaseObjectPage baseObject = new BaseObjectPage();
        Sleeper.sleepTightInSeconds(30);
        baseObject.clickOnPlus();
        seleniumDriver.waitForRequestsToFinish();
        baseObject.plusSubaction(action);
    }

    @When("^Plus action and Mark As Done/Markeren Als Verwerkt of first customer from list$")
    public void plusActionAndOfFirstCustomerFromList() throws Throwable {
        BaseObjectPage baseObject = new BaseObjectPage();
        baseObject.clickOnPlus();
        seleniumDriver.waitForRequestsToFinish();
        baseObject.clickOnMarkAsDonePlusMenuSubAction();
    }

    @When("^Plus action and Mark As Done/Markeren Als Verwerkt of first customer from list waiting for (\\d+) seconds$")
    public void plusActionAndOfFirstCustomerFromList(int waitingTime) throws Throwable {
        BaseObjectPage baseObject = new BaseObjectPage();
        Sleeper.sleepTightInSeconds(waitingTime);
        baseObject.clickOnPlusNow();
        Sleeper.sleepTightInSeconds(waitingTime);
        baseObject.clickOnMarkAsDonePlusMenuSubActionNow();
    }

    @When("^Save task ID of first customer in list$")
    public void saveTaskIDOfFirstCustomerInList() {
        TasksPage tp = new TasksPage();
        taskId = tp.getTaskId();
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
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
