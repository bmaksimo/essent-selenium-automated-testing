package stepdefinitions.dwp.contracts.b2c;

import com.billinghouse.test_automation.util.dsl.DwpDateTimeFormat;
import com.billinghouse.test_automation.util.dsl.IntervalUtil;
import com.essent.testing.dwp.constant.ParameterKeys;
import com.essent.testing.dwp.pageobject.ViewList;
import com.essent.testing.dwp.pageobject.elements.NonEditable;
import com.essent.testing.dwp.pageobject.impl.elements.NonEditableImpl;
import com.essent.testing.dwp.pageobject.list_view.ViewListTestObject;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.runtime.CucumberException;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.openqa.selenium.support.ui.FluentWait;
import stepdefinitions.dwp.navigation.NavigationElements;
import stepdefinitions.dwp.tables.DwpArrows;
import stepdefinitions.dwp.view_list.ViewListChecks;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class ContractRenewalSteps extends NavigationElements {

  @Before("@DWP or @B2C or @E2E or @REGRESSION")
  public void setupTest(Scenario scenario){
    registerActiveScenario(scenario);
  }

  @And(
      "^All date values at column \"([^\"]*)\" from table \"([^\"]*)\" are within the period \"([^\"]*)\"$")
  public void checkProductValidnessPeriod(String column, String table, String intervalParameter){
    String periodOfRenewal = parameterProvider.getValueOrParameterAsString(intervalParameter);
    ViewList viewList = new ViewListTestObject();
    List<String> dateValues = viewList.fetchColumnData(table, column);
    int count =
        (int)
            dateValues.stream()
                .filter(
                    date ->
                        IntervalUtil.containsDate(
                            periodOfRenewal,
                            date,
                            DwpDateTimeFormat.DWP_PRODUCT_VALIDNESS_DATE_FORMAT))
                .count();
    assertThat(
        String.format(
            "Date(s) at column  \"%s\" in table \"%s\" are not within the period \"%s\"",
            column, table, periodOfRenewal),
        dateValues,
        Matchers.hasSize(count));
  }

  @And("^\"([^\"]*)\" field value is switched to \"([^\"]*)\" within (\\d+) seconds?$")
  public void checkFieldValue(String label, String expectedValue, int seconds){
    NonEditable field = new NonEditableImpl();
    FluentWait<NonEditable> waiter = waiter(field, seconds, 5);
    waiter.until(
        (NonEditable p) -> {
          updateDetailsPage();
          String actualValue = p.getValue(label);
          String assertionMessage =
              String.format(
                  "Actual value of \"%s\" was \"%s\" differs from expected \"%s\"",
                  label, actualValue, expectedValue);
          waiter.withMessage(assertionMessage);
          return StringUtils.equalsIgnoreCase(expectedValue, actualValue);
        });
  }

  private void updateDetailsPage() {
    clickTopArrow(DwpArrows.Up.getArrow());
    try {
      ViewListChecks viewListChecks = (ViewListChecks) getScenarioInstance(ViewListChecks.class);
      viewListChecks.clickOnLink(ParameterKeys.SuiteCrmCustomer.getKey());
    } catch (Throwable throwable) {
      throw new CucumberException(throwable);
    }
  }

  @Override
  @After("@DWP or @B2C or @E2E or @REGRESSION")
  public void tearDown() {
    super.tearDown();
  }
}
