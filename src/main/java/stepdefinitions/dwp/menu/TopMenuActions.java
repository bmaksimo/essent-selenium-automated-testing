package stepdefinitions.dwp.menu;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TopMenuActions  extends Component {
    private static final String ICON_ARROW_UP_CLASS_NAME = "icon-arrow-up";
    private static final String ICON_PLUS_CLASS_NAME = "icon-plus";
    private static final String ICON_PREVIOUS_CLASS_NAME = "icon-previous";

    private static final String PLUS_MENU_PATH_SEPARATOR = "\\s*->\\s*";
    private static final int TOP_MENU_RETRIES = 20;

    public void clickUpButton() {
        seleniumDriver.waitForRequestsToFinish();
        WebElement element = seleniumDriver.findElementWhenClickable(By.className(ICON_ARROW_UP_CLASS_NAME));
        clickWithRetries(element, TOP_MENU_RETRIES);
    }

    public void clickPreviousButton() {
        seleniumDriver.waitForRequestsToFinish();
        WebElement element = seleniumDriver.findElementWhenClickable(By.className(ICON_PREVIOUS_CLASS_NAME));
        clickWithRetries(element, TOP_MENU_RETRIES);
    }

    public void clickPlusButton(String path) {
        seleniumDriver.waitForRequestsToFinish();
        WebElement element = seleniumDriver.findElementWhenClickable(By.className(ICON_PLUS_CLASS_NAME));
        clickWithRetries(element, TOP_MENU_RETRIES);
        navigateThruMenus(path);
    }

    private void navigateThruMenus(String path) {
        String[] navigationMenus = path.split(PLUS_MENU_PATH_SEPARATOR);
        for (String menu : navigationMenus) {
            Sleeper.sleepTightInSeconds(2);
            WebElement menuElement = seleniumDriver.findElementWhenClickable(By.xpath(buildXPath(menu)));
            clickWithRetries(menuElement, TOP_MENU_RETRIES);
        }
    }

    private String buildXPath(String label) {
        return "//span[contains(text(),'"+label+"')]/parent::a";
    }
}
