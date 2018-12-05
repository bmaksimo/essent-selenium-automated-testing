package stepdefinitions.odoo.navigation.menu;

import com.essent.testing.odoo.navigation.menu.MenuNavigation;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class OdooMenu extends OdooScenario {

    @Before("@ODOO, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo top menu is ([^\"]*)$")
    public void clickTopMenu(String menu) {
        MenuNavigation menuuNavigation = new MenuNavigation(webDriver);
        boolean success = menuuNavigation.findAndClickMainMenuItem(menu);
        if(! success) {
            throw new CucumberException(menuuNavigation.getReason());
        }
    }

    @When("^Odoo left menu is ([^\"]*)$")
    public void OdooCheckTopMenuAction(String menuPath) {
        MenuNavigation odooMenuNavigation = new MenuNavigation(webDriver);
        odooMenuNavigation.executeAction(menuPath);
    }

    @When("^Odoo filter is ([^\"]*)$")
    public void OdooFilter(String expression) {
        String filter = parameterProvider.getValueOrParameterAsString(expression) == null ?
            expression : parameterProvider.getValueOrParameterAsString(expression);
        awaitOdooRequestToFinish(5);
        List<WebElement> filterElements = webDriver.findElements(By.xpath("//div[@class='oe_searchview_input']"), Duration.ofSeconds(30), Duration.ofSeconds(5));
        if (filterElements.isEmpty()) throw new CucumberException("Button was not found");
        WebElement filterElement = filterElements.get(1);
        filterElement.click();
        filterElement.sendKeys(filter);
        filterElement.sendKeys(Keys.RETURN);
    }

    @Then("^The value in the column \"([^\"]*)\" of the \"([^\"]*)\" row is \"([^\"]*)\"$")
    public void OdooCheckValueInColumn(String column, String ordinal, String value) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");

        Map<String, String> menuMap = OdooMenuList.menuMap;

        MenuNavigation check = new MenuNavigation(webDriver);
        By xpathCheck = By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+rowIndex+"]//td[@data-field='"+OdooMenuList.getKey(menuMap, column)+"'][1]");

        awaitOdooRequestToFinish(5);
        String cellText = check.findElementWhenVisible(xpathCheck).getText();

        if (!value.equals(cellText)) throw new CucumberException("The value does not match the expected one");

    }

    @Then("^The value in the column \"([^\"]*)\" is \"([^\"]*)\"$")
    public void OdooCheckValueInColumn(String column, String value) {
        List<WebElement> rows = webDriver.findElements(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr"));
        WebElement currentRow;
        for (int i = 1; i <= rows.size(); i++) {
            currentRow = webDriver.findElement(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+i+"]//td[@data-field='"+OdooMenuList.getKey(OdooMenuList.menuMap, column)+"'][1]"));
            if (currentRow != null && value.equalsIgnoreCase(currentRow.getText())) {
                return;
            }
        }
        throw new CucumberException("Row was not found");
    }

    @Then("^Column \"([^\"]*)\" of the \"([^\"]*)\" row is clicked$")
    public void odooClickValueInColumn(String column, String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        awaitOdooRequestToFinish(2);
        List<WebElement> rows = webDriver.findElements(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+rowIndex+"]//td[@data-field='"+OdooMenuList.getKey(OdooMenuList.menuMap, column)+"'][1]"));
        awaitOdooRequestToFinish(5);
        if (CollectionUtils.isEmpty(rows)) throw new CucumberException("Row was not found");

        rows.get(rows.size()-1).click();
    }

    @Then("^Column \"([^\"]*)\" with value \"([^\"]*)\" is clicked$")
    public void odooClickSpecificValueInColumn(String column, String value) {
        String input = parameterProvider.getValueOrParameterAsString(value) == null ?
            value : parameterProvider.getValueOrParameterAsString(value);
        awaitOdooRequestToFinish(10);
        List<WebElement> rows = webDriver.findElements(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr"));
        WebElement currentRow;
        for (int i = 1; i <= rows.size(); i++) {
            currentRow = webDriver.findElement(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+i+"]//td[@data-field='"+OdooMenuList.getKey(OdooMenuList.menuMap, column)+"'][1]"));
            if (currentRow != null && input.equalsIgnoreCase(currentRow.getText())) {
                currentRow.click();
                awaitOdooRequestToFinish(10);
                return;
            }
        }
        throw new CucumberException("Row was not found");
    }

    @Then("^Generate CODA in the \"([^\"]*)\" row is clicked$")
    public void odooClickGenerateCoda(String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        awaitOdooRequestToFinish(10);
        WebElement button = webDriver.findElement(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+rowIndex+"]//td[@data-field='generate_coda']//button[1]"));
        if (null == button) throw new CucumberException("Button was not found");
        button.click();
    }

    @Then("^Button \"([^\"]*)\" is clicked$")
    public void odooButtonClick(String label) {
        awaitOdooRequestToFinish(10);
        WebElement button = webDriver.findElement(By.xpath("//button//div[contains(., '" + label + "')]"));
        if (null == button) throw new CucumberException("Button was not found");
        button.click();
    }

    @Then("^Modal title contains \"([^\"]*)\"$")
    public void odooContainsModalTitle(String modalTitle) {
        WebElement title = webDriver.findElement(By.xpath("//h3[@class='modal-title']"));
        if (null == title || StringUtils.isBlank(title.getText())) throw new CucumberException("Title was not found");
        assertThat("Current title does not contain " + modalTitle, title.getText().contains(modalTitle));
    }

    @Then("^Modal button \"([^\"]*)\" is clicked$")
    public void odooClickButton(String buttonLabel) {
        WebElement button = webDriver.findElement(By.xpath("//button//span[contains(., '" + buttonLabel + "')]"));
        if (null == button) throw new CucumberException("Button " + buttonLabel + " was not found.");

        button.click();
    }

    @Override
    @After("@ODOO, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
