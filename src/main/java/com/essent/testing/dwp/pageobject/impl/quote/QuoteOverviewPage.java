package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import stepdefinitions.dwp.tables.SignatureData;

import static com.essent.testing.dwp.autocrat.element.quote.SignatureElements.*;
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


    public boolean fillInFormDataDeduplication() {
        String place = signatureData.getPlace();
        String filePath = signatureData.getFilePath();
        Model.Execution execution = createExecution();
        execution
            .element(SIGN_LOCATION_DEDUPLICATION.element())
            .element(SIGN_UPLOAD_DOC_DEDUPLICATION.element())
            .step(createStep(Action.REQUIRE).element(SIGN_UPLOAD_DOC_DEDUPLICATION.name()))
            .step(createStep(Action.UPLOAD).element(SIGN_UPLOAD_DOC_DEDUPLICATION.name()).value(filePath), UPLOAD_FILE.getSleepInMillis())
            .step(createStep(Action.TYPING).element(SIGN_LOCATION_DEDUPLICATION.name()).value(place), INPUT.getSleepInMillis());
        return execute(execution);
    }
}
