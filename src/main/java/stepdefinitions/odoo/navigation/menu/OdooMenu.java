package stepdefinitions.odoo.navigation.menu;

import com.essent.testing.odoo.navigation.menu.MenuNavigation;
import com.essent.testing.odoo.pageobject.impl.elements.ButtonImpl;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang.StringUtils;
import org.awaitility.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.TWO_SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;

public class OdooMenu extends OdooScenario {

    @Before("@ODOO, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo top menu is \"([^\"]*)\"$")
    public void clickTopMenu(String menu) {
        awaitOdooRequestToFinish(10);
        MenuNavigation menuNavigation = new MenuNavigation();
        boolean success = menuNavigation.findAndClickMainMenuItem(menu);
        if(!success) {
            throw new CucumberException(menuNavigation.getReason());
        }
    }

    @When("^Odoo left menu is \"([^\"]*)\"$")
    public void executeLeftMenuAction(String menuPath) {
        awaitOdooRequestToFinish(20);
        MenuNavigation odooMenuNavigation = new MenuNavigation();
        odooMenuNavigation.executeAction(menuPath);
        awaitOdooRequestToFinish(5);
    }



    @Then("^Generate CODA in the \"([^\"]*)\" row is clicked$")
    public void clickCodaUrl(String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        awaitOdooRequestToFinish(10);
        WebElement button = seleniumDriver.findElementWhenVisible(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+rowIndex+"]//td[@data-field='generate_coda']//button[1]"));
        if (null == button) throw new CucumberException("Button was not found");

        seleniumDriver.moveToElementAndClick(button);
    }

    @Then("^Button \"([^\"]*)\" is clicked$")
    public void clickButton(String label) {
        awaitOdooRequestToFinish(10);
        WebElement webElement = seleniumDriver.findElement(By.xpath("//button//div[contains(., '" + label + "')]"));
        if (null == webElement) throw new CucumberException("Button was not found");
        new ButtonImpl(webElement).click();
    }

    @Then("^Modal title contains \"([^\"]*)\"$")
    public void odooContainsModalTitle(String modalTitle) {
        WebElement title = seleniumDriver.findElementWhenVisible(By.xpath("//h3[@class='modal-title']"));
        if (null == title || StringUtils.isBlank(title.getText())) throw new CucumberException("Title was not found");
        assertThat("Current title does not contain " + modalTitle, title.getText().contains(modalTitle));
    }

    @Then("^Modal button \"([^\"]*)\" is clicked$")
    public void odooClickButton(String buttonLabel) {
        WebElement button = seleniumDriver.findElementWhenVisible(By.xpath("//button//span[contains(., '" + buttonLabel + "')]"));
        if (null == button) throw new CucumberException("Button " + buttonLabel + " was not found.");

        //button.click();
    }

    @Then("^Bank Statement \"([^\"]*)\" button is clicked$")
    public void odooBankStatementClickButton(String buttonLabel) {
        given()
            .await()
            .ignoreExceptions()
            .pollInterval(new Duration(20, SECONDS))
            .pollDelay(TWO_SECONDS)
            .atMost(new Duration(300, SECONDS)).until(()-> inputBankStatementButtonProcessed(buttonLabel));
    }

    private boolean inputBankStatementButtonProcessed(String buttonLabel) {
        WebElement buttonAvailable = seleniumDriver.findElement(By.xpath("//span[contains(@attrs, 'False')]//button//span[contains(., '"+ buttonLabel +"')]"));
        if (null != buttonAvailable) {
            buttonAvailable.click();
            return true;
        }
        refreshCurrentPage();
        return false;
    }

    private void refreshCurrentPage() {
        seleniumDriver.getDriver().navigate().to(seleniumDriver.getDriver().getCurrentUrl());
        awaitOdooRequestToFinish(10);
    }

    @And("^Journal entry is open$")
    public void journalEntry() {
        awaitOdooRequestToFinish(3);

        WebElement journal = seleniumDriver.findElement(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr[1]//td[@data-field='move_id'][1]"));
        journal.click();
        awaitOdooRequestToFinish(3);
        WebElement move = seleniumDriver.findElement(By.xpath("//span[@data-fieldname='move_id']/a[@class='oe_m2o_cm_button oe_e']"));
        move.click();
        awaitOdooRequestToFinish(3);

    }

   @And("^Modal buttons \"([^\"]*)\" are clicked$")
   public void modalButtons(String name) {
       awaitOdooRequestToFinish(3);
       WebElement reverse1 = seleniumDriver.findElement(By.xpath("//header//button//span[contains(., '" + name + "')]"));
       if (null == reverse1) throw new CucumberException("Button was not found");
       new ButtonImpl(reverse1).click();

       awaitOdooRequestToFinish(3);
       WebElement reverse2 = seleniumDriver.findElement(By.xpath("//footer//button//span[contains(., '" + name + "')]"));
       if (null == reverse2) throw new CucumberException("Button was not found");
       new ButtonImpl(reverse2).click();
       awaitOdooRequestToFinish(8);
   }





    @Override
    @After("@ODOO, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
