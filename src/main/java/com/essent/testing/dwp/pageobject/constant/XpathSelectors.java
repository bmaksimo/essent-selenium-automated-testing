package com.essent.testing.dwp.pageobject.constant;

public enum XpathSelectors {
    VIEW_SELECTOR("//dwp-app//div[@ui-view = 'focus-mode']"),
    TITLE_SELECTOR_TEMPLATE("//h2[normalize-space(text())='${value}']");
    private String query;

    public String getQuery() {
        return query;
    }

    private XpathSelectors(String query) {
        this.query = query;
    }
}
