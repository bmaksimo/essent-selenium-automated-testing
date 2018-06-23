package stepdefinitions.dwp.view;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import stepdefinitions.dwp.NavigationElements;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ViewListElements extends NavigationElements {

    @And("^Select \"([^\"]*)\" List rows at:$")
    public void selectListRowsAt(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    private class CheckViewListHeader implements Predicate<String> {
        @Override
        public boolean test(String header) {
            int sec = 7;
            Map<String, Object> options = new HashMap<>();
            options.put("schedule_seconds", sec);
            options.put("header", header);
            return executeJavascriptTest("TrCheckViewListHeader", options);
        }
    }

    private class GetTableModel {
        public DefaultTableModel getViewTableModel() {
            DefaultTableModel tableModel = new DefaultTableModel();
            String json = executeJavascriptMethod("TrGetTableModel", new HashMap<>());
            List columnNames = from(json).get("column_names");
            List rows =        from(json).get("rows");
            tableModel.setColumnIdentifiers(columnNames.toArray());
            for(int i = 0; i < rows.size(); i++) {
                Object[] row = ((List) rows.get(i)).toArray();
                tableModel.addRow(row);
            }
            return tableModel;
        }
    }

    private class ClickTableCellUrl implements Predicate<Map> {
        @Override
        public boolean test(Map options) {
            return executeJavascriptTest("TrClickTableCellUrl", options);
        }
    }

    @Before("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void SetupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @When("^View List Header is \"([^\"]*)\"$")
    public void checkViewListHeader(String header) throws Throwable {
        boolean success = new CheckViewListHeader().test(header);
        assertThat(String.format("View list did not contain heeder '%s'", header),
            success, is(true));
    }

    @When("^View List is empty$")
    public void checkTableModel() throws Throwable {
        TableModel viewTableModel = new GetTableModel().getViewTableModel();
        boolean success = viewTableModel.getRowCount() == 0;
        assertThat("View Table list is not empty",
            success, is(true));
    }

    @When("^Click on link in View List at ([^\"]*) row and \"([^\"]*)\" column$")
    public void clickOnViewListAtRowAndColumn(String ordinal, String column) throws Throwable {
        String rowIndex = ordinal.replaceAll("(?<=\\d)(rd|st|nd|th)\\b", "");
        Map<String, String> columnIndexListOptions = new HashMap<>();
        columnIndexListOptions.put("column", column);
        columnIndexListOptions.put("index", rowIndex);
        boolean success = new ClickTableCellUrl().test(columnIndexListOptions);
        assertThat(String.format("View list did not contain URL at row %s header '%s'", ordinal, column),
            success, is(true));
    }

    @And("^([^\"]*) List element with$")
    public void listElementWith(String ordinal) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Override
    @After("@SMOKE, @QUOTE, @MENU, @DWP_SETUP, @FILTER, @RENEWAL")
    public void tearDown() throws Exception {
        super.tearDown();
    }
}

