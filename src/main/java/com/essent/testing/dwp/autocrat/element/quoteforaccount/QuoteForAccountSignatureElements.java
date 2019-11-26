package com.essent.testing.dwp.autocrat.element.quoteforaccount;

import com.essent.automation.autocrat.Model;

public enum QuoteForAccountSignatureElements {
    SIGN_UPLOAD_DOC("SELECTOR", "#signed-contract-docguid-c-field:not([disabled])");

    private String searchBy;
    private String query;

    QuoteForAccountSignatureElements(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public Model.Element element() {
        Model.Element element = new Model.Element().search(searchBy).query(this.query).key(this.name());
        return element;
    }
}
