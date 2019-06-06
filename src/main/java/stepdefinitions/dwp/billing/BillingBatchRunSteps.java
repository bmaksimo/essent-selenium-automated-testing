package stepdefinitions.dwp.billing;

import com.essent.testing.dwp.pageobject.impl.modal.confirm.ConfirmSignatureDialogImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BillingBatchRunSteps extends DwpScenario {

    @Before("@DWP or @E2E or @API")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Invoice run is scheduled$")
    public void invoiceRunIsScheduled() throws Throwable {
        ConfirmSignatureDialog dialog = new ConfirmSignatureDialogImpl();
        boolean success = dialog.confirm();
        assertThat("Invoice run dialog was not confirmed.",
            success,
            is(true));
    }


    @Override
    @After("@DWP or @E2E")
    public void tearDown() {
        super.tearDown();
    }
}
