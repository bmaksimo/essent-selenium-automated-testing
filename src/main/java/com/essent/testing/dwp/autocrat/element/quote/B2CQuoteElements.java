package com.essent.testing.dwp.autocrat.element.quote;

import com.essent.automation.autocrat.Model;

public enum B2CQuoteElements {
    SALES_CHANNEL_FIELD("SELECTOR", "#accounts-aos-quotes-sales-channel-id-field"),
    COPY_ADDRESS_CONNECTION_TO_BILLING("SELECTOR", "#dwp-copy-address-connection-to-billing-field"),
    SALUTATION("SELECTOR", "#salutation-field"),
    FIRST_NAME("SELECTOR", "#first-name-field"),
    BIRTHDAY("SELECTOR", "#birthdate-field"),
    LAST_NAME("SELECTOR", "#last-name-field"),
    EMAIL("SELECTOR", "#leads-contact-details-contact-details-type-email-contact-details-value-field"),
    MOBILE_NR("SELECTOR", "#leads-contact-details-contact-details-phone-type-mobile-phone-contact-details-type-phone-contact-details-value-field"),
    WORK_PHONE_NR("SELECTOR", "#leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field"),
    DELIVERY_ADDR_STREET_SUGGESTION("XPATH", "//ul[@class='suggestions above-field']"),
    DELIVERY_ADDR_STREET("XPATH", "(//input[@id='address-street-field'])[1]"),
    DELIVERY_ADDR_HOUSE_NR("XPATH", "(//input[@id='address-number-field'])[1]"),
    DELIVERY_ADDR_HOUSE_ADD("XPATH", "(//input[@id='address-addition-field'])[1]"),
    DELIVERY_ADDR_BUS("XPATH", "(//input[@id='address-city-field'])[1]"),
    DELIVERY_ADDR_ZIPCODE("XPATH", "(//input[@id='address-postalcode-field'])[1]"),
//    KLIK("XPATH", "//*[@id='accounts-aos-quotes-aos-products-quotes-addresses-aos-products-quotes-field-container']/fieldset/autocomplete/ul/li/a/b"),
    DELIVERY_ADDR_CITY("XPATH", "(//input[@id='address-city-field'])[1]"),
    DELIVERY_ADDR_COUNTRY("XPATH", "(//select[@id='address_country'])[1]"),
    ELECTRICITY_EAN_CODE("SELECTOR", "#ean-c-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field");
    private String searchBy;
    private String query;

    B2CQuoteElements(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public Model.Element element() {
        Model.Element element = new Model.Element().search(searchBy).query(this.query).key(this.name());
        return element;
    }
}
