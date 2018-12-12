package stepdefinitions.odoo.accounting.coda;

import com.essent.testing.odoo.pageobject.impl.modal.coda.CodaImportDialogImpl;
import com.essent.testing.odoo.pageobject.modal.CodaImportDialog;
import com.essent.testing.odoo.scenario.OdooScenario;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.Collection;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.FIVE_HUNDRED_MILLISECONDS;
import static org.awaitility.Duration.TWO_SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OdooCodaSteps extends OdooScenario {
    @Before("@ODOO, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^Odoo file upload dialog is ([^\"]*)$")
    public void verifyDialogue(String title) {
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        assertThat(dialog.getTitle(), equalToIgnoringCase(title));
    }

    @When("^CODA file is selected")
    public void inputUploadValue() {
        String path = ResourceUtil.toPath(File.separator + "data" + File.separator + "odoo" + File.separator);
        Collection<File> codaFiles = FileUtils.listFiles(new File(path), TrueFileFilter.INSTANCE, null);

        assertThat("No CODA file found in " + path, CollectionUtils.isNotEmpty(codaFiles));

        File codaFileToUpload = codaFiles.iterator().next();
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        dialog.setUploadFile(codaFileToUpload.getAbsolutePath());

        assertThat(dialog.fillInFormData(), is(true));
    }

    @When("^CODA file is ([^\"]*)$")
    public void setCodaPath(String codaFile) {
        String path = parameterProvider.getValueOrParameterAsString(codaFile) == null ?
            codaFile : parameterProvider.getValueOrParameterAsString(codaFile);
        File document = new File(path);
        assertThat("File at path " + document.getAbsolutePath() + " doesn't exist.", true,
            is(document.exists()));
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        dialog.setUploadFile(document.getAbsolutePath());
        boolean success = dialog.fillInFormData();
        assertThat(success, is(true));
    }

    @And("^Odoo file upload confirm button is ([^\"]*)$")
    public void conformCodaImport(String button) {
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        dialog.setImportButton(button);
        dialog.confirm();
    }

    @And("^Odoo file import report$")
    public void odooFileImportReport() throws Throwable {
        CodaImportDialog dialog = new CodaImportDialogImpl(webDriver);
        String report = dialog.getImportReport();
        assertThat(report, not(isEmptyString()));
    }

    @And("^Generated CODA file is downloaded$")
    public void odooDownloadGeneratedCodaFile() throws Throwable {
        WebElement downloadLink = webDriver.findElement(By.xpath("//div[@class='modal-content openerp']//a[@class='oe_form_uri']"));
        if (null == downloadLink) throw new CucumberException("CODA file download link was not found");
        downloadLink.click();

        String path = ResourceUtil.toPath(File.separator + "data" + File.separator + "odoo" + File.separator);
        given().await()
            .pollInterval(FIVE_HUNDRED_MILLISECONDS)
            .pollDelay(TWO_SECONDS)
            .atMost(new Duration(10, SECONDS)).until(()-> CollectionUtils.isNotEmpty(retrieveDownloadedCodaFiles(path)));
        if (CollectionUtils.isEmpty(retrieveDownloadedCodaFiles(path))) throw new CucumberException("CODA file download link was not found");

        assertThat("File could not be downloaded", CollectionUtils.isNotEmpty(retrieveDownloadedCodaFiles(path)));

        File downloadedCodaFile = retrieveDownloadedCodaFiles(path).iterator().next();
        String downloadedCodaFilePath = path + downloadedCodaFile.getName();

        parameterProvider.put("codaFile" , downloadedCodaFilePath);
    }

    private Collection<File> retrieveDownloadedCodaFiles(String path) {
        return FileUtils.listFiles(new File(path), new WildcardFileFilter("*.COD"), TrueFileFilter.INSTANCE);
    }

    @And("^Cleanup Odoo CODA files$")
    public void odooCleanupOdooCodaFiles() throws Throwable {
        FileUtils.cleanDirectory(new File(ResourceUtil.toPath(File.separator + "data" + File.separator + "odoo" + File.separator)));
    }



    @Override
    @After("@ODOO, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
