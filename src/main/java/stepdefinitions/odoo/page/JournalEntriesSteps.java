package stepdefinitions.odoo.page;

import com.essent.testing.odoo.pageobject.impl.page.JournalEnteriesPage;
import com.essent.testing.odoo.scenario.OdooScenario;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;

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
        je.ClickOnCreateJournalEntery();
    }

    @And("^Journal is \"([^\"]*)\"$")
    public void journalIs(String journal) throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        je.ChooseDiverseDagboekKlanten(journal);
    }

    @And("^Date document is now$")
    public void dateDocumentIsNow() throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        je.DateDocumentIsToday();
    }

    @And("^New item is$")
    public void newItemIs(DataTable dbTabel) throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        List<List<String>> db = dbTabel.raw();
        je.ClickOnAddAnItem();
        je.createNewItem(db,1);
        je.ClickOnAddAnItem();
        je.createNewItem(db,2);
    }

    @And("^Save and Post journal entry")
    public void saveAndPost() throws Throwable {
        JournalEnteriesPage je = new JournalEnteriesPage(webDriver);
        je.SaveJournal();
        je.PostJournal();
    }
}
