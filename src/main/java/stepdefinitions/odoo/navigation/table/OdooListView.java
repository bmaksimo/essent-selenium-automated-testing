package stepdefinitions.odoo.navigation.table;

import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import stepdefinitions.odoo.navigation.menu.OdooMenuList;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static stepdefinitions.odoo.navigation.menu.OdooMenuList.getKey;

public class OdooListView extends OdooScenario  {

    private static final String TABLE_CELL_SELECTOR_TEMPLATE = "//table[@class='oe_list_content'][1]//tbody//tr[${rowIndex}]//td[@data-field='${key}'][1]";

    @Before("@ODOO, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo filter is ([^\"]*)$")
    public void setSearchFilter(String expression) {

        String filter = parameterProvider.getValueOrParameterAsString(expression) == null ?
            expression : parameterProvider.getValueOrParameterAsString(expression);
        awaitOdooRequestToFinish(10);
        String selector = "//div[@class='oe_searchview_input']";

        By xpath = By.xpath(selector);
        List<WebElement> filterElements = webDriver.findElements(xpath,
            Duration.ofSeconds(30),
            Duration.ofSeconds(5));
        if (filterElements.isEmpty()) throw new CucumberException("Button was not found");

        WebElement filterElement = filterElements.get(1);
        filterElement.click();
        filterElement.sendKeys(filter);
        filterElement.sendKeys(Keys.RETURN);
    }

    @Then("^The value in the column \"([^\"]*)\" of the \"([^\"]*)\" row is \"([^\"]*)\"$")
    public void checkCellValue(String column, String ordinal, String value) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> valuesMapper = new HashMap<>();
        valuesMapper.put("rowIndex", rowIndex);
        valuesMapper.put("key", getKey(column));
        String query = createQuery(TABLE_CELL_SELECTOR_TEMPLATE, valuesMapper);
        By xpathCheck = By.xpath(query);
        awaitOdooRequestToFinish(5);
        WebElement elementWhenVisible = webDriver.findElementWhenVisible(xpathCheck);
        if (null == elementWhenVisible) throw new CucumberException("Table is not visible");
        String cellText = elementWhenVisible.getText();
        if (!value.equals(cellText)) throw new CucumberException("The value does not match the expected one");

    }

    @Then("^Column \"([^\"]*)\" of the \"([^\"]*)\" row is clicked$")
    public void clickOnCellData(String column, String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> valuesMapper = new HashMap<>();
        valuesMapper.put("rowIndex", rowIndex);
        valuesMapper.put("key", getKey(column));
        String query = createQuery(TABLE_CELL_SELECTOR_TEMPLATE, valuesMapper);
        By xpathCheck = By.xpath(query);
        awaitOdooRequestToFinish(5);
        List<WebElement> rows = webDriver.findElements(xpathCheck);
        awaitOdooRequestToFinish(5);
        if (CollectionUtils.isEmpty(rows)) throw new CucumberException("Row was not found");
        rows.get(rows.size()-1).click();
    }

    @Then("^Column \"([^\"]*)\" with value \"([^\"]*)\" is clicked$")
    public void findAndClickCellData(String column, String value) {
        String input = parameterProvider.getValueOrParameterAsString(value) == null ?
            value : parameterProvider.getValueOrParameterAsString(value);
        awaitOdooRequestToFinish(10);
        List<WebElement> rows = webDriver.findElements(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr"));
        WebElement currentRow;
        for (int i = 1; i <= rows.size(); i++) {
            currentRow = webDriver.findElement(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr[" + i + "]//td[@data-field='"
                + getKey(column)
                + "'][1]"));
            if (currentRow != null && input.equalsIgnoreCase(currentRow.getText())) {
                currentRow.click();
                awaitOdooRequestToFinish(10);
                return;
            }
        }
        throw new CucumberException("Row was not found");
    }

    @Then("^The value in the column \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkColumnContainsValue(String column, String value) {
        List<WebElement> rows = webDriver.findElements(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr"));
        WebElement currentRow;
        for (int i = 1; i <= rows.size(); i++) {
            currentRow = webDriver.findElementWhenVisible(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr[" + i + "]//td[@data-field='"
                + OdooMenuList.getKey(column)
                + "'][1]"));
            if (currentRow != null && value.equalsIgnoreCase(currentRow.getText())) {
                return;
            }
        }
        throw new CucumberException("Row was not found");
    }

    @Override
    @After("@ODOO, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

}
