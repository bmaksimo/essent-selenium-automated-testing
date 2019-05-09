package com.essent.testing.dwp.pageobject.impl.quote_for_account;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.impl.quote.QuoteCreationGuidedStep;
import stepdefinitions.dwp.tables.SignatureData;

import static com.essent.testing.dwp.autocrat.element.quote_for_account.QuoteForAccountSignatureElements.SIGN_UPLOAD_DOC;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.UPLOAD_FILE;

public class QuoteForAccountOverviewPage extends QuoteCreationGuidedStep {

    private SignatureData signatureData;

    public void setSignatureData(SignatureData signatureData) {
        this.signatureData = signatureData;
    }

    @Override
    public boolean fillInFormData() {
        String filePath = signatureData.getFilePath();
        Model.Execution execution = createExecution();
        execution
            .element(SIGN_UPLOAD_DOC.element())
            .step(createStep(Action.REQUIRE).element(SIGN_UPLOAD_DOC.name()).requireDisplayed(false))
            .step(createStep(Action.UPLOAD).element(SIGN_UPLOAD_DOC.name()).value(filePath).requireDisplayed(false), UPLOAD_FILE.getSleepInMillis());
        return execute(execution);
    }
}
