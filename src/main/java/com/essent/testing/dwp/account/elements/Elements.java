package com.essent.testing.dwp.account.elements;

import com.essent.automation.autocrat.Model;

public enum Elements {
    ACCOUNT_HEADER("XPATH", "//div[@class='card__header']/h1[contains(text(), '${value1}') and contains(text(), '${value2}')]");

    private String searchBy;
    private String query;
    private String pick;

    private Elements(String searchBy, String query, String pick) {
        this.searchBy = searchBy;
        this.query = query;
        this.pick = pick;
    }

    private Elements(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public Model.Element element() {
        Model.Element element = new Model.Element().search(searchBy).query(this.query).key(this.name());
        if (pick == null) {
            return element;
        } else {
            return element.pick(pick);
        }
    }

    public Model.Element element(Object... value) {
        Model.Element element = this.element();
        for(int i = 0; i < value.length; i++) {
            element.query = element.query.replace(String.format("${value%s}", i + 1), value[i].toString());
        }
        return element;
    }
}
