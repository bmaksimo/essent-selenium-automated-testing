package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.constant.Quote;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.SignatureData;

import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.BasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.SignatureElements.*;
import static org.junit.Assert.fail;

public class QuoteOverviewView extends Component implements CreateQuoteView, CreateQuoteStepView {

    private SignatureData signatureData;

    public void setSignatureData(SignatureData signatureData) {
        this.signatureData = signatureData;
    }

    public QuoteOverviewView(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
        WebElement title = new WebDriverWait(seleniumDriver.getDriver(), 5).withoutException().until(
            driver -> {
                logger().info("STEP:");
                logger().info(" - ACTION: SELENIUM_FIND_ELEMENT");
                By by = By.xpath(TITLE_SELECTOR_TEMPLATE.getQuery().replace("${value}", Quote.QUOTE_OVERVIEW.getText()));
                logger().info(" - BY: " + by.toString());
                return driver.findElement(by);
            }
        );
        if(title == null) {
            fail("Quote Overview was not found.");
        }
        logger().info(" - RESULT: " + "element: <" + title.getTagName() + " class='" + title.getAttribute("class") + "'>" + title.getText() + "/<" + title.getTagName()+ ">");
    }

    @Override
    public CreateQuoteStepView next() {
        logger().info("Quote overview, now confirming the signature.");
        Model.Execution execution = newExecution();
        execution
            .element(NEXT_BUTTON.element())
            .step(createStep(Action.CLICK).element(NEXT_BUTTON.name()).timeoutInSeconds(SUBMIT_QUOTE.getWaitInSeconds()));
            execute(execution);
        return null;
    }

    @Override
    public boolean fillInFormData() {
        String formattedDate = signatureData.getDate().print();
        String place = signatureData.getPlace();
        String filePath = signatureData.getFilePath();
        Model.Execution execution = newExecution();
        execution
            .element(SIGN_WANTTOSIGN_CHECKBOX.element())
            .element(SIGN_ALREADYSIGNED_CHECKBOX.element())
            .element(SIGN_DATE.element())
            .element(SIGN_LOCATION.element())
            .element(SIGN_UPLOAD_DOC.element())
            .element(NEXT_BUTTON.element())
            .step(createStep(Action.ACCESS).element(SIGN_WANTTOSIGN_CHECKBOX.name()).requireDisplayed(false).callback(new HideIconOverlays()))
            .step(createStep(Action.CLICK).requireDisplayed(false).element(SIGN_WANTTOSIGN_CHECKBOX.name()).timeoutInSeconds(TOGGLE_CHECKBOX.getWaitInSeconds()), TOGGLE_CHECKBOX.getSleepInMillis())
            .step(createStep(Action.CLICK).requireDisplayed(false).element(SIGN_ALREADYSIGNED_CHECKBOX.name()), TOGGLE_CHECKBOX.getSleepInMillis())
            .step(createStep(Action.TYPING).element(SIGN_DATE.name()).value(formattedDate), INPUT.getSleepInMillis())
            .step(createStep(Action.TYPING).element(SIGN_LOCATION.name()).value(place), INPUT.getSleepInMillis())
            .step(createStep(Action.REQUIRE).element(SIGN_UPLOAD_DOC.name()).requireDisplayed(false))
            .step(createStep(Action.UPLOAD)
                .element(SIGN_UPLOAD_DOC.name()).value(filePath).requireDisplayed(false), UPLOAD_FILE.getSleepInMillis());
        return execute(execution);
    }
}
