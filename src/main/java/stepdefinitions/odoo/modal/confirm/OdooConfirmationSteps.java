package stepdefinitions.odoo.modal.confirm;

import com.essent.testing.dwp.pageobject.impl.modal.confirm.ConfirmSignatureDialogImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import com.essent.testing.odoo.pageobject.impl.modal.coda.CodaImportDialogImpl;
import com.essent.testing.odoo.pageobject.modal.CodaImportDialog;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

public class OdooConfirmationSteps extends OdooScenario {

    @Before("@SMOKE, @ODOO, @CODA")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Odoo file upload dialog is ([^\"]*)$")
    public void verifyDialogue(String title) {
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        assertThat(dialog.getTitle(), equalToIgnoringCase(title));
    }


    @Override
    @After("@SMOKE, @ODOO, @CODA")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
