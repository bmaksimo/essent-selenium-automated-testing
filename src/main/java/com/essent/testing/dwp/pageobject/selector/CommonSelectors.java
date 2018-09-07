package com.essent.testing.dwp.pageobject.selector;

import com.essent.automation.autocrat.Model;

public enum CommonSelectors {
    VIEW("XPATH", "//dwp-app//div[@ui-view = 'focus-mode']"),
    SIBLING_OVERLAYING_ICONS("XPATH", "../span[contains(@class, 'icon')]"),
    NEXT_BUTTON("SELECTOR", "#primaryButton:not([disabled])");

    private String searchBy;
    private String query;
    private String pick;

    private CommonSelectors(String searchBy, String query, String pick) {
        this.searchBy = searchBy;
        this.query = query;
        this.pick = pick;
    }
    private CommonSelectors(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public String getQuery() {
        return query;
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
