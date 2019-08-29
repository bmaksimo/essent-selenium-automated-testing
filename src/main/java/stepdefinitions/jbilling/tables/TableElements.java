package stepdefinitions.jbilling.tables;

import com.essent.testing.jbilling.pageobject.impl.table.TablePage;
import com.essent.testing.jbilling.scenario.JBillingScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TableElements extends JBillingScenario {

	@Before("@JBILLING or @B2B or @REGRESSION")
	public void setupTest(Scenario scenario){
		registerActiveScenario(scenario);
	}

	@When("^JBilling Click on row in Table by entering first cell value \"([^\"]*)\"$")
	public void clickFirstCellInTable(String expectedResult){
        if(expectedResult.startsWith("parameter:")) {
            expectedResult = parameterProvider.getValueOrParameterAsString(expectedResult);
        }

		TablePage tablePage = new TablePage();
		boolean success = tablePage.clickFirstCellInTable(expectedResult);

		assertThat("First cell in table is not clicked", success, is(true));
	}

	@When("^JBilling Click on row \"([^\"]*)\" in Table$")
	public void clickOnRowInTable(String rowNumber){
		TablePage tablePage = new TablePage();
		boolean success = tablePage.clickOnRowInTable(rowNumber);

		assertThat("Row: " + rowNumber + " in the table is not clicked", success, is(true));
	}

	@When("^JBilling Click on text link \"([^\"]*)\"$")
	public void clickTextLink(String label){
		TablePage tablePage = new TablePage();
		boolean success = tablePage.clickTextLink(label);

		assertThat("Text link: " + label + " is not clicked", success, is(true));
	}

	@When("^JBilling First cell value in first row is \"([^\"]*)\"$")
	public void checkFirstCellValueInFirstRow(String expectedResult){
        if(expectedResult.startsWith("parameter:")) {
            expectedResult = parameterProvider.getValueOrParameterAsString(expectedResult);
        }

		TablePage tablePage = new TablePage();
		String actualResult = tablePage.checkFirstCellValueInFirstRow();

        assertThat("Invoice " + expectedResult + " is not shown in table. Invoice shown in jbilling is: " + actualResult, actualResult.equalsIgnoreCase(expectedResult), is(true));

	}

	@When("^JBilling Value next to label \"([^\"]*)\" is \"([^\"]*)\"$")
	public void checkValueNextToLabel(String label, String expectedResult){
		if(expectedResult.startsWith("parameter:")) {
			expectedResult = parameterProvider.getValueOrParameterAsString(expectedResult);
		}

		TablePage tablePage = new TablePage();
		String actualResult = tablePage.checkValueNextToLabel(label);
		assertThat("Value " + expectedResult + " is not shown next to label " + label, actualResult.equalsIgnoreCase(expectedResult), is(true));
	}

	@When("^Inner tables are not empty$")
	public void checkInnerOrderTablesNotEmpty(){
		TablePage tablePage = new TablePage();
		boolean isNotEmpty = tablePage.checkInnerTablesNotEmpty();

		assertThat("Rows in inner tables are empty", isNotEmpty, is(true));
	}

	@Override
	@After("@JBILLING or @B2B or @REGRESSION")
	public void tearDown() {
		super.tearDown();
	}

}
