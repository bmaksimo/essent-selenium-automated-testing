package stepdefinitions.odoo.navigation.table;

import com.billinghouse.exception.ExtendedCucumberException;
import com.essent.automation.util.Sleeper;
import com.essent.testing.odoo.pageobject.elements.ListView;
import com.essent.testing.odoo.pageobject.impl.elements.DefaultListView;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import stepdefinitions.odoo.navigation.search.AdvancedSearch;
import stepdefinitions.odoo.navigation.search.AdvancedSearchComponent;

import java.time.Duration;
import java.util.List;

public class OdooListView extends OdooScenario  {

    private static final String TABLE_CELL_SELECTOR_TEMPLATE = "//table[@class='oe_list_content'][1]//tbody//tr[${rowIndex}]//td[@data-field='${key}'][1]";


    @Before("@ODOO, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo filter is \"([^\"]*)\"$")
    public void setAdvancedSearchFilter(String expression) {
        String filter = parameterProvider.getValueOrParameterAsString(expression) == null ?
            expression : parameterProvider.getValueOrParameterAsString(expression);
        String selector = "//div[@class='oe_searchview_input']";
        By xpath = By.xpath(selector);
        List<WebElement> filterElements = seleniumDriver.findElements(xpath,
            Duration.ofSeconds(30),
            Duration.ofSeconds(5));
        if (filterElements.isEmpty()) throw new ExtendedCucumberException("Button was not found");

        WebElement filterElement = filterElements.get(1);
        filterElement.click();
        filterElement.sendKeys(filter);
        filterElement.sendKeys(Keys.RETURN);
        awaitOdooRequestToFinish(600);
    }

    @When("^Advanced search is$")
    public void setAdvancedSearchFilter(DataTable dbTable) {
        List<List<String>> list = dbTable.raw();
        String searchParameter = parameterProvider.getValueOrParameterAsString(list.get(1).get(2));
        AdvancedSearch advancedSearch = new AdvancedSearch(list.get(1).get(0), list.get(1).get(1), searchParameter);
        awaitOdooRequestToFinish(10);
        new AdvancedSearchComponent().runAdvancedSearch(advancedSearch);
    }

    @Then("^The value in the column \"([^\"]*)\" of the \"([^\"]*)\" row is \"([^\"]*)\"$")
    public void checkCellAt(String column, String ordinal, String value) {
        String input = parameterProvider.getValueOrParameterAsString(value) == null ?
            value : parameterProvider.getValueOrParameterAsString(value);
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        ListView odooList = new DefaultListView();
        odooList.checkCellAt(column, rowIndex, input);


    }

    @Then("^Column \"([^\"]*)\" of the \"([^\"]*)\" row is clicked$")
    public void clickCellAt(String column, String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        ListView odooList = new DefaultListView();
        odooList.clickCellAt(column, rowIndex);
    }

    @Then("^Column \"([^\"]*)\" with value \"([^\"]*)\" is clicked$")
    public void clickValueAt(String column, String value) {
        awaitOdooRequestToFinish(60);
        String input = parameterProvider.getValueOrParameterAsString(value) == null ?
            value : parameterProvider.getValueOrParameterAsString(value);
        ListView odooList = new DefaultListView();
        Sleeper.sleepTightInSeconds(5);
        odooList.clickValueAt(column, input);
        awaitOdooRequestToFinish(180);
    }

    @Then("^The value in the column \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkValueAt(String column, String value) {
        String input = parameterProvider.getValueOrParameterAsString(value) == null ?
            value : parameterProvider.getValueOrParameterAsString(value);
        ListView odooList = new DefaultListView();
        odooList.checkValueAt(column, input);
    }

    @Override
    @After("@ODOO, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
