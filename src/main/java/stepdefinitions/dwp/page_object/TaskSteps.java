package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ContractenPage;
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
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Plus action of first customer from list$")
    public void plusActionOfFirstCustomerFromList() throws Throwable {
    }

    @When("^Plus action and \"([^\"]*)\" of first customer from list$")
    public void plusActionAndOfFirstCustomerFromList(String action) throws Throwable {
        BaseObject baseObject = new BaseObject(webDriver);
        webDriver.waitForRequestsToFinish();
        baseObject.clickOnPlus();
        baseObject.plusSubaction(action);
    }

    private void inputResolution(String text) {
        webDriver.waitAndSendKeys(webDriver.findElementWhenVisible(By.id("task-resolution-c-field")), text);
    }

    @When("^Save task ID of first customer in list$")
    public void saveTaskIDOfFirstCustomerInList() throws Throwable {
        BaseObject baseObject = new BaseObject(webDriver);
        taskId = baseObject.getTaskId();
    }

    @And("^Resolution input is \"([^\"]*)\"$")
    public void resolutionInputIs(String text) throws Throwable {
        inputResolution(text);
    }

    @Then("^Task was marked as done$")
    public void taskWasMarkedAsDone() throws Throwable {
        findTaskId(taskId);
    }

    private void findTaskId(String taskId) {
        webDriver.waitForRequestsToFinish();
        webDriver.waitAndSendKeys(webDriver.findElementWhenVisible(By.id("task-number-c-default-value-field")), taskId);
    }

    @And("^Search for task id$")
    public void searchForTaskId() throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.searchForTaskId(taskId);
    }

    @Then("^\"([^\"]*)\" was rejection reason$")
    public void wasRejectionReason(String input) throws Throwable {
        ContractenPage contractenPage = new ContractenPage(webDriver);
        contractenPage.findRejectionReason(input);
    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
