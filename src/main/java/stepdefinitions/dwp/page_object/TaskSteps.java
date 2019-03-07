package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.DashboardPages.ContractenPages.ContractPage;
import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

public class TaskSteps extends DwpScenario {
    private String taskId;

    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @When("^Plus action of first customer from list$")
    public void plusActionOfFirstCustomerFromList() {
    }

    @When("^Plus action and \"([^\"]*)\" of first customer from list$")
    public void plusActionAndOfFirstCustomerFromList(String action) throws Throwable {
        BaseObject baseObject = new BaseObject();
        Thread.sleep(30000);
        baseObject.clickOnPlus();
        seleniumDriver.waitForRequestsToFinish();
        baseObject.plusSubaction(action);
    }

    @When("^Plus action and Mark As Done/Markeren Als Verwerkt of first customer from list$")
    public void plusActionAndOfFirstCustomerFromList() throws Throwable {
        BaseObject baseObject = new BaseObject();
        Thread.sleep(30000);
        baseObject.clickOnPlus();
        seleniumDriver.waitForRequestsToFinish();
        baseObject.clickOnMarkAsDonePlusMenuSubAction();
    }

    private void inputResolution(String text) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("task-resolution-c-field")), text);
    }

    @When("^Save task ID of first customer in list$")
    public void saveTaskIDOfFirstCustomerInList() {
        BaseObject baseObject = new BaseObject();
        taskId = baseObject.getTaskId();
    }

    @And("^Resolution input is \"([^\"]*)\"$")
    public void resolutionInputIs(String text) {
        inputResolution(text);
    }

    @Then("^Task was marked as done$")
    public void taskWasMarkedAsDone() {
        findTaskId(taskId);
    }

    private void findTaskId(String taskId) {
        seleniumDriver.waitForRequestsToFinish();
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("task-number-c-default-value-field")), taskId);
    }

    @And("^Search for task id$")
    public void searchForTaskId() {
        ContractPage contractenPage = new ContractPage();
        contractenPage.searchForTaskId(taskId);
    }

    @Then("^\"([^\"]*)\" was rejection reason$")
    public void wasRejectionReason(String input) {
        ContractPage contractenPage = new ContractPage();
        contractenPage.findRejectionReason(input);
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
