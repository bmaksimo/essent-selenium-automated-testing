package stepdefinitions.dwp.b2b;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.contractingswitching.QuotesListPage;
import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.filter.DwpFilterPage;
import com.essent.testing.dwp.pageobject.impl.page.BaseObjectPage;
import com.essent.testing.dwp.pageobject.impl.servicecontracting.ChangeAccountStatusPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.util.resource.ResourceUtil;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class OfferteStep extends DwpScenario {
    @Before("@DWP or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }


    @And("^Take Offertenummer from first offerte$")
    public void takeOffertenummerFromFirstOfferte() throws Exception {
        String offerteNummer = new QuotesListPage().getOfferteNumberAsString();
        parameterProvider.put("offertenummer", offerteNummer);
    }

    @And("^Reset filter$")
    public void resetFilter() {
        new DwpFilterPage().resetFilter();
    }

    @And("^Label \"([^\"]*)\" is \"([^\"]*)\"$")
    public void labelIs(String label, String value) {
        String input = parameterProvider.getValueOrParameterAsString(value);
        new BaseObjectPage().clickOnLabel(label, input);
    }

    @And("^Filter button is clicked$")
    public void filterButtonIsClicked() {
        new DwpFilterPage().clickOnFilter();
    }

    @And("^Offertenummer input is \"([^\"]*)\"$")
    public void offertenummerInputIs(String value) {
        String input = parameterProvider.getValueOrParameterAsString(value);
        new QuotesListPage().offerteNumberFieldSendKeys(input);
    }

    @And("^Oplossing text is \"([^\"]*)\"$")
    public void oplossingTextIs(String input){
        new QuotesListPage().markAsDoneOplossingSendKeys(input);
    }

    @Then("^Offerte status is \"([^\"]*)\"$")
    public void statusIs(String status) {
        QuotesListPage quotesList = new QuotesListPage();
        int refreshCount = 15;
        boolean expectedValue = false;
        for (int i = 0; i < refreshCount; i++) {
            if (quotesList.getOfferteStatus().equals(status)) {
                expectedValue = true;
                break;
            } else {
                seleniumDriver.getDriver().navigate().back();
                Sleeper.sleepTightInSeconds(2);
                seleniumDriver.getDriver().navigate().forward();
                Sleeper.sleepTightInSeconds(2);
            }
        }
        Assert.assertTrue(String.format("Quote status is not changed to %s after %s number of retrying.",status, refreshCount),expectedValue);
    }

    @And("^\"([^\"]*)\" turn on with dot$")
    public void turnOnWithDot(String label) {
        new ToggleImpl().switchOnWithDot(label);
    }

    @Then("^Bevestigen$")
    public void bevestigen(){
        new BaseObjectPage().confirmQuote();
    }

    @And("^Sign quote file is uploaded$")
    public void signQuoteFileIsUploaded() {
        String filePath = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        boolean success = new ChangeAccountStatusPage().uploadFileForSign(filePath);
        assertThat(String.format("Signature file %s upload failed.", filePath), success, is(true));
    }

    @And("^Client signature receive data is \"([^\"]*)\"$")
    public void clientSignatureReceiveDataIs(String date) {
        new QuotesListPage().setSignatureReceivedDate(date);
    }
}
