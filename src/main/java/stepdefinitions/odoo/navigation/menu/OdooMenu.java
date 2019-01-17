package stepdefinitions.odoo.navigation.menu;

import com.essent.testing.odoo.navigation.menu.MenuNavigation;
import com.essent.testing.odoo.pageobject.impl.elements.ButtonImpl;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.hamcrest.MatcherAssert.assertThat;

public class OdooMenu extends OdooScenario {

    @Before("@ODOO, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo top menu is \"([^\"]*)\"$")
    public void clickTopMenu(String menu) {
        MenuNavigation menuNavigation = new MenuNavigation(webDriver);
        boolean success = menuNavigation.findAndClickMainMenuItem(menu);
        if(! success) {
            throw new CucumberException(menuNavigation.getReason());
        }
    }

    @When("^Odoo left menu is \"([^\"]*)\"$")
    public void executeLeftMenuAction(String menuPath) {
        MenuNavigation odooMenuNavigation = new MenuNavigation(webDriver);
        odooMenuNavigation.executeAction(menuPath);
    }



    @Then("^Generate CODA in the \"([^\"]*)\" row is clicked$")
    public void clickCodaUrl(String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        awaitOdooRequestToFinish(10);
        WebElement button = webDriver.findElementWhenVisible(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+rowIndex+"]//td[@data-field='generate_coda']//button[1]"));
        if (null == button) throw new CucumberException("Button was not found");

        moveToElementAndClick(button, webDriver.getDriver());
    }

    @Then("^Button \"([^\"]*)\" is clicked$")
    public void clickButton(String label) {
        awaitOdooRequestToFinish(10);
        WebElement webElement = webDriver.findElement(By.xpath("//button//div[contains(., '" + label + "')]"));
        if (null == webElement) throw new CucumberException("Button was not found");
        new ButtonImpl(webElement).click();
    }

    @Then("^Modal title contains \"([^\"]*)\"$")
    public void odooContainsModalTitle(String modalTitle) {
        WebElement title = webDriver.findElementWhenVisible(By.xpath("//h3[@class='modal-title']"));
        if (null == title || StringUtils.isBlank(title.getText())) throw new CucumberException("Title was not found");
        assertThat("Current title does not contain " + modalTitle, title.getText().contains(modalTitle));
    }

    @Then("^Modal button \"([^\"]*)\" is clicked$")
    public void odooClickButton(String buttonLabel) {
        WebElement button = webDriver.findElementWhenVisible(By.xpath("//button//span[contains(., '" + buttonLabel + "')]"));
        if (null == button) throw new CucumberException("Button " + buttonLabel + " was not found.");

        button.click();
    }

    @Override
    @After("@ODOO, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
