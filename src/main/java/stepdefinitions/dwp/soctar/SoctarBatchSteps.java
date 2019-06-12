package stepdefinitions.dwp.soctar;

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

public class SoctarBatchSteps extends DwpScenario {

    private static String SOCTAR_CONFIRMATION_LETTERS = "SocTar Confirmation Letters";

    @Before("@DWP or @SOCTAR or @E2E")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Soctar batch action \"([^\"]*)\" is clicked$")
    public void clickOnSoctarBatchAction(String actionName) {
        new SoctarBatchPage().clickOnAction(actionName);
    }

    @Then("^Soctar status is changed to \"([^\"]*)\" within (\\d+) seconds?$")
    public void checkSoctarBatchStatus(String status, int seconds) {
        seleniumDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage();
        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(5, SECONDS))
            .atMost(new Duration(seconds, SECONDS)).until(()-> loopback() && soctarBatchPage.checkStatus(status));
    }

    @Then("^Soctar type is changed to \"([^\"]*)\" within (\\d+) seconds?$")
    public void checkSoctarBatchType(String type, int seconds) {
        seleniumDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage();
        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(5, SECONDS))
            .atMost(new Duration(seconds, SECONDS)).until(()-> loopback() && soctarBatchPage.checkType(type));
    }

    @Then("^Soctar confirmation letters type is changed to \"([^\"]*)\" within (\\d+) seconds?$")
    public void checkSoctarConfirmationLettersType(String type, int seconds) {
        seleniumDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage();
        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(5, SECONDS))
            .atMost(new Duration(seconds, SECONDS)).until(()-> loopback(true) && soctarBatchPage.checkType(type));
    }

    @Then("^Soctar confirmation letters status is changed to \"([^\"]*)\" within (\\d+) seconds?$")
    public void checkSoctarConfirmationLettersStatus(String status, int seconds) {
        seleniumDriver.waitForRequestsToFinish();
        SoctarBatchPage soctarBatchPage = new SoctarBatchPage();
        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(5, SECONDS))
            .atMost(new Duration(seconds, SECONDS)).until(()-> loopback(true) && soctarBatchPage.checkStatus(status));
    }

    private boolean loopback() {
        return loopback(false);
    }

    private boolean loopback(boolean isConfirmationLetters) {
        By classSelector = By.cssSelector(".icon-arrow-up");
        seleniumDriver.findElement(classSelector).click();
        seleniumDriver.waitForRequestsToFinish();
        String soctarFileLink = isConfirmationLetters ? SOCTAR_CONFIRMATION_LETTERS : parameterProvider.getValueOrParameterAsString("parameter:soctar-file-name");
        seleniumDriver.findElement(By.linkText(soctarFileLink)).click();
        seleniumDriver.waitForRequestsToFinish();

        return true;
    }
}
