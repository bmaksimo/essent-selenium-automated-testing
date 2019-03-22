package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.service_contracting.LogCasePageImpl;
import com.essent.testing.dwp.pageobject.sales_marketing.customer_dashboard.service.CaseDetailsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")
public class LogQuestionsOfCustomers extends DwpScenario {


    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);

    }

    @Then("^Case details are visible$")
    public void checkSuccess() {
        CaseDetailsPage cdp = new CaseDetailsPage();
        String complaintText = "TextForComplaint";
        String solutionText = "TextForSolution";
        assertTrue(cdp.getComplaintText().equalsIgnoreCase(complaintText));
        assertTrue(cdp.getSolutionFieldText().equalsIgnoreCase(solutionText));
        assertTrue(cdp.checkIfPriorityIsHigh());
    }

    @And("^New case for account is created$")
    public void createCaseForAccount() {
        LogCasePageImpl logCasePage = new LogCasePageImpl();
        logCasePage.setSubjectSelection("Afrekeningsfactuur");
        logCasePage.setDescription("TextForComplaint");
        logCasePage.setSolution("TextForSolution");
        logCasePage.setPriority("Hoog");
        boolean success = logCasePage.fillInFormData();
        assertThat("Log Case Form was not filled in",
            success, is(true));
        logCasePage.save("Opslaan");

    }

    @Override
    @After("@DWP, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
