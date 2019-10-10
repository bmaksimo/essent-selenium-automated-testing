package com.essent.testing.dwp.pageobject.impl.modal.confirm;

import com.essent.testing.dwp.pageobject.impl.modal.ModalBase;
import com.essent.testing.dwp.pageobject.modal.confirm.ConfirmSignatureDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import java.time.Duration;

public class ConfirmSignatureDialogImpl extends ModalBase implements ConfirmSignatureDialog {

  private static final By CONFIRM_SIGNATURE_MODAL_SELECTOR =
      By.cssSelector(".view__modal .modal__header");

  private static final String ACTION_LIST_LOCATOR = "//div[@class='action-list']//li/span[contains(text(), '%s')]";

  public ConfirmSignatureDialogImpl(String title) {
    super();
    this.title = title;
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
    try {
      seleniumDriver.findElementWhenPresent(
              By.xpath(
                      String.format(ACTION_LIST_LOCATOR, textToLookup)
              ), Duration.ofSeconds(10), Duration.ofMillis(50)
      );
      return true;
    } catch (TimeoutException e) {
      return false;
    }
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
