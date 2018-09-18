package stepdefinitions.odoo.menu;

import com.essent.testing.odoo.scenario.OdooScenario;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import stepdefinitions.odoo.navigation.OdooNavigationElements;

import java.time.Duration;

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
