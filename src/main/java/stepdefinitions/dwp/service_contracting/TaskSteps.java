package stepdefinitions.dwp.service_contracting;

import com.essent.testing.dwp.pageobject.BaseObject;
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

    @Before("@SMOKE, @E2E_B2C, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @E2E_B2C, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @When("^Plus action of first customer from list$")
    public void plusActionOfFirstCustomerFromList() throws Throwable {
    }

    @When("^Plus action and \"([^\"]*)\" of first customer from list$")
    public void plusActionAndOfFirstCustomerFromList(String action) throws Throwable {
        webDriver.waitForRequestsToFinish();
        BaseObject baseObject = new BaseObject(webDriver);
        baseObject.clickOnPlus();
        baseObject.plusSubaction(action);
    }

    private void inputResolution(String text) {
        webDriver.findElementWhenVisible(By.id("task-resolution-c-field")).sendKeys(text);
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
        webDriver.findElementWhenVisible(By.id("task-number-c-default-value-field")).sendKeys(taskId);

    }
}
