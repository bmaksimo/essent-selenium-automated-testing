package stepdefinitions.dwp.contracts.b2c;

import com.billinghouse.test_automation.util.dsl.DwpDateTimeFormat;
import com.billinghouse.test_automation.util.dsl.IntervalUtil;
import com.essent.testing.dwp.pageobject.ViewList;
import com.essent.testing.dwp.pageobject.list_view.ViewListTestObject;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import org.hamcrest.Matchers;
import org.joda.time.Interval;

import java.util.List;

import static com.billinghouse.MatcherAssert.assertThat;

public class ContractRenewalSteps extends DwpScenario {

    @Before("@DWP, @B2C, @E2E, @REGRESSION")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

    @And("^All date values at column \"([^\"]*)\" from table \"([^\"]*)\" are within the period \"([^\"]*)\"$")
    public void checkProductValidnessPeriod(String column, String table, String intervalParameter) throws Throwable {
        String periodOfRenewal = parameterProvider.getValueOrParameterAsString(intervalParameter);
        ViewList viewList = new ViewListTestObject();
        List<String> dateValues = viewList.fetchColumnData(table, column);
        int count = (int) dateValues.stream().filter(date -> IntervalUtil.containsDate(periodOfRenewal, date, DwpDateTimeFormat.DWP_PRODUCT_VALIDNESS_DATE_FORMAT)).count();
        assertThat(String.format("Date(s) at column  \"%s\" in table \"%s\" are not within the period \"%s\"",
            column, table, periodOfRenewal), dateValues, Matchers.hasSize(count));
    }


    @Override
    @After("@DWP, @B2C, @E2E, @REGRESSION")
    public void tearDown() {
        super.tearDown();
    }
}
