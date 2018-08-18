package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import stepdefinitions.dwp.tables.SignatureData;

import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.quote.elements.SignatureElements.*;

public class QuoteOverviewPage extends CreateQuoteGuidedStep {

    private SignatureData signatureData;

    public QuoteOverviewPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }


    public void setSignatureData(SignatureData signatureData) {
        this.signatureData = signatureData;
    }

    @Override
    public boolean fillInFormData() {
        String formattedDate = signatureData.getDate().print();
        String place = signatureData.getPlace();
        String filePath = signatureData.getFilePath();
        Model.Execution execution = createExecutuin();
        execution
            .element(SIGN_WANTTOSIGN_CHECKBOX.element())
            .element(SIGN_ALREADYSIGNED_CHECKBOX.element())
            .element(SIGN_DATE.element())
            .element(SIGN_LOCATION.element())
            .element(SIGN_UPLOAD_DOC.element())
            .step(createStep(Action.ACCESS).element(SIGN_WANTTOSIGN_CHECKBOX.name()).requireDisplayed(false).callback(new HideIconOverlays()))
            .step(createStep(Action.CLICK).requireDisplayed(false).element(SIGN_WANTTOSIGN_CHECKBOX.name()).timeoutInSeconds(TOGGLE_CHECKBOX.getWaitInSeconds()), TOGGLE_CHECKBOX.getSleepInMillis())
            .step(createStep(Action.CLICK).requireDisplayed(false).element(SIGN_ALREADYSIGNED_CHECKBOX.name()), TOGGLE_CHECKBOX.getSleepInMillis())
            .step(createStep(Action.TYPING).element(SIGN_DATE.name()).value(formattedDate), INPUT.getSleepInMillis())
            .step(createStep(Action.TYPING).element(SIGN_LOCATION.name()).value(place), INPUT.getSleepInMillis())
            .step(createStep(Action.REQUIRE).element(SIGN_UPLOAD_DOC.name()).requireDisplayed(false))
            .step(createStep(Action.UPLOAD).element(SIGN_UPLOAD_DOC.name()).value(filePath).requireDisplayed(false), UPLOAD_FILE.getSleepInMillis());
        return execute(execution);
    }
}
