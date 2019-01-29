package stepdefinitions.dwp.soctar;

import com.essent.testing.dwp.pageobject.impl.page.SoctarBatchPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.MatcherAssert.assertThat;

public class SoctarBatchSteps extends DwpScenario {

    @Before("@DWP, @SOCTAR, @E2E")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Soctar batch action \"([^\"]*)\" is clicked$")
    public void clickOnSoctarBatchAction(String actionName) {
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage(webDriver);
        soctarBatchPage.clickOnAction(actionName);
        webDriver.waitForRequestsToFinish();
    }

    @Then("^Soctar status is changed to \"([^\"]*)\"$")
    public void checkSoctarBatchStatus(String status) {
        webDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage(webDriver);
        boolean success = soctarBatchPage.checkStatus(status);
        assertThat("Soctar status is not " + status, success);
    }

    @Then("^Soctar type is changed to \"([^\"]*)\"$")
    public void checkSoctarBatchType(String type) {
        webDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage(webDriver);
        boolean success = soctarBatchPage.checkType(type);
        assertThat("Soctar type is not " + type, success);
    }


}
