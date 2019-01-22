package com.essent.testing.dwp.pageobject.impl.modal.billing.runjob;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.modal.billing.runjob.RunJobDialog;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import org.openqa.selenium.By;

public class StartInvoiceRunDialogImpl extends Component implements RunJobDialog {

    private final static By SELECTOR = By.cssSelector(".view__modal .modal__header");

    private String title;

    private String jobName;

    private String invoiceDate;

    private String processDate;



    public StartInvoiceRunDialogImpl(SeleniumDriverDwpImpl seleniumDriver) {
        super(seleniumDriver);
        waitForRequestsToFinish();
    }

    public StartInvoiceRunDialogImpl(SeleniumDriverDwpImpl seleniumDriver, String title) {
        super(seleniumDriver.findElementOrNull(SELECTOR), seleniumDriver);
        this.title = title;
        waitForRequestsToFinish();
    }

    @Override
    public void start() {

    }
}
