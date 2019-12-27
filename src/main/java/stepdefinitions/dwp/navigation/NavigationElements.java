package stepdefinitions.dwp.navigation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.essent.testing.dwp.pageobject.impl.navigation.DashboardMenuPage;
import com.essent.testing.dwp.pageobject.impl.navigation.DwpPlusMenu;
import com.essent.testing.dwp.pageobject.impl.navigation.TopActionsPageImpl;
import com.essent.testing.dwp.pageobject.navigation.TopActionsPage;
import com.essent.testing.dwp.scenario.DwpScenario;
import cucumber.runtime.CucumberException;
import java.util.function.Predicate;
import org.junit.Assert;
import stepdefinitions.dwp.menu.TopMenuActions;

public abstract class NavigationElements extends DwpScenario {

  private class ClickTopAction implements Predicate<String> {
    @Override
    public boolean test(String action) {
      TopActionsPage topActions = new TopActionsPageImpl();
      return topActions.executeTopAction(action);
    }

    private boolean testWithFixedTime(String action, int waitingTime) {
      TopActionsPage topActions = new TopActionsPageImpl();
      return topActions.executeTopActionWithFixedWait(action, waitingTime);
    }
  }

  protected void clickTopAction(String name) {
    seleniumDriver.waitForRequestsToFinish();
    boolean success = new ClickTopAction().test(name);
    assertThat(String.format("Top Menu item %s was not available.", name), success, is(true));
  }

  protected void clickTopAction(String name, int waitingTime) {
    seleniumDriver.waitForRequestsToFinish();
    boolean success = new ClickTopAction().testWithFixedTime(name, waitingTime);
    assertThat(String.format("Top Menu item %s was not available.", name), success, is(true));
  }

  protected void clickTopArrow(String arrow) throws Exception {
    seleniumDriver.waitForRequestsToFinish();
    if ("up".equalsIgnoreCase(arrow)) new TopMenuActions().clickUpButton();
    else if ("back".equalsIgnoreCase(arrow)) new TopMenuActions().clickPreviousButton();
    else
      Assert.fail(
          String.format(
              "Top Arrow %s is unknown. Currently possible top arrows are: 'up' and 'back'",
              arrow));
  }

  protected void clickPlusAction(String path) {
    seleniumDriver.waitForRequestsToFinish();
    DwpPlusMenu plusMenu = new DwpPlusMenu();
    boolean success = plusMenu.executeAction(path);
    assertThat(String.format("Plus Menu Path %s undefined.", path), success, is(true));
  }

  protected void loopBack(String arrow, String dashboardMenu) {
    try {
      seleniumDriver.waitForRequestsToFinish();
      clickTopArrow(arrow);
      seleniumDriver.waitForRequestsToFinish();
      new DashboardMenuPage().clickOnDashboardElement(dashboardMenu);
      seleniumDriver.waitForRequestsToFinish();
    } catch (Throwable t) {
      throw new CucumberException(t);
    }
  }
}
