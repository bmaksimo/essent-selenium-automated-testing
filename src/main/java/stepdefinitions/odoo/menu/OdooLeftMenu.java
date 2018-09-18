package stepdefinitions.odoo.menu;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import stepdefinitions.odoo.navigation.OdooNavigationElements;

import java.time.Duration;

public class OdooLeftMenu extends OdooNavigationElements {

    @Before("@CODA")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo left menu is ([^\"]*)$")
    public void OdooCheckTopMenuAction(String menu) throws Throwable {
        if(
            menu.equals("Import CODA Files") ||
            menu.equals("Imported CODA Files") ||
            menu.equals("CODA Bank Statements") ||
            menu.equals("CODA Bank Statement lines")
        ) {
            By xpath = By.xpath("//span[normalize-space(text())='CODA Processing']/ancestor::a");
            WebElement codaElement = webDriver.findElementOrNull(xpath);
            FluentWait<WebDriver> waiter = new FluentWait<>(webDriver.getDriver()).withTimeout(Duration.ofSeconds(5));
            waiter.until(ExpectedConditions.elementToBeClickable(codaElement));
            codaElement.click();
        }

        WebElement element = webDriver.findElementOrNull(By.xpath("//span[normalize-space(text())='Import CODA Files']"));
        element.click();
    }
}
