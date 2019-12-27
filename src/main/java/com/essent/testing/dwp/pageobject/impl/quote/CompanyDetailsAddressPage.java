package com.essent.testing.dwp.pageobject.impl.quote;

import static com.essent.automation.autocrat.Action.TYPING;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.automation.util.Sleeper;
import com.essent.testing.datagenerator.address.StreetGenerator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.tables.CustomerAddress;

public class CompanyDetailsAddressPage extends QuoteCreationGuidedStep {

  private CustomerAddress address;

  public void setAddress(CustomerAddress customerAddress) {
    address = customerAddress;
  }

  @Override
  public boolean fillInFormData() {
    return setAddress();
  }

  private boolean setAddress() {
    String street = address.getStreet();
    if (street.equals("Random")) {
      street = StreetGenerator.getRandomStreetInKontich();
    }
    String houseNr = Integer.toString(address.getHouseNr());
    String houseNrAdd = address.getHouseNrAdd();
    String bus = address.getBus();
    String postcode = address.getPostalCode();
    String city = address.getCity();
    String country = address.getCountry();

    Model.Execution initializeAddress = createExecution();
    initializeAddress
        .element(DELIVERY_ADDR_STREET.element())
        .element(DELIVERY_ADDR_HOUSE_NR.element())
        .element(DELIVERY_ADDR_HOUSE_ADD.element())
        .element(DELIVERY_ADDR_BUS.element())
        .element(DELIVERY_ADDR_ZIPCODE.element())
        .element(DELIVERY_ADDR_CITY.element())
        .element(DELIVERY_ADDR_COUNTRY.element());

    initializeAddress
        .step(
            createStep(TYPING)
                .timeoutInSeconds(3)
                .element(DELIVERY_ADDR_STREET.name())
                .value(street),
            INPUT.getSleepInMillis())
        .step(
            createStep(TYPING)
                .timeoutInSeconds(3)
                .element(DELIVERY_ADDR_HOUSE_NR.name())
                .value(houseNr),
            INPUT.getSleepInMillis())
        .step(
            createStep(TYPING).element(DELIVERY_ADDR_HOUSE_ADD.name()).value(houseNrAdd),
            INPUT.getSleepInMillis());
    if (StringUtils.isNotEmpty(bus)) {
      initializeAddress.step(
          createStep(TYPING).element(DELIVERY_ADDR_BUS.name()).value(bus),
          INPUT.getSleepInMillis());
    }
    initializeAddress
        .step(
            createStep(TYPING).element(DELIVERY_ADDR_ZIPCODE.name()).value(postcode),
            INPUT.getSleepInMillis())
        .step(
            createStep(TYPING).element(DELIVERY_ADDR_CITY.name()).value(city),
            INPUT.getSleepInMillis());
    if (StringUtils.isNotEmpty(country)) {
      initializeAddress.step(
          createStep(Action.SELECT).element(DELIVERY_ADDR_COUNTRY.name()).value(country));
    }
    return execute(initializeAddress);
  }

  public void setAddressNewDatatable(List<Map<String, String>> add) {
    String street = null;
    String houseNr = null;
    String houseNrAdd = null;
    String bus = null;
    String postcode = null;
    String city = null;
    String country = null;

    for (Map<String, String> stringStringMap : add) {
      street = stringStringMap.get("street");
      if (street.equals("Random")) {
        street = StreetGenerator.getRandomStreetInKontich();
      }
      houseNr = stringStringMap.get("houseNr");
      houseNrAdd = stringStringMap.get("houseNrAdd");
      bus = stringStringMap.get("bus");
      postcode = stringStringMap.get("postalCode");
      city = stringStringMap.get("city");
      country = stringStringMap.get("country");
    }

    // There is no need to wait inbetween, we don't care what happens, just wait at the end
    this.fillFieldByXPath(DELIVERY_ADDR_STREET.getQuery(), street);
    this.fillFieldByXPath(DELIVERY_ADDR_HOUSE_NR.getQuery(), houseNr);
    this.fillFieldByXPath(DELIVERY_ADDR_HOUSE_ADD.getQuery(), houseNrAdd);
    this.fillFieldByXPath(DELIVERY_ADDR_ZIPCODE.getQuery(), postcode);
    this.fillFieldByXPath(DELIVERY_ADDR_CITY.getQuery(), city);
    this.fillFieldByXPath(DELIVERY_ADDR_BUS.getQuery(), bus);
    this.fillFieldByXPath(DELIVERY_ADDR_COUNTRY.getQuery(), country);
    seleniumDriver.waitForRequestsToFinish();
    Sleeper.sleepTightInSeconds(5);
    handleAlert();
  }

  private CustomerAddress getCustomerAddressFromDataTable(Map<String, String> address) {
    CustomerAddress customerAddress = new CustomerAddress();

    String street =
        "Random".equals(address.get("street"))
            ? StreetGenerator.getRandomStreetInKontich()
            : address.get("street");
    customerAddress.setStreet(street);
    customerAddress.setHouseNr(Integer.parseInt(address.get("houseNr")));
    customerAddress.setHouseNrAdd(address.get("houseNrAdd"));
    customerAddress.setBus(address.get("bus"));
    customerAddress.setPostalCode(address.get("postalCode"));
    customerAddress.setCity(address.get("city"));
    customerAddress.setCountry(address.get("country"));

    return customerAddress;
  }
}
