package stepdefinitions.odoo.page;

import com.essent.testing.odoo.pageobject.impl.page.OdooDunningPages;
import com.essent.testing.odoo.scenario.OdooScenario;
import com.essent.testing.odoo.table.OdooTableFilter;
import com.essent.testing.table.Filter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OdooDunningSteps extends OdooScenario {
    @Before("@ODOO or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @Then("^Dunning bundle is present$")
    public void dunningBundleIsPresent() {
        OdooDunningPages odp = new OdooDunningPages();
        Assert.assertTrue("Dunning bundle is not pressent", odp.getBundleIdElement().isDisplayed());
    }

    @Then("^Dunning Instance State is \"([^\"]*)\"$")
    public void dunningInstanceStatus(String expectedState) throws Exception{
        List<WebElement> dunningInstanceHB1Row = getHB1Row();
        int stateColumn = 2;
        String pageState = dunningInstanceHB1Row.get(stateColumn).getText();
        String message = String.format("Dunning instance state is not \"%s\"", expectedState);
        Assert.assertThat(message, pageState, equalTo(expectedState));
    }

    @Then("^Dunning Instance Description is \"([^\"]*)\"$")
    public void dunningInstanceDescription(String expectedDescription) throws Exception {
        List<WebElement> dunningInstanceHB1Row = getHB1Row();
        int descriptionColumn = 1;
        String pageDescription = dunningInstanceHB1Row.get(descriptionColumn).getText();
        String message = String.format("Dunning instance description is not \"%s\"", expectedDescription);
        Assert.assertThat(message, pageDescription, equalTo(expectedDescription));
    }

    @Then("Dunning Instance Cost Entry is filled in")
    public void dunningInstanceCostEntry() throws Exception{
        List<WebElement> dunningInstanceHB1Row = getHB1Row();
        int costEntryColumn = 4;
        String pageCostEntry = dunningInstanceHB1Row.get(costEntryColumn).getText();
        String message = "Dunning instance cost entry is not filled in";
        Assert.assertThat(message, pageCostEntry, notNullValue());
    }

    @Then("^Dunning Instance Letter State is \"([^\"]*)\"$")
    public void dunningInstanceLetterState(String expectedLetterState) throws Exception{
        List<WebElement> dunningInstanceHB1Row = getHB1Row();
        int letterStateColumn = 7;
        String pageLetterState = dunningInstanceHB1Row.get(letterStateColumn).getText();
        String message = String.format("Dunning instance letter state is not \"%s\"", expectedLetterState);
        Assert.assertThat(message, pageLetterState, equalTo(expectedLetterState));
    }

    @Then("^Dunning invoice number is same as \"([^\"]*)\"$")
    public void dunningInvoiceNumber(String invoiceNumber) {
        String dunningDWPInvoiceNumber = parameterProvider.getValueOrParameterAsString(invoiceNumber);
        String message = String.format("Dunning invoice number is not \"%s\"", dunningDWPInvoiceNumber);
        String dunningOdooInvoiceNumber = new OdooDunningPages().getDunningInvoiceNumber().getText();

        Assert.assertThat(message, dunningOdooInvoiceNumber.substring(0, dunningOdooInvoiceNumber.indexOf(" (")), equalTo(dunningDWPInvoiceNumber));
    }

    private List<WebElement> getHB1Row() throws Exception {
        List<Filter> filters = Collections.singletonList(new Filter("DESCRIPTION", "HB1"));
        return new DunningInstancePage().selectRowOnTable("", filters, parameterProvider.getCurrentContextParameters());
    }

    @Override
    @After("@ODOO or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
