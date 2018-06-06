package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.core.WebDriverWait;
import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.dwp.pageobject.constant.Quote;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteStepView;
import com.essent.testing.dwp.pageobject.quote.CreateQuoteView;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.runtime.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.SalesChannel;
import stepdefinitions.dwp.tables.TariffTable;

import static com.essent.testing.dwp.DwpDateFormats.MONTHLY_PACKAGE;
import static com.essent.testing.dwp.DwpTimingParameters.*;
import static com.essent.testing.dwp.elements.BasicElements.NEXT_BUTTON;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.TITLE_SELECTOR_TEMPLATE;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.B2CQuoteElements.CONNECTION_DETAILS_ACTIVE;
import static com.essent.testing.dwp.quote.elements.TariffElements.PACKAGE;
import static com.essent.testing.dwp.quote.elements.TariffElements.TARIFFSHEET;

public class SelectPackageAndFuelTypeView extends Component implements CreateQuoteView, CreateQuoteStepView {


    private TariffTable tariffData;

    public SelectPackageAndFuelTypeView(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
        WebElement title = new WebDriverWait(seleniumDriver.getDriver(), 5).withoutException().until(
            driver -> {
                logger().info("STEP:");
                logger().info(" - ACTION: SELENIUM_FIND_ELEMENT");
                By by = By.xpath(TITLE_SELECTOR_TEMPLATE.getQuery().replace("${value}", Quote.PACKAGE_FUEL_TYPE.getText()));
                logger().info(" - BY: " + by.toString());
                return driver.findElement(by);
            }
        );
        if(title == null) {
            throw new CucumberException("Package And Fuel Type was not found.");
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
        return new ConnectionDetailsView(seleniumDriver);
    }

    @Override
    public boolean fillInInputValues() {
        String essentTariff = tariffData.getTariffSheet().replace("${MM_yyyy}", MONTHLY_PACKAGE.print());
        Model.Execution execution = newExecution();
        execution
            .element(TARIFFSHEET.element()).
            element(PACKAGE.element()).
            element(NEXT_BUTTON.element()).
            element(CONNECTION_DETAILS_ACTIVE.element()).
            step(createStep(Action.SELECT).requireDisplayed(true).element(TARIFFSHEET.name()).value(essentTariff), TOGGLE_CHECKBOX.getSleepInMillis()).
            step(createStep(Action.SELECT).requireDisplayed(true).element(PACKAGE.name()).value(tariffData.getPackageName()),TOGGLE_CHECKBOX.getSleepInMillis()).
            step(createStep(Action.CLICK).timeoutInSeconds(NEXT_STEP.getWaitInSeconds()).
                element(NEXT_BUTTON.name())).
            step(createStep(Action.REQUIRE).timeoutInSeconds(WAIT_NEXT_PAGE.getWaitInSeconds()).element(CONNECTION_DETAILS_ACTIVE.name()));
        return execute(execution);
    }

    public void setTariffData(TariffTable tariff) {
        this.tariffData = tariff;
    }
}
