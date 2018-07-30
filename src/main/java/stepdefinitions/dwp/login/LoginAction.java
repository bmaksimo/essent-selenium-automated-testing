package stepdefinitions.dwp.login;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.dwp.pageobject.LoginComponent;
import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.DWPLoginDialog;
import com.essent.testing.dwp.pageobject.impl.IWelcomeLoginDialog;
import com.essent.testing.selenium.SeleniumDriver;

public class LoginAction {

    private SeleniumDriver seleniumDriver;

    public LoginAction(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public Window doLogin(String username, String password) throws Throwable {
        return getCurrentLoginDialog().login(username, password);
    }

    private LoginComponent getCurrentLoginDialog() {
        return "DEVINT01".equalsIgnoreCase(ConfigProvider.getProperty(ConfigKey.ENVIRONMENT)) ?
            new DWPLoginDialog(seleniumDriver)
            : new IWelcomeLoginDialog(seleniumDriver);
    }
}
