package stepdefinitions.odoo.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.odoo.pageobject.impl.page.JournalEnteriesPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
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
        je.saveJournal();
        je.postJournal();
    }

    @And("^Mark first two journal items one with credit and one with debit \"([^\"]*)\"$")
    public void markFirstTwoJournalItemsOneWithCreditAndOneWithDebit(String money) throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        Assert.assertEquals(je.journalImtesDebit(81), money);
        je.clickOnJournalItemsCheckBox(81);
        Assert.assertEquals(je.journalImtesCredit(82), money);
        je.clickOnJournalItemsCheckBox(82);
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
}
