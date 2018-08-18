package com.essent.testing.dwp.pageobject.quote.impl;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.selenium.SeleniumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import stepdefinitions.dwp.tables.SalesChannel;
import stepdefinitions.dwp.tables.TariffTable;

import static com.essent.testing.dwp.DwpTimingParameters.TOGGLE_CHECKBOX;
import static com.essent.testing.dwp.pageobject.constant.XpathSelectors.VIEW_SELECTOR;
import static com.essent.testing.dwp.quote.elements.TariffElements.PACKAGE;
import static com.essent.testing.dwp.quote.elements.TariffElements.TARIFFSHEET;

public class SelectPackageAndFuelTypePage extends CreateQuoteGuidedStep {


    private TariffTable tariffData;

    public SelectPackageAndFuelTypePage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver.findElementOrNull(By.xpath(VIEW_SELECTOR.getQuery())), seleniumDriver);
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
    public boolean fillInFormData() {
        String essentTariff = tariffData.getTariffSheet();
        Model.Execution execution = newExecution();
        execution.
            element(PACKAGE.element());
        if(StringUtils.isNotEmpty(essentTariff))
            execution.element(TARIFFSHEET.element()).
            step(createStep(Action.SELECT).requireDisplayed(true).element(TARIFFSHEET.name()).value(essentTariff), TOGGLE_CHECKBOX.getSleepInMillis());

        execution.step(createStep(Action.SELECT).requireDisplayed(true).element(PACKAGE.name()).value(tariffData.getPackageName()),TOGGLE_CHECKBOX.getSleepInMillis());
        return execute(execution);
    }

    public void setTariffData(TariffTable tariff) {
        this.tariffData = tariff;
    }
}
