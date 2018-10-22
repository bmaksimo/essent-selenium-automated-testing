package stepdefinitions.dwp.page_object;

import com.essent.testing.dwp.pageobject.impl.service_contracting.ChangeAccountStatusPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
public class ChangeAccountStatusSteps extends DwpScenario {
    @Before("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @BILLING, @B2B_REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@SMOKE, @QUOTE, @QUOTE_CS, @QUOTE_MI, @QUOTE_SS, @B2B_REGRESSION")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @And("^Update account status on \"([^\"]*)\"$")
    public void updateAccountStatusOn(String status) throws Throwable {
        ChangeAccountStatusPage changeAccountStatusPage = new ChangeAccountStatusPage(webDriver);
        changeAccountStatusPage.chooseAccountStatus(status);
    }

    @And("^Client signature file is uploaded$")
    public void uploadFile() throws Throwable {
        String filePath = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        ChangeAccountStatusPage changeAccountStatusPage = new ChangeAccountStatusPage(webDriver);
        boolean success = changeAccountStatusPage.uploadFile(filePath);
        assertThat(String.format("Signature file %s upload failed.", filePath), success, is(true));
    }

    @Then("^Find document$")
    public void findDocument() throws Throwable {
        ChangeAccountStatusPage changeAccountStatusPage = new ChangeAccountStatusPage(webDriver);
        changeAccountStatusPage.findDocument();
    }
}
