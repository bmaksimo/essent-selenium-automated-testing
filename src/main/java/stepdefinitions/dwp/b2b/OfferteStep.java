package stepdefinitions.dwp.b2b;

import com.essent.testing.dwp.pageobject.impl.elements.ToggleImpl;
import com.essent.testing.dwp.pageobject.impl.page.OffertePage;
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
    public void takeOffertenummerFromFirstOfferte() throws Throwable {
        OffertePage op = new OffertePage(webDriver);
        String offertenummer = op.getOfferteNumber();
        parameterProvider.put("offertenummer", offertenummer);
    }

    @And("^Reset filter$")
    public void resetFilter() throws Throwable {
        OffertePage op = new OffertePage(webDriver);
        op.resetFilter();
        webDriver.waitForRequestsToFinish();
    }

    @And("^Label \"([^\"]*)\" is \"([^\"]*)\"$")
    public void labelIs(String label, String value) throws Throwable {
        String input = parameterProvider.getValueOrParameterAsString(value);
        OffertePage op = new OffertePage(webDriver);
        op.clickOnLabel(label, input);
    }

    @And("^Filter button is clicked$")
    public void filterButtonIsClicked() throws Throwable {
        OffertePage of = new OffertePage(webDriver);
        of.clickOnFilter();
    }

    @And("^Offertenummer input is \"([^\"]*)\"$")
    public void offertenummerInputIs(String value) throws Throwable {
        String input = parameterProvider.getValueOrParameterAsString(value);
        OffertePage of = new OffertePage(webDriver);
        of.offerteNumberFieldSendKeys(input);
    }

    @And("^Oplossing text is \"([^\"]*)\"$")
    public void oplossingTextIs(String input) throws Throwable {
        OffertePage of = new OffertePage(webDriver);
        of.markAsDoneOplossingSendKeys(input);
    }

    @Then("^Offerte status is \"([^\"]*)\"$")
    public void statusIs(String status) throws Throwable {
        OffertePage of = new OffertePage(webDriver);
        Assert.assertTrue(of.getStatus().equalsIgnoreCase(status));
    }

    @And("^\"([^\"]*)\" turn on with dot$")
    public void turnOnWithDot(String label) throws Throwable {
        ToggleImpl tgl = new ToggleImpl(webDriver);
        tgl.clickCheckboxWithDot(label);

    }

    @Then("^Bevestigen$")
    public void bevestigen() throws Throwable {
        webDriver.waitForRequestsToFinish();
        OffertePage of = new OffertePage(webDriver);
        of.clickOnBevestigen();
    }

    @And("^Sign quote file is uploaded$")
    public void signQuoteFileIsUploaded() throws Throwable {
        String filePath = ResourceUtil.toPath("/data/dwp/customer-signature.pdf");
        ChangeAccountStatusPage changeAccountStatusPage = new ChangeAccountStatusPage(webDriver);
        boolean success = changeAccountStatusPage.uploadFileForSign(filePath);
        assertThat(String.format("Signature file %s upload failed.", filePath), success, is(true));
    }

    @And("^Client signature receive data is \"([^\"]*)\"$")
    public void clientSignatureReceiveDataIs(String date) throws Throwable {
        OffertePage of = new OffertePage(webDriver);
        of.setSinganureReceivedDate(date);
    }
}
