package stepdefinitions.odoo.menu;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.odoo.navigation.OdooNavigationElements;

public class OdooTopMenu extends OdooNavigationElements {

    @Before("@CODA")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo top menu is ([^\"]*)$")
    public void OdooCheckTopMenuAction(String menu) throws Throwable {
        By xpath = By.xpath("//*[@id=\"oe_main_menu_placeholder\"]/ul[1]/li[4]/a");
        WebElement element = webDriver.findElementOrNull(xpath);
        element.click();
    }
}
