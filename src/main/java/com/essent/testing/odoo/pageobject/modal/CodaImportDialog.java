package com.essent.testing.odoo.pageobject.modal;

import com.essent.testing.dwp.pageobject.Form;
public interface CodaImportDialog extends Dialog, Form {
    String  getTitle();
    void    setUploadFile(String path);
    void    setImportButton(String text);
    void    confirm();
    String getImportReport();
}
