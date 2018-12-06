package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;

public class DocumentSteps extends DwpScenario {

    @Before("@DWP, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) {
        registerActiveScenario(scenario);
    }

    @Then("^Check if document \"([^\"]*)\" is present$")
    public void checkIfDocumentIsPresent(String txt) throws Throwable {
        BaseObject bo = new BaseObject(webDriver);
        String documentName = bo.documentText();
        Assert.assertTrue(documentName.equalsIgnoreCase(txt));
    }
}
