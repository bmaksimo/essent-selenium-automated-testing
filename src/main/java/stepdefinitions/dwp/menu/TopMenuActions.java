package stepdefinitions.dwp.menu;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TopMenuActions  extends Component {
    private static final String ICON_ARROW_UP_CLASS_NAME = "icon-arrow-up";
    private static final String ICON_PLUS_CLASS_NAME = "icon-plus";
    private static final String ICON_PREVIOUS_CLASS_NAME = "icon-previous";
    private static final int TOP_MENU_RETRIES = 20;

    public void clickUpButton() {
        seleniumDriver.waitForRequestsToFinish();
        WebElement element = seleniumDriver.findElementWhenClickable(By.className(ICON_ARROW_UP_CLASS_NAME));
        clickWithRetries(element, TOP_MENU_RETRIES);
    }

    public void clickPlusButton() {
        seleniumDriver.waitForRequestsToFinish();
        WebElement element = seleniumDriver.findElementWhenClickable(By.className(ICON_PLUS_CLASS_NAME));
        clickWithRetries(element, TOP_MENU_RETRIES);
    }

    public void clickPreviousButton() {
        seleniumDriver.waitForRequestsToFinish();
        WebElement element = seleniumDriver.findElementWhenClickable(By.className(ICON_PREVIOUS_CLASS_NAME));
        clickWithRetries(element, TOP_MENU_RETRIES);
    }
}
