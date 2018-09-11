package stepdefinitions.odoo.login;

import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.odoo.pageobject.impl.modal.login.OdooLogin;
import com.essent.testing.selenium.SeleniumDriver;

public class OdooLoginAction {
//    private final static By IWELCOME_SELECTOR = By.id("login-base");
//    private final static By DWP_SELECTOR = By.cssSelector(".modal__container.login");

    private SeleniumDriver seleniumDriver;

    public OdooLoginAction(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public Window doLogin(String username, String password) throws Throwable {
        //DWPLoginDialog loginComponent = new DWPLoginDialog(seleniumDriver);
        OdooLogin odooLogin = new OdooLogin(seleniumDriver);
        if (null == odooLogin) return null;
        return odooLogin.login(username, password);
    }
}
