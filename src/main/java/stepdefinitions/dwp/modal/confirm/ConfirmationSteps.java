package stepdefinitions.dwp.modal.confirm;

import com.essent.testing.dwp.pageobject.impl.modal.confirm.ConfirmSignatureDialogImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConfirmationSteps extends DwpScenario {

    @Before("@DWP, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Modal dialog is ([^\"]*)$")
    public void verifyDialogue(String title) {
        ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl(webDriver, title);
        boolean success = dialog.isShown();
        assertThat("Contract signature dialog was not shown.",
            success,
            is(true));
    }

    @And("^Contract signature is confirmed$")
    public void contractSignatureIsConfirmed() throws Throwable {
        ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl(webDriver);
        boolean success = dialog.confirm();
        assertThat("Contract signature was not confirmed.",
            success,
            is(true));
    }

    @Override
    @After("@DWP, @CORE, @E2E, @REGRESSION, @SALES-MARKETING, @CONTRACTING-SWITCHING, @BUSINESS-DESK, @BILLING")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
