package stepdefinitions.dwp.menu;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

public class TopMenuActions extends Component {
    private static final String ICON_ARROW_UP_CLASS_NAME = "icon-arrow-up";
    private static final String ICON_PLUS_CLASS_NAME = "icon-plus";
    private static final String ICON_PREVIOUS_CLASS_NAME = "icon-previous";

    private static final String PLUS_MENU_PATH_SEPARATOR = "\\s*->\\s*";
    private static final int TOP_MENU_RETRIES = 20;
    private static final int LEAF_POSITION = 2;

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

    public void clickPlusButton(String path) throws Exception {
        seleniumDriver.waitForRequestsToFinish();
        WebElement element = seleniumDriver.findElementWhenClickable(By.className(ICON_PLUS_CLASS_NAME));
        clickWithRetries(element, TOP_MENU_RETRIES);
        String[] navigationMenus = path.split(PLUS_MENU_PATH_SEPARATOR);
        navigateThruMenus(navigationMenus);
    }

    private void navigateThruMenus(String[] navigationMenus) throws Exception {
        int pathPosition = 0;
        for (String menu : navigationMenus) {
            Sleeper.sleepTightInSeconds(2);
            clickOnMenu(menu, pathPosition);
            pathPosition++;
        }
    }

    private void clickOnMenu(String menu, int pathPosition) throws Exception {
        By by = By.xpath(buildXPath(menu));
        Optional<WebElement> menuOptional = isLeaf(pathPosition) ? getLeafMenu(seleniumDriver.findElements(by)) : seleniumDriver.findElementOptional(by);
        if (!menuOptional.isPresent()) throw new Exception("Error navigatin on plus menu, " + menu + " not found");
        clickWithRetries(menuOptional.get(), TOP_MENU_RETRIES);
    }

    private Optional<WebElement> getLeafMenu(List<WebElement> menus){
        for (WebElement menuElement : menus) {
            if (menuElement.isDisplayed()) return Optional.of(menuElement);
        }
        return Optional.empty();
    }

    private boolean isLeaf(int pathPosition) {
        return pathPosition == LEAF_POSITION;
    }

    private String buildXPath(String label) {
        return "//span[contains(text(),'"+label+"')]/parent::a";
    }
}
