package com.essent.testing.dwp.pageobject.impl.modal.confirm;


import com.essent.testing.dwp.pageobject.elements.Button;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ButtonImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ConfirmSignatureDialogImpl extends Component implements ConfirmSignatureDialog {

    private final static By SELECOR = By.cssSelector(".view__modal .modal__header");

    private final static By SELECOR_CONFIRM_BUTTON = By.id("confirm-button");


    public ConfirmSignatureDialogImpl(SeleniumDriver seleniumDriver, String title) {
        super(seleniumDriver.findElementOrNull(SELECOR), seleniumDriver);
        this.title = title;
        waitForRequestsToFinish();
    }

    public ConfirmSignatureDialogImpl(SeleniumDriver seleniumDriver) {
       super(seleniumDriver);
       seleniumDriver.waitForRequestsToFinish();
    }

    private String title;

    private String signatureDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(String signatureDate) {
        this.signatureDate = signatureDate;
    }

    @Override
    public boolean confirm() {
        WebElement element = seleniumDriver.findElementOrNull(SELECOR_CONFIRM_BUTTON);
        if(element == null)
            return false;
        Button confirmButton = new ButtonImpl(element);
        confirmButton.click();
        waitForRequestsToFinish();
        return true;
    }

    @Override
    public boolean reject() {
        return true;
    }
}
