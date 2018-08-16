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
import stepdefinitions.dwp.tables.SalesChannel;

import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.BasicElements.CONFIRM_BUTTON;
import static com.essent.testing.dwp.elements.BasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.*;
import static org.junit.Assert.fail;

public class SelectQuoteTypeView extends Component implements CreateQuoteView, CreateQuoteStepView {


    public SelectQuoteTypeView(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
        WebElement title = new WebDriverWait(seleniumDriver.getDriver(), 5).withoutException().until(
            driver -> {
                logger().info("STEP:");
                logger().info(" - ACTION: SELENIUM_FIND_ELEMENT");
                By by = By.xpath(TITLE_SELECTOR_TEMPLATE.getQuery().replace("${value}", Quote.QUOTE_DETAILS.getText()));
                logger().info(" - BY: " + by.toString());
                return driver.findElement(by);
            }
        );
        if(title == null) {
            fail("Select Quote Type was not found.");
        }
        logger().info(" - RESULT: " + "element: <" + title.getTagName() + " class='" + title.getAttribute("class") + "'>" + title.getText() + "/<" + title.getTagName()+ ">");
    }

    private boolean      regularisation;

    private SalesChannel salesChannel;

    public boolean isRegularisation() {
        return regularisation;
    }

    public void setRegularisation(boolean regularisation) {
        this.regularisation = regularisation;
    }

    public SalesChannel getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(SalesChannel salesChannel) {
        this.salesChannel = salesChannel;
    }

    @Override
    public CreateQuoteStepView next() {
        Model.Execution toggleReguCheckbox = newExecution();
        toggleReguCheckbox.
            element(NEXT_BUTTON.element()).
            element(CUSTOMER_DETAILS_ACTIVE.element()).
            step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).
                element(NEXT_BUTTON.name())).
            step(createStep(Action.REQUIRE).timeoutInSeconds(WAIT_NEXT_PAGE.getWaitInSeconds()).element(CUSTOMER_DETAILS_ACTIVE.name()));
        if(!execute(toggleReguCheckbox))
            fail("Guided step Channel details faied.");
        return new CustomerDetailsView(seleniumDriver);
    }

    @Override
    public boolean fillInFormData() {
        Model.Execution toggleReguCheckbox = newExecution();
        toggleReguCheckbox.
            element(SALES_CHANNEL_FIELD.element()).
            step(createStep(Action.SELECT).element(SALES_CHANNEL_FIELD.name()).value(salesChannel.getLabel()), INPUT.getSleepInMillis());
            return execute(toggleReguCheckbox);
    }
}
