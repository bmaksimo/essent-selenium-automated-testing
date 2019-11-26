package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.servicecontracting.LogCasePageImpl;
import com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.service.CaseDetailsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class LogQuestionsOfCustomersSteps extends DwpScenario {


    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);

    }

    @Then("^Case details are visible$")
    public void checkSuccess() {
        CaseDetailsPage cdp = new CaseDetailsPage();
        String complaintText = "Neki tekst - Pitanje";
        String solutionText = "Neki tekst - Resenje";
        assertTrue("Actual complaint text: "+cdp.getComplaintText()+" is different from expected: "+complaintText, cdp.getComplaintText().equalsIgnoreCase(complaintText));
        assertTrue("Actual solution field text: "+cdp.getSolutionFieldText()+" is different from expected: "+solutionText, cdp.getSolutionFieldText().equalsIgnoreCase(solutionText));
        assertTrue("Actual priority is different from expected: High", cdp.checkIfPriorityIsHigh());
    }

    @And("^New case for account is created$")
    public void createCaseForAccount() {
        LogCasePageImpl logCasePage = new LogCasePageImpl();
        logCasePage.setSubjectSelection("Afrekeningsfactuur");
        logCasePage.setDescription("Neki tekst - Pitanje");
        logCasePage.setSolution("Neki tekst - Resenje");
        logCasePage.setPriority("Hoog");
        boolean success = logCasePage.fillInFormData();
        assertThat("Log Case Form was not filled in",
            success, is(true));
        logCasePage.save("Opslaan");

    }

    @Override
    @After("@DWP or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
