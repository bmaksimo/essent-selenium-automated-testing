package com.essent.testing.dwp.pageobject.salesmarketing.customerdashboard.contracts;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class ContractPricesPage extends Component {

  public void clickOnBekijkPrijzenTariefkaatFromPlus() {
    Sleeper.sleepTightInSeconds(3);
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath("//list-plus-cell[@list-key='ContractlinesOnContract']/div/a")));
    Sleeper.sleepTightInSeconds(2);
    // TODO Remove locale-specific hard code.
    // The project must support official Belgian languages.
    // Locale-specific elements of web element locators must be parameterized.
    // This is basic rule!
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath("//list-row-action[@label='Bekijk prijzen tariefkaart']/a")));
  }

  public String getTypeProduct() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver.findElementWhenVisible(By.id("aos-products-price-type-field")).getText();
  }

  public String getEnergieprijsEnkelvoudigInclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-th-incl-vat-fixed-field"))
        .getText();
  }

  public String getEnergieprijsDagInclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-high-incl-vat-fixed-field"))
        .getText();
  }

  public String getEnergieprijsNachtInclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-low-incl-vat-fixed-field"))
        .getText();
  }

  public String getEnergieprijsExclusiefNachtInclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-exclnight-incl-vat-fixed-field"))
        .getText();
  }

  public String getVasteVergoedingInclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-fixedfee-incl-vat-field"))
        .getText();
  }

  public String getEnergieprijsEnkelvoudigExclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-th-fixed-field"))
        .getText();
  }

  public String getEnergieprijsDagExclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-high-fixed-field"))
        .getText();
  }

  public String getEnergieprijsNachExclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-low-fixed-field"))
        .getText();
  }

  public String getEnergieprijsExclusiefNachtExclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-exclnight-fixed-field"))
        .getText();
  }

  public String getVasteVergoedingExclBtw() {
    seleniumDriver.waitForRequestsToFinish();
    return seleniumDriver
        .findElementWhenVisible(By.id("dwp-selling-price-fixedfee-field"))
        .getText();
  }

  public void closeBekijkPrijsDetailsTK1() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(By.xpath("//a[@class=\"button icon-close\"]")));
  }
}
