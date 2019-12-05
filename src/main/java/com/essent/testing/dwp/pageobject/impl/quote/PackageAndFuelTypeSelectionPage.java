package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.util.Sleeper;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import stepdefinitions.dwp.tables.SalesChannel;
import stepdefinitions.dwp.tables.TariffTable;


public class PackageAndFuelTypeSelectionPage extends QuoteCreationGuidedStep {


    private TariffTable tariffData;

    private boolean regularisation;
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

    private final String TARIFFSHEET_ID = "accounts-aos-quotes-aos-products-quotes-tariffsheet-id-field";
    private final String PACKAGE_ID = "accounts-aos-quotes-aos-products-quotes-package-id-field";


    @Override
    public boolean fillInFormData() {
        seleniumDriver.waitForRequestsToFinish();

        try {
            if (null != tariffData.getTariffSheet()) {
                WebElement tariffSheetDropDown = seleniumDriver.findElementWhenClickable(By.id(TARIFFSHEET_ID));
                seleniumDriver.waitAndClick(tariffSheetDropDown);
                tariffSheetDropDown.sendKeys(tariffData.getTariffSheet());
            }

            if (null != tariffData.getPackageName()) {
                WebElement packageDropDown = seleniumDriver.findElementWhenClickable(By.id(PACKAGE_ID));
                seleniumDriver.waitAndClick(packageDropDown);
                packageDropDown.sendKeys(tariffData.getPackageName());
            }

            seleniumDriver.waitForRequestsToFinish();
            Sleeper.sleepTightInSeconds(10);

            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void setTariffData(TariffTable tariff) {
        this.tariffData = tariff;
    }
}
