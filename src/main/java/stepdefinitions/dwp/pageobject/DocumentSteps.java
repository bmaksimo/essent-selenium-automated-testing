package stepdefinitions.dwp.pageobject;

import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.documents.DocumentsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class DocumentSteps extends DwpScenario {

    @Before("@DWP or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Then("^Check if document \"([^\"]*)\" is present$")
    public void checkIfDocumentIsPresent(String documentName){
        Assert.assertTrue(new DocumentsPage().isDocumentNamePresent(documentName));
    }
}
