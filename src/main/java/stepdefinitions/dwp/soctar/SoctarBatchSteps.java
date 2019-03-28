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
import static org.hamcrest.MatcherAssert.assertThat;

public class SoctarBatchSteps extends DwpScenario {

    @Before("@DWP, @SOCTAR, @E2E")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Soctar batch action \"([^\"]*)\" is clicked$")
    public void clickOnSoctarBatchAction(String actionName) {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(30);
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage();
        soctarBatchPage.clickOnAction(actionName);
    }

    @Then("^Soctar status is changed to \"([^\"]*)\"$")
    public void checkSoctarBatchStatus(String status) {
        seleniumDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage();
        boolean success = soctarBatchPage.checkStatus(status);
        assertThat("Soctar status is not " + status, success);
    }

    @Then("^Soctar type is changed to \"([^\"]*)\" within (\\d+) seconds?$")
    public void checkSoctarBatchType(String type, int seconds) {
        seleniumDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage();
        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(2, SECONDS))
            .atMost(new Duration(seconds, SECONDS)).until(()-> loopback() && soctarBatchPage.checkType(type));
    }

    private boolean loopback() {
        By classSelector = By.cssSelector(".icon-arrow-up");
        seleniumDriver.findElement(classSelector).click();
        seleniumDriver.waitForRequestsToFinish();
        String soctarFileLink = parameterProvider.getValueOrParameterAsString("parameter:soctar-file-name");
        seleniumDriver.findElement(By.linkText(soctarFileLink)).click();
        seleniumDriver.waitForRequestsToFinish();

        return true;
    }
}
