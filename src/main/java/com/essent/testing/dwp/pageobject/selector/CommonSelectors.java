package com.essent.testing.dwp.pageobject.selector;

import com.essent.automation.autocrat.Model;

public enum CommonSelectors {
    CARD_TEMPLATE("XPATH", "//div[div[normalize-space(h2/text())='${title}']]"),
    VIEW("XPATH", "//dwp-app//div[@ui-view = 'focus-mode']"),
    NEXT_BUTTON("SELECTOR", "#primaryButton:not([disabled])");

    private String searchBy;
    private String query;

    CommonSelectors(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public Model.Element element() {
        return new Model.Element().search(searchBy).query(this.query).key(this.name());
    }
}
