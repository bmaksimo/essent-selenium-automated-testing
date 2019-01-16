package stepdefinitions.odoo.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.odoo.pageobject.impl.page.JournalEnteriesPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.bcel.generic.ClassGen;
import org.junit.Assert;

import java.util.List;

public class JournalEntriesSteps extends OdooScenario {

    @Before("@ODOO,@B2B, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @Override
    @After("@ODOO, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }

    @And("^Create new Journal Entries is clicked$")
    public void createNewJournalEntriesIsClicked() throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        Sleeper.sleepTightInSeconds(2);
        je.clickOnCreateJournalEntery();
    }

    @And("^Journal is \"([^\"]*)\"$")
    public void journalIs(String journal) throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        je.chooseDiverseDagboekKlanten(journal);
    }

    @And("^Date document is now$")
    public void dateDocumentIsNow() throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        je.dateDocumentIsToday();
    }

    @And("^New item is$")
    public void newItemIs(DataTable dbTabel) throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        List<List<String>> db = dbTabel.raw();
        je.clickOnAddAnItem();
        je.createNewItem(db,1);
        je.clickOnAddAnItem();
        je.createNewItem(db,2);
    }

    @And("^Save and Post journal entry")
    public void saveAndPost() throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        Sleeper.sleepTightInSeconds(5);
        je.saveJournal();
        Sleeper.sleepTightInSeconds(5);
        je.postJournal();
    }

    @And("^Mark first two journal items one with credit and one with debit \"([^\"]*)\"$")
    public void markFirstTwoJournalItemsOneWithCreditAndOneWithDebit(String money) throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        //Assert.assertEquals(je.journalImtesDebit(1), money);
        je.clickOnJournalItemsCheckBox(1); //81
       // Assert.assertEquals(je.journalImtesCredit(2), money);
        je.clickOnJournalItemsCheckBox(2); //82
    }

    @And("^More menu is \"([^\"]*)\"$")
    public void moreMenuIs(String item) throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        je.clickOnMoreMenuItem(item);
    }

    @And("^Confirm$")
    public void confirm() throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        je.clickOnconfirm();
    }

    @Then("^Reconcile number is shown$")
    public void reconcileNumberIsShown() throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        String reconcile1 = je.reconcileText(1);
        String reconcile2 = je.reconcileText(2);
        Assert.assertEquals(reconcile1,reconcile2);
    }

    @Then("^Reconcile number is removed$")
    public void reconcileNumberIsRemoved() throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        Sleeper.sleepTightInSeconds(5);

        Assert.assertFalse(je.isElementPresent("(//td[@data-field='reconcile_id'])[1]/a"));
        Assert.assertFalse(je.isElementPresent("(//td[@data-field='reconcile_id'])[2]/a"));


//        Assert.assertFalse(je.reconcile(1).isDisplayed());
//        Assert.assertFalse(je.reconcile(2).isDisplayed());


//        Assert.assertFalse(ExpectedConditions.visibilityOf(je.reconcile(1));
//        Assert.assertFalse(ExpectedConditions.visibilityOf(je.reconcile(2));

//        String reconcile11 = je.reconcileText(1);
//        String reconcile22 = je.reconcileText(2);
//        Assert.assertTrue(reconcile11.isEmpty());
//        Assert.assertTrue(reconcile22.isEmpty());

    }
}
