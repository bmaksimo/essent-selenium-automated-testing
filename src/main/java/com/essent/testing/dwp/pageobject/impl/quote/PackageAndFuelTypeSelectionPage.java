package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.tables.SalesChannel;
import stepdefinitions.dwp.tables.TariffTable;

import static com.essent.testing.dwp.autocrat.element.quote.TariffElements.PACKAGE;
import static com.essent.testing.dwp.autocrat.element.quote.TariffElements.TARIFFSHEET;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.TOGGLE_CHECKBOX;

public class PackageAndFuelTypeSelectionPage extends QuoteCreationGuidedStep {


    private TariffTable tariffData;

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
        Model.Execution execution = createExecution();
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
