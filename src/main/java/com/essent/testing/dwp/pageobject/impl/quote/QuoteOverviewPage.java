package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import stepdefinitions.dwp.tables.SignatureData;

import static com.essent.testing.dwp.autocrat.element.quote.SignatureElements.SIGN_LOCATION;
import static com.essent.testing.dwp.autocrat.element.quote.SignatureElements.SIGN_UPLOAD_DOC;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.UPLOAD_FILE;

public class QuoteOverviewPage extends QuoteCreationGuidedStep {

    private SignatureData signatureData;

    public void setSignatureData(SignatureData signatureData) {
        this.signatureData = signatureData;
    }

    @Override
    public boolean fillInFormData() {
        String place = signatureData.getPlace();
        String filePath = signatureData.getFilePath();
        Model.Execution execution = createExecution();
        execution
            .element(SIGN_LOCATION.element())
            .element(SIGN_UPLOAD_DOC.element())
            .step(createStep(Action.REQUIRE).element(SIGN_UPLOAD_DOC.name()).requireDisplayed(false))
            .step(createStep(Action.UPLOAD).element(SIGN_UPLOAD_DOC.name()).value(filePath).requireDisplayed(false), UPLOAD_FILE.getSleepInMillis())
            .step(createStep(Action.TYPING).element(SIGN_LOCATION.name()).value(place), INPUT.getSleepInMillis());
        return execute(execution);
    }

}
