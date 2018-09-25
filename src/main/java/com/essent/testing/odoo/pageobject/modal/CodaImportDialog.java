package com.essent.testing.odoo.pageobject.modal;

public interface CodaImportDialog extends Dialog {
    String getTitle();
    boolean selectFile(String path);
    boolean importFile();
}
