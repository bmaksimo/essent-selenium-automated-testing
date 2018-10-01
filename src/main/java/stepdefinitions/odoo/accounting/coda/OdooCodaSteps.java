package stepdefinitions.odoo.accounting.coda;

import com.essent.testing.odoo.pageobject.impl.modal.coda.CodaImportDialogImpl;
import com.essent.testing.odoo.pageobject.modal.CodaImportDialog;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import stepdefinitions.odoo.navigation.OdooNavigationElements;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OdooCodaSteps extends OdooNavigationElements {
    @Before("@SMOKE, @ODOO, @CODA")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Odoo file upload dialog is ([^\"]*)$")
    public void verifyDialogue(String title) {
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        assertThat(dialog.getTitle(), equalToIgnoringCase(title));
    }

    @When("^CODA file is selected")
    public void inputUploadValue() throws Throwable {
        String path = ResourceUtil.toPath("/data/odoo/6860012583.COD");
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true,
            is(document.exists()));
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        dialog.setUploadFile(path);
        boolean success = dialog.fillInFormData();
        assertThat(success, is(true));
    }

    @And("^Odoo file upload confirm button is ([^\"]*)$")
    public void conformCodaImport(String button) {
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        dialog.setImportButton(button);
        dialog.confirm();
    }

    @Override
    @After("@SMOKE, @ODOO, @CODA")
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @And("^Odoo file import report$")
    public void odooFileImportReport() throws Throwable {
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        String report = dialog.getImportReport();
        assertThat(report, not(isEmptyString()));
    }
}
