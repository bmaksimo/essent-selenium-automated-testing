package com.essent.testing.dwp.quote.elements;

import com.essent.automation.autocrat.Model;

public enum B2CQuoteElements {
    HOME_BUTTON("XPATH", "//a[@class='home']"),
    SALES_TC1_NEWQUOTE_ACCORDION_ITEM("XPATH", "//labeled-accordion-wrapper[@label='TC1']/div/div/ul/menu-button//menu-link[@label='Create new quote B2C']/li/a"),
    REGU_CHECKBOX("SELECTOR", "#accounts-aos-quotes-regularisation-c-field"),
    SALES_CHANNEL_FIELD("SELECTOR", "#accounts-aos-quotes-sales-channel-id-field"),
    SELECT_QOUTE_TYPE_ACTIVE("XPATH", "//div/progress-bar/ul/li[contains(@class, 'status-active')] and a[contains(., 'Select Quote type')]]/a"),
    SELECT_QOUTE_TYPE_DONE("XPATH", "//div/progress-bar/ul/li[a[not(contains(@class, 'status-active'))] and a[contains(., 'Select Quote type')]]/a"),
    CUSTOMER_DETAILS_ACTIVE("XPATH", "//div/progress-bar/ul/li[a[not(contains(@class, 'status-active'))] and a[contains(., 'Customer details')]]/a"),
    CONNECTION_DETAILS_ACTIVE("XPATH", "//div/progress-bar/ul/li[a[not(contains(@class, 'status-active'))] and a[contains(., 'Connection details')]]/a"),
    SELECT_PACKAGE_ACTIVE("XPATH", "//div/progress-bar/ul/li[a[not(contains(@class, 'status-active'))] and a[contains(., 'Select package & products')]]/a"),
    BILLING_DETAILS_ACTIVE("XPATH", "//div/progress-bar/ul/li[a[not(contains(@class, 'status-active'))] and a[contains(., 'Billing details')]]/a"),
    SIGNATURE_OPTIONS_ACTIVE("XPATH", "//div/progress-bar/ul/li[a[not(contains(@class, 'status-active'))] and a[contains(., 'Overview & signature options ')]]/a"),
    COPY_ADDRESS_CONNECTION_TO_BILLING("SELECTOR", "#dwp-copy-address-connection-to-billing-field"),
    SALUTATION("SELECTOR", "#salutation-field"),
    FIRST_NAME("SELECTOR", "#first-name-field"),
    BIRTHDAY("SELECTOR", "#birthdate-field"),
    LAST_NAME("SELECTOR", "#last-name-field"),
    EMAIL("SELECTOR", "#leads-contact-details-contact-details-type-email-contact-details-value-field"),
    MOBILE_NR("SELECTOR", "#leads-contact-details-contact-details-phone-type-mobile-phone-contact-details-type-phone-contact-details-value-field"),
    WORK_PHONE_NR("SELECTOR", "#leads-contact-details-contact-details-phone-type-work-phone-contact-details-type-phone-contact-details-value-field"),
    DELIVERY_ADDR_STREET_SUGGESTION("XPATH", "//ul[@class='suggestions above-field']"),
    DELIVERY_ADDR_STREET("XPATH", "//validation-wrapper/div[label[text() = 'Delivery address']]//input[@id='address-street-field']"),
    DELIVERY_ADDR_HOUSE_NR("XPATH", "//validation-wrapper/div[label[text() = 'Delivery address']]//input[@id='address-number-field']"),
    DELIVERY_ADDR_HOUSE_ADD("XPATH", "//validation-wrapper/div[label[text() = 'Delivery address']]//input[@id='address-addition-field']"),
    DELIVERY_ADDR_BUS("XPATH", "//validation-wrapper/div[label[text() = 'Delivery address']]//input[@id='address-bus-field']"),
    DELIVERY_ADDR_ZIPCODE("XPATH", "//validation-wrapper/div[label[text() = 'Delivery address']]//input[@id='address-postalcode-field']"),
    DELIVERY_ADDR_CITY("XPATH", "//validation-wrapper/div[label[text() = 'Delivery address']]//input[@id='address-city-field']"),
    DELIVERY_ADDR_COUNTRY("XPATH", "//validation-wrapper/div[label[text() = 'Delivery address']]//input[@id='address_country']"),
    SIMILAR_CUSTOMER_ALERT("XPATH", "//div[h5[contains(text(), 'Similar Accounts')]]/a[@class='button icon-close']");

    private String searchBy;
    private String query;
    private String pick;

    private B2CQuoteElements(String searchBy, String query, String pick) {
        this.searchBy = searchBy;
        this.query = query;
        this.pick = pick;
    }
    private B2CQuoteElements(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public Model.Element element() {
        Model.Element element = new Model.Element().search(searchBy).query(this.query).key(this.name());
        if(pick == null) {
            return element;
        } else {
            return element.pick(pick);
        }
    }
}
