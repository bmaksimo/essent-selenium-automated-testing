package com.essent.testing.dwp.autocrat.element.quote;

import com.essent.automation.autocrat.Model;

public enum SignatureElements {
    SIGN_CHANNEL("SELECTOR", "accounts-aos-quotes-sign-channel-c-field"),
    SIGN_WANTTOSIGN_CHECKBOX("SELECTOR", "#dwp-customer-wants-to-sign-field:not([disabled])"),
    SIGN_ALREADYSIGNED_CHECKBOX("SELECTOR", "#dwp-alreadysigned-field:not([disabled])"),
    SIGN_DATE("SELECTOR", "#accounts-aos-quotes-sign-date-c-field"),
    SIGN_LOCATION("SELECTOR", "#accounts-aos-quotes-sign-location-c-field"),
    SIGN_UPLOAD_DOC("SELECTOR", "#accounts-aos-quotes-signed-contract-docguid-c-field:not([disabled])");

    private String searchBy;
    private String query;

    SignatureElements(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public Model.Element element() {
        Model.Element element = new Model.Element().search(searchBy).query(this.query).key(this.name());
        return element;
    }
}
