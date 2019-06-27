package stepdefinitions.odoo.navigation.menu;

import com.essent.automation.util.Sleeper;
import com.essent.testing.odoo.navigation.menu.MenuNavigation;
import com.essent.testing.odoo.pageobject.impl.elements.ButtonImpl;
import com.essent.testing.odoo.pageobject.impl.pageObject.CustomerPage;
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
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.awaitility.Duration.TWO_SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;


public class OdooMenu extends OdooScenario {

    @Before("@ODOO or @E2E or @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^Odoo top menu is \"([^\"]*)\"$")
    public void clickTopMenu(String menu) {
        MenuNavigation menuNavigation = new MenuNavigation();
        boolean success = menuNavigation.findAndClickMainMenuItem(menu);
        if(!success) {
            throw new CucumberException(menuNavigation.getReason());
        }
        awaitOdooRequestToFinish(180);
    }

    @When("^Odoo left menu is \"([^\"]*)\"$")
    public void executeLeftMenuAction(String menuPath) {
        Sleeper.sleepTightInSeconds(3);
        MenuNavigation odooMenuNavigation = new MenuNavigation();
        odooMenuNavigation.executeAction(menuPath);
        awaitOdooRequestToFinish(120);
    }



    @Then("^Generate CODA in the \"([^\"]*)\" row is clicked$")
    public void clickCodaUrl(String ordinal) {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        awaitOdooRequestToFinish(10);
        WebElement button = seleniumDriver.findElementWhenVisible(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr["+rowIndex+"]//td[@data-field='generate_coda']//button[1]"));
        if (null == button) throw new CucumberException("Button was not found");

        seleniumDriver.moveToElementAndClick(button);
    }

    @Then("^Generate CODA in the first row with \"([^\"]*)\" is clicked$")
    public void clickDownloadCoda(String value) {
        String locator = "//tr[td[text() = '${value}']]/td//button[@title='Download coda']";
        HashMap<String, String> mapper = new HashMap<>();
        mapper.put("value", value);
        awaitOdooRequestToFinish(10);
        List<WebElement> buttons = seleniumDriver.findElements(By.xpath(createQuery(locator, mapper)));
        if(buttons.isEmpty()) {
            throw new CucumberException("Coda download button was not found");
        } else {
            seleniumDriver.moveToElementAndClick(buttons.get(0));
        }
    }


    @Then("^Button \"([^\"]*)\" is clicked$")
    public void clickButton(String label) {
        WebElement webElement = seleniumDriver.findElement(By.xpath("//button//div[contains(., '" + label + "')]"));
        if (null == webElement) throw new CucumberException("Button was not found");
        new ButtonImpl(webElement).click();
        awaitOdooRequestToFinish(180);
    }

    @Then("^Modal title contains \"([^\"]*)\"$")
    public void odooContainsModalTitle(String modalTitle) {
        WebElement title = seleniumDriver.findElementWhenVisible(By.xpath("//h3[@class='modal-title']"));
        if (null == title || StringUtils.isBlank(title.getText())) throw new CucumberException("Title was not found");
        assertThat("Current title does not contain " + modalTitle, title.getText().contains(modalTitle));
    }

    @Then("^Modal button \"([^\"]*)\" is clicked$")
    public void odooClickButton(String buttonLabel) {
        awaitOdooRequestToFinish(10);
        WebElement button = seleniumDriver.findElementWhenVisible(By.xpath("//button//span[contains(., '" + buttonLabel + "')]"));
        if (null == button) throw new CucumberException("Button " + buttonLabel + " was not found.");
        button.click();
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
        CustomerPage cp = new CustomerPage();
        cp.openJournalEntry();
    }

   @And("^Modal button \"([^\"]*)\" clicked$")
   public void modalButtons(String name) {
       CustomerPage cp = new CustomerPage();
       cp.reversePaymentPlan();
   }

    @And("^Odoo click on tab \"([^\"]*)\"$")
    public void odooClickOnTab(String tab){
        CustomerPage cp = new CustomerPage();
        cp.clickOnTabMenu(tab);
    }

    @Then("^Odoo validate bank account was changed on \"([^\"]*)\"$")
    public void odooValidateBankAccountWasChangedOn(String ban) {
        String bankAccountNumber = parameterProvider.getValueOrParameterAsString(ban);
        CustomerPage cp = new CustomerPage();
        Assert.assertTrue("Check if band account number is same as in DWP",cp.getBankAccountAsString().equalsIgnoreCase(bankAccountNumber));
    }

    @Then("^Odoo verify payment method has changed to \"([^\"]*)\"$")
    public void odooVerifyPaymentMethodChanged(String pm) {
        String paymentMethod = parameterProvider.getValueOrParameterAsString(pm);
        CustomerPage cp = new CustomerPage();
        Assert.assertTrue("Check if payment method is same as in DWP",cp.getPaymentMethodAsString().equalsIgnoreCase(paymentMethod));
    }


    @Override
    @After("@ODOO or @E2E or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
