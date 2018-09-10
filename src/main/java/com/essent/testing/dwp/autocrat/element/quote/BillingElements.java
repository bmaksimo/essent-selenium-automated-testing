package com.essent.testing.dwp.autocrat.element.quote;

import com.essent.automation.autocrat.Model;

public enum BillingElements {
    PAYMENT_METHOD("SELECTOR", "#accounts-aos-quotes-payment-details-payment-methods-payment-method-field"),
    PAYMENT_IBAN("SELECTOR", "#accounts-aos-quotes-payment-details-bankaccounts-iban-field"),
    PAYMENT_BIC("SELECTOR", "#accounts-aos-quotes-payment-details-bankaccounts-swiftbic-field");
    private String searchBy;
    private String query;
    private String pick;

    private BillingElements(String searchBy, String query, String pick) {
        this.searchBy = searchBy;
        this.query = query;
        this.pick = pick;
    }

    private BillingElements(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public Model.Element element() {
        Model.Element element = new Model.Element().search(searchBy).query(this.query).key(this.name());
        if (pick == null) {
            return element;
        } else {
            return element.pick(pick);
        }
    }
}
