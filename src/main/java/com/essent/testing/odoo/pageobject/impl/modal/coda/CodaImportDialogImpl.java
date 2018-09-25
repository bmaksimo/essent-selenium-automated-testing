package com.essent.testing.odoo.pageobject.impl.modal.coda;

import com.essent.testing.dwp.pageobject.Window;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.modal.login.LoginComponent;
import com.essent.testing.dwp.pageobject.modal.Dialog;
import com.essent.testing.odoo.pageobject.impl.main.OdooMainWindow;
import com.essent.testing.odoo.pageobject.modal.CodaImportDialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertNotNull;

public class CodaImportDialogImpl extends Component implements CodaImportDialog {
    //Import CODA File
    private final static By SELECTOR = By.cssSelector(".modal-content");

    public CodaImportDialogImpl(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
        seleniumDriver.findElementOrNull(SELECTOR);
    }

    @Override
    public String getTitle() {
        WebElement titleElement = element.findElement(By.cssSelector("h3"));
        return titleElement.getText();
    }

    @Override
    public boolean selectFile(String path) {
        return false;
    }

    @Override
    public boolean importFile() {
        return false;
    }
}
