package com.essent.testing.dwp.autocrat.element.quote;

import com.essent.automation.autocrat.Model;

public enum TariffElements {
    NO_PRICESHEET_ALERT("XPATH", "//p[@class='icon-alert' and contains(text(), 'No tariffsheetprice found')]"),
    TARIFFSHEET("SELECTOR", "#accounts-aos-quotes-aos-products-quotes-tariffsheet-id-field"),
    PACKAGE("SELECTOR", "#accounts-aos-quotes-aos-products-quotes-package-id-field");

    private String searchBy;
    private String query;
    private String pick;

    private TariffElements(String searchBy, String query, String pick) {
        this.searchBy = searchBy;
        this.query = query;
        this.pick = pick;
    }

    private TariffElements(String searchBy, String query) {
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
