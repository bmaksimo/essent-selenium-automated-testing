package com.essent.testing.dwp.pageobject.impl.quote;

import com.essent.automation.autocrat.Action;
import com.essent.automation.autocrat.Model;
import com.essent.testing.datagenerator.address.StreetGenerator;
import org.apache.commons.lang3.StringUtils;
import stepdefinitions.dwp.tables.CustomerAddress;

import static com.essent.automation.autocrat.Action.TYPING;
import static com.essent.testing.dwp.autocrat.element.quote.B2CQuoteElements.*;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.INPUT;


public class CompanyDetailsAddressPage extends QuoteCreationGuidedStep {

    private CustomerAddress address;

    public void setAddress(CustomerAddress customerAddress) {
        address = customerAddress;
    }

    @Override
    public boolean fillInFormData() {
        return setAddress();
    }

    public boolean setAddress() {
        String street = address.getStreet();
        if(street.equals("Random")) { street = StreetGenerator.getRandomStreetInKontich(); }
        String houseNr = Integer.toString(address.getHouseNr());
        String houseNrAdd = address.getHouseNrAdd();
        String bus = address.getBus();
        String postcode = address.getPostalCode();
        String city = address.getCity();
        String country = address.getCountry();

        Model.Execution initializeAddress = createExecution();
        initializeAddress.
            element(DELIVERY_ADDR_STREET.element()).
            element(DELIVERY_ADDR_HOUSE_NR.element()).
            element(DELIVERY_ADDR_HOUSE_ADD.element()).
            element(DELIVERY_ADDR_BUS.element()).
            element(DELIVERY_ADDR_ZIPCODE.element()).
            element(DELIVERY_ADDR_CITY.element()).
            element(DELIVERY_ADDR_COUNTRY.element());

        initializeAddress.
            step(createStep(TYPING).timeoutInSeconds(3).element(DELIVERY_ADDR_STREET.name()).value(street), INPUT.getSleepInMillis()).
            step(createStep(TYPING).timeoutInSeconds(3).element(DELIVERY_ADDR_HOUSE_NR.name()).value(houseNr), INPUT.getSleepInMillis()).
            step(createStep(TYPING).element(DELIVERY_ADDR_HOUSE_ADD.name()).value(houseNrAdd), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(bus)) {
            initializeAddress.step(createStep(TYPING).element(DELIVERY_ADDR_BUS.name()).value(bus), INPUT.getSleepInMillis());
        }
        initializeAddress.step(createStep(TYPING).element(DELIVERY_ADDR_ZIPCODE.name()).value(postcode), INPUT.getSleepInMillis()).
            step(createStep(TYPING).element(DELIVERY_ADDR_CITY.name()).value(city), INPUT.getSleepInMillis());
        if (StringUtils.isNotEmpty(country)) {
            initializeAddress.step(createStep(Action.SELECT).element(DELIVERY_ADDR_COUNTRY.name()).value(country));
        }
        return execute(initializeAddress);
    }
}
