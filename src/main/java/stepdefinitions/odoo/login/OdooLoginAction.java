package stepdefinitions.odoo.login;

import com.essent.testing.dwp.pageobject.LoginComponent;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.DWPLoginDialog;
import com.essent.testing.dwp.pageobject.impl.IWelcomeLoginDialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

public class OdooLoginAction {
    private final static By IWELCOME_SELECTOR = By.id("login-base");
    private final static By DWP_SELECTOR = By.cssSelector(".modal__container.login");

    private SeleniumDriver seleniumDriver;

    public OdooLoginAction(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public Window doLogin(String username, String password) throws Throwable {
        LoginComponent loginComponent = getCurrentLoginDialog();
        if (null == loginComponent) return null;
        return loginComponent.login(username, password);
    }

    private LoginComponent getCurrentLoginDialog() {
        if (null != seleniumDriver.findElementOrNull(IWELCOME_SELECTOR)) return new IWelcomeLoginDialog(seleniumDriver);
        else if (null != seleniumDriver.findElementOrNull(DWP_SELECTOR)) return new DWPLoginDialog(seleniumDriver);
        return null;
    }
}
