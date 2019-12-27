package com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.documents;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.navigation.DashboardMenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DocumentsPage extends Component {

  private static final String DETAILS_DASHBOARD_MENU = "Details";
  private static final String DOCUMENTS_DASHBOARD_MENU = "Documenten";
  private static final String DOCUMENT_LABEL_TEMPLATE =
      "//span[contains(text(), '${documentLabel}')]";

  public boolean isDocumentNamePresent(String document) {
    seleniumDriver.waitForRequestsToFinish();
    String documentLabel = createQuery(DOCUMENT_LABEL_TEMPLATE, "documentLabel", document);

    boolean found = false;
    int currentAttempt = 0;
    int maxAttempts = 20;

    while (!found && currentAttempt < maxAttempts) {
      currentAttempt++;
      try {
        WebElement documentElement = findElementWhenVisible(By.xpath(documentLabel));
        return true;
      } catch (Exception e) {
        logger().debug("Element " + documentLabel + " not found");
        loopback();
      }
    }
    return false;
  }

  private void loopback() {
    Sleeper.sleepTightInSeconds(30);
    DashboardMenuPage dashboardMenuPage = new DashboardMenuPage();
    dashboardMenuPage.clickOnDashboardElement(DETAILS_DASHBOARD_MENU);
    seleniumDriver.waitForRequestsToFinish();
    dashboardMenuPage.clickOnDashboardElement(DOCUMENTS_DASHBOARD_MENU);
    seleniumDriver.waitForRequestsToFinish();
  }
}
