package com.essent.testing.dwp.pageobject.modal.confirm;

import com.essent.testing.dwp.pageobject.modal.ConfirmDialog;

public interface ConfirmSignatureDialog extends ConfirmDialog {
    String getTitle();

    void setTitle(String title);

    String getSignatureDate();

    void setSignatureDate(String signatureDate);

    boolean isInActionList(String textToLookup);


}
