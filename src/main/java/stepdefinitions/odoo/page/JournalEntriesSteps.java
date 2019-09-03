package stepdefinitions.odoo.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.odoo.pageobject.impl.page.JournalEntriesPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import io.cucumber.datatable.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class JournalEntriesSteps extends OdooScenario {

    @Before("@ODOO or @B2B or @REGRESSION")
    public void setupTest(Scenario scenario){
        registerActiveScenario(scenario);
    }

    @Override
    @After("@ODOO or @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Create new Journal Entries is clicked$")
    public void createNewJournalEntriesIsClicked() {
        JournalEntriesPage je = new JournalEntriesPage();
        je.clickOnCreateJournalEntry();
    }

    @And("^Journal is \"([^\"]*)\"$")
    public void journalIs(String journal) {
        JournalEntriesPage je = new JournalEntriesPage();
        je.chooseDiverseDagboekKlanten(journal);
    }

    @And("^Date document is now$")
    public void dateDocumentIsNow() {
        JournalEntriesPage je = new JournalEntriesPage();
        je.dateDocumentIsToday();
    }

    @And("^New item is$")
    public void newItemIs(DataTable dbTable) {
        awaitOdooRequestToFinish(10);
        JournalEntriesPage je = new JournalEntriesPage();
        List<List<String>> db = dbTable.asLists();
        je.clickOnAddAnItem();
        je.createNewItem(db,1,parameterProvider.getValueOrParameterAsString(db.get(1).get(1)));
        Sleeper.sleepTightInSeconds(1);
        je.clickOnAddAnItem();
        je.createNewItem(db,2,parameterProvider.getValueOrParameterAsString(db.get(2).get(1)));
    }

    @And("^Save journal entry")
    public void saveJornalEntery() {
        awaitOdooRequestToFinish(10);
        JournalEntriesPage je = new JournalEntriesPage();
        je.saveJournal();
    }

    @And("^Mark first two journal items one with credit and one with debit \"([^\"]*)\"$")
    public void markFirstTwoJournalItemsOneWithCreditAndOneWithDebit(String money) {
        JournalEntriesPage je = new JournalEntriesPage();
        je.clickOnJournalItemsCheckBox(1);
        je.clickOnJournalItemsCheckBox(2);
    }

    @And("^More menu is \"([^\"]*)\"$")
    public void moreMenuIs(String item) {
        awaitOdooRequestToFinish(10);
        JournalEntriesPage je = new JournalEntriesPage();
        je.clickOnMoreMenuItem(item);
    }

    @And("^Confirm action$")
    public void confirm() {
        awaitOdooRequestToFinish(10);
        JournalEntriesPage je = new JournalEntriesPage();
        je.clickOnConfirm();
    }

    @Then("^Reconcile number is shown$")
    public void reconcileNumberIsShown() {
        JournalEntriesPage je = new JournalEntriesPage();
        String reconcile1 = je.reconcileText(1);
        String reconcile2 = je.reconcileText(2);
        Assert.assertTrue(reconcile1.equalsIgnoreCase(reconcile2));
    }

    @Then("^Reconcile number is removed$")
    public void reconcileNumberIsRemoved() {
        ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-field='reconcile_id'])[1]/a")));
    }

}
