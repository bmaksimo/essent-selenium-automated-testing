package stepdefinitions.odoo.navigation.menu;

import com.essent.testing.odoo.navigation.menu.MenuNavigation;
import com.essent.testing.odoo.scenario.OdooScenario;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class OdooMenu extends OdooScenario {

    @Before("@ODOO, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo top menu is ([^\"]*)$")
    public void clickTopMenu(String menu) throws Throwable {
        MenuNavigation menuuNavigation = new MenuNavigation(webDriver);
        boolean success = menuuNavigation.findAndClickMainMenuItem(menu);
        if(! success) {
            throw new CucumberException(menuuNavigation.getReason());
        }
    }

    @When("^Odoo left menu is ([^\"]*)$")
    public void OdooCheckTopMenuAction(String menuPath) throws Throwable {
        MenuNavigation odooMenuNavigation = new MenuNavigation(webDriver);
        odooMenuNavigation.executeAction(menuPath);
    }

    @Then("^The value in the column \"([^\"]*)\" of the \"([^\"]*)\" row is \"([^\"]*)\"$")
    public void OdooCheckValueInColumn(String column, String ordinal, String value) throws Throwable {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");

        OdooMenuList odooMenuList = new OdooMenuList();
        Map menuMap = odooMenuList.getMenu();

        SeleniumDriver seleniumDriver = new SeleniumDriver();
        MenuNavigation check = new MenuNavigation(webDriver);
        By xpathCheck = By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+rowIndex+"]//td[@data-field='"+odooMenuList.getKey(menuMap, column)+"'][1]");

        String cellText = check.findElementWhenVisible(xpathCheck).getText();

        if(cellText.equals(value)) {
            return;
        } else {
            throw new CucumberException("The value does not match the expected one");
        }

    }


    @Override
    @After("@ODOO, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
