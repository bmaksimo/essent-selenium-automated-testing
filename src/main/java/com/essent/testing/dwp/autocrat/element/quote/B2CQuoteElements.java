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
    DELIVERY_ADDR_STREET("XPATH", "//div[@id='accounts-aos-quotes-aos-products-quotes-addresses-aos-products-quotes-field-container']//input[@id='address-street-field']"),
    DELIVERY_ADDR_HOUSE_NR("XPATH", "//div[@id='accounts-aos-quotes-aos-products-quotes-addresses-aos-products-quotes-field-container']//input[@id='address-number-field']"),
    DELIVERY_ADDR_HOUSE_ADD("XPATH", "//div[@id='accounts-aos-quotes-aos-products-quotes-addresses-aos-products-quotes-field-container']//input[@id='address-addition-field']"),
    DELIVERY_ADDR_BUS("XPATH", "//div[@id='accounts-aos-quotes-aos-products-quotes-addresses-aos-products-quotes-field-container']//input[@id='address-city-field']"),
    DELIVERY_ADDR_ZIPCODE("XPATH", "//div[@id='accounts-aos-quotes-aos-products-quotes-addresses-aos-products-quotes-field-container']//input[@id='address-postalcode-field']"),
    DELIVERY_ADDR_CITY("XPATH", "//div[@id='accounts-aos-quotes-aos-products-quotes-addresses-aos-products-quotes-field-container']//input[@id='address-city-field']"),
    DELIVERY_ADDR_COUNTRY("XPATH", "//div[@id='accounts-aos-quotes-aos-products-quotes-addresses-aos-products-quotes-field-container']//select[@id='address_country']");
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
