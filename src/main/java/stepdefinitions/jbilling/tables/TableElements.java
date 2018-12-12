package stepdefinitions.jbilling.tables;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Assert;

import com.essent.testing.dwp.scenario.DwpScenario;
import com.essent.testing.jbilling.pageobject.impl.table.TablePage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class TableElements extends DwpScenario {

	@Before("@JBILLING, @B2B, @REGRESSION")
	public void setupTest(Scenario scenario) throws Throwable {
		registerActiveScenario(scenario);
	}

	@When("JBilling Click on row in Table by entering first cell value \"([^\"]*)\"$")
	public void clickFirstCellInTable(String firstCellValue) throws Throwable {
		String value = parameterProvider.getValueOrParameterAsString(firstCellValue);
		
		TablePage tablePage = new TablePage(webDriver);
		tablePage.clickFirstCellInTable(value);
	}
	
	@When("JBilling Click on row \"([^\"]*)\" in Table$")
	public void clickOnRowInTable(String rowNumber) throws Throwable {
		TablePage tablePage = new TablePage(webDriver);
		tablePage.clickOnRowInTable(rowNumber);
	}
	
	@When("JBilling Click on text link \"([^\"]*)\"$")
	public void clickTextLink(String label) throws Throwable {
		TablePage tablePage = new TablePage(webDriver);
		tablePage.clickTextLink(label);
	}
	
	@When("JBilling First cell value in first row is \"([^\"]*)\"$")
	public void checkFirstCellValueInFirstRow(String invoiceNameDWP) throws Throwable {
		String expectedResult = parameterProvider.getValueOrParameterAsString(invoiceNameDWP);
		
		TablePage tablePage = new TablePage(webDriver);
		String actualResult = tablePage.checkFirstCellValueInFirstRow();
        Assert.assertTrue(actualResult.equalsIgnoreCase(expectedResult));
        
        assertThat("Invoice " + expectedResult + " is not shown in table", actualResult.equalsIgnoreCase(expectedResult), is(true));

	}
	
	@When("JBilling label \"([^\"]*)\" contains value \"([^\"]*)\" at column \"([^\"]*)\"$")
	public void checkValueNextToLabel(String label, String expectedValue, String columnNumber) throws Throwable {
		String expectedResult = parameterProvider.getValueOrParameterAsString(expectedValue);
		
		TablePage tablePage = new TablePage(webDriver);
		String actualResult = tablePage.checkValueNextToLabel(label, columnNumber);
		assertThat("Value " + expectedResult + " is not shown next to label " + label, actualResult.equalsIgnoreCase(expectedResult), is(true));
	}
	
	@When("Inner tables are not empty$")
	public void checkInnerOrderTablesNotEmpty() throws Throwable {
		TablePage tablePage = new TablePage(webDriver);
		boolean isNotEmpty = tablePage.checkInnerTablesNotEmpty();
		
		assertThat("Rows in inner tables are empty", isNotEmpty, is(true));
	}
	
	@Override
	@After("@JBILLING, @B2B, @REGRESSION")
	public void tearDown() {
		super.tearDown();
	}

}
