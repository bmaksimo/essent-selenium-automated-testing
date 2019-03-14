package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.customer_dashboard.contracts.OffertePage;
import com.essent.testing.dwp.pageobject.impl.service_contracting.ChangeAccountStatusPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration("classpath:stepdefinitions/cucumber.xml")

public class OfferteStep extends DwpScenario {
    @Before("@DWP, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }


    @And("^Take Offertenummer from first offerte$")
    public void takeOffertenummerFromFirstOfferte() {
        OffertePage op = new OffertePage();
        String offertenummer = op.getOfferteNumber();
        parameterProvider.put("offertenummer", offertenummer);
    }

    @And("^Reset filter$")
    public void resetFilter() {
        OffertePage op = new OffertePage();
        op.resetFilter();
        seleniumDriver.waitForRequestsToFinish();
    }

    @And("^Label \"([^\"]*)\" is \"([^\"]*)\"$")
    public void labelIs(String label, String value) {
        String input = parameterProvider.getValueOrParameterAsString(value);
        OffertePage op = new OffertePage();
        op.clickOnLabel(label, input);
    }

    @And("^Filter button is clicked$")
    public void filterButtonIsClicked() {
        OffertePage of = new OffertePage();
        of.clickOnFilter();
    }

    @And("^Offertenummer input is \"([^\"]*)\"$")
    public void offertenummerInputIs(String value) {
        String input = parameterProvider.getValueOrParameterAsString(value);
        OffertePage of = new OffertePage();
        of.offerteNumberFieldSendKeys(input);
    }

    @And("^Oplossing text is \"([^\"]*)\"$")
    public void oplossingTextIs(String input){
        OffertePage of = new OffertePage();
        of.markAsDoneOplossingSendKeys(input);
    }

    @Then("^Offerte status is \"([^\"]*)\"$")
    public void statusIs(String status) {
        OffertePage of = new OffertePage();
        Assert.assertTrue(of.getStatus().equalsIgnoreCase(status));
    }

    @And("^\"([^\"]*)\" turn on with dot$")
    public void turnOnWithDot(String label) {
        ToggleImpl tgl = new ToggleImpl();
        tgl.clickCheckboxWithDot(label);

    }

    @Then("^Bevestigen$")
    public void bevestigen(){
        seleniumDriver.waitForRequestsToFinish();
        OffertePage of = new OffertePage();
        of.confirmQuote();
    }

    @And("^Sign quote file is uploaded$")
    public void signQuoteFileIsUploaded() {
        String filePath = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        ChangeAccountStatusPage changeAccountStatusPage = new ChangeAccountStatusPage();
        boolean success = changeAccountStatusPage.uploadFileForSign(filePath);
        assertThat(String.format("Signature file %s upload failed.", filePath), success, is(true));
    }

    @And("^Client signature receive data is \"([^\"]*)\"$")
    public void clientSignatureReceiveDataIs(String date) {
        OffertePage of = new OffertePage();
        of.setSinganureReceivedDate(date);
    }
}
