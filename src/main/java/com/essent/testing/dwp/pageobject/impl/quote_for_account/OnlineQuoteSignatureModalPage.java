package com.essent.testing.dwp.pageobject.impl.quote_for_account;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.dwp.pageobject.impl.quote.QuoteCreationGuidedStep;
import stepdefinitions.dwp.tables.SignatureData;

import static com.essent.testing.dwp.autocrat.element.quote.SignatureElements.B2B_TK2_ONLINE_SIGN_UPLOAD_DOC;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.UPLOAD_FILE;

public class OnlineQuoteSignatureModalPage  extends QuoteCreationGuidedStep {
    private SignatureData signatureData;

    public void setSignatureData(SignatureData signatureData) {
        this.signatureData = signatureData;
    }

    @Override
    public boolean fillInFormData() {
        String filePath = signatureData.getFilePath();

        Model.Execution execution = createExecution();
        execution.element(B2B_TK2_ONLINE_SIGN_UPLOAD_DOC.element());
        seleniumDriver.waitForRequestsToFinish();
        execution.step(createStep(Action.REQUIRE).element(B2B_TK2_ONLINE_SIGN_UPLOAD_DOC.name()).requireDisplayed(false));
        seleniumDriver.waitForRequestsToFinish();
        execution.step(createStep(Action.UPLOAD).element(B2B_TK2_ONLINE_SIGN_UPLOAD_DOC.name()).value(filePath).requireDisplayed(false), UPLOAD_FILE.getSleepInMillis());
        seleniumDriver.waitForRequestsToFinish();

        return execute(execution);
    }
}
