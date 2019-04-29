package com.essent.testing.dwp.pageobject.impl.modal.confirm;


import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.elements.Button;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.elements.ButtonImpl;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ConfirmSignatureDialogImpl extends Component implements ConfirmSignatureDialog {

    private final static By CONFIRM_SIGNATURE_MODAL_SELECTOR = By.cssSelector(".view__modal .modal__header");

    private final static By CONFIRM_BUTTON_SELECTOR = By.id("confirm-button");

    private static final By ACTION_LIST_LOCATOR = By.xpath("//*[@class='action-list']");

    public ConfirmSignatureDialogImpl(String title) {
        super(CONFIRM_SIGNATURE_MODAL_SELECTOR);
        this.title = title;
        seleniumDriver.waitForRequestsToFinish();
    }

    public ConfirmSignatureDialogImpl() {
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
    public boolean isInActionList(String textToLookup) {
        List<WebElement> actionList = seleniumDriver.findElements(ACTION_LIST_LOCATOR);
        return actionList.stream().filter(WebElement::isDisplayed).
            anyMatch(element->element.getText().
                contains(textToLookup));
    }

    @Override
    public boolean confirm() {
        seleniumDriver.waitForRequestsToFinish();
        Sleeper.sleepTightInSeconds(2);
        WebElement element = seleniumDriver.findElementWhenPresent(CONFIRM_BUTTON_SELECTOR);
        Button confirmButton = new ButtonImpl(element);
        confirmButton.click();
        seleniumDriver.waitForRequestsToFinish();
        return true;
    }

    @Override
    public boolean reject() {
        return true;
    }

    @Override
    public boolean isShown() {
        seleniumDriver.waitForRequestsToFinish();
        return seleniumDriver.findElementOptional(CONFIRM_SIGNATURE_MODAL_SELECTOR).isPresent();
    }
}
