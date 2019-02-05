package stepdefinitions.dwp.soctar;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.page.SoctarBatchPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.awaitility.Duration;
import org.openqa.selenium.By;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.TWO_SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;

public class SoctarBatchSteps extends DwpScenario {

    @Before("@DWP, @SOCTAR, @E2E")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Soctar batch action \"([^\"]*)\" is clicked$")
    public void clickOnSoctarBatchAction(String actionName) {
        webDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(30);
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage(webDriver);
        soctarBatchPage.clickOnAction(actionName);
//        parameterProvider.put("soctar-action", actionName);
    }

    @Then("^Soctar status is changed to \"([^\"]*)\"$")
    public void checkSoctarBatchStatus(String status) {
        webDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage(webDriver);
        boolean success = soctarBatchPage.checkStatus(status);
        assertThat("Soctar status is not " + status, success);
    }

    @Then("^Soctar type is changed to \"([^\"]*)\" within (\\d+) seconds?$")
    public void checkSoctarBatchType(String type, int seconds) {
        webDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage(webDriver);
        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(2, SECONDS))
            .atMost(new Duration(seconds, SECONDS)).until(()-> loopback() && soctarBatchPage.checkType(type));
    }

    private boolean loopback() {
        By classSelector = By.cssSelector(".icon-arrow-up");
        webDriver.findElement(classSelector).click();
        webDriver.waitForRequestsToFinish();
        String soctarFileLink = parameterProvider.getValueOrParameterAsString("parameter:soctar-file-name");
        webDriver.findElement(By.linkText(soctarFileLink)).click();
        webDriver.waitForRequestsToFinish();

        return true;
    }
}
