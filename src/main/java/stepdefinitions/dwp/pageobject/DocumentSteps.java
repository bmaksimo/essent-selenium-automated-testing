package stepdefinitions.dwp.pageobject;

import com.essent.testing.dwp.pageobject.salesmarketing.customer_dashboard.documents.DocumentsPage;
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
    public void checkIfDocumentIsPresent(String txt){
        DocumentsPage dp = new DocumentsPage();
        String documentName = dp.getDocumentName();
        Assert.assertTrue(documentName.equalsIgnoreCase(txt));
    }
}
