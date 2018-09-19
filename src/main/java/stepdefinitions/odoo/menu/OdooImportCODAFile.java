package stepdefinitions.odoo.menu;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.odoo.navigation.OdooNavigationElements;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class OdooImportCODAFile extends OdooNavigationElements {
    @Before("@CODA")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo Import CODA File is Select")
    public void OdooCheckTopMenuAction() throws Throwable {
        By xpath = By.cssSelector(".oe_form_binary_file[name='ufile']");
        WebElement element = webDriver.findElementOrNull(xpath);
        element.click();
    }
}
