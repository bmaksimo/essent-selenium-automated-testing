package com.essent.testing.dwp.pageobject.impl.modal.billing.runjob;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.modal.billing.runjob.RunJobDialog;
import org.openqa.selenium.By;

public class StartInvoiceRunDialogImpl extends Component implements RunJobDialog {

    private final static By SELECTOR = By.cssSelector(".view__modal .modal__header");
    private String title;
    private String jobName;
    private String invoiceDate;
    private String processDate;

    public StartInvoiceRunDialogImpl() {
        seleniumDriver.waitForRequestsToFinish();
    }

    public StartInvoiceRunDialogImpl(String title) {
        super(SELECTOR);
        this.title = title;
        seleniumDriver.waitForRequestsToFinish();
    }

    @Override
    public void start() {

    }
}
