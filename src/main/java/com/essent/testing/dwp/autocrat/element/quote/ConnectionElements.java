package com.essent.testing.dwp.autocrat.element.quote;

import com.essent.automation.autocrat.Model;

public enum ConnectionElements {
    ELEC_METER_OPEN_CHECKBOX("SELECTOR", "#meter-open-c-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field"),
    ELEC_MIG_MODULE("SELECTOR", "#dwp-mig-module-c-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field"),
    ELEC_MOVE_CHECKBOX("SELECTOR", "#move-in-c-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field:not([disabled])"),
    ELEC_OTHEREAN_CHECKBOX("SELECTOR", "#dwp-other-ean-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field:not([disabled])"),
    ELEC_EAN("XPATH", "//input-form-element[@id='ean_c']/div/input"),
    ELEC_METER_NR("SELECTOR", "#meter-no-c-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field"),
    GAS_METER_OPEN_CHECKBOX("SELECTOR", "#meter-open-c-accounts-aos-quotes-aos-products-quotes-a-6565-bd-4-e-0-ec-dee-9-64-fd-58-aca-11-b-3994-field"),
    GAS_MIG_MODULE("SELECTOR", "#dwp-mig-module-c-accounts-aos-quotes-aos-products-quotes-a-6565-bd-4-e-0-ec-dee-9-64-fd-58-aca-11-b-3994-field"),
    GAS_MOVE_CHECKBOX("SELECTOR", "#move-in-c-accounts-aos-quotes-aos-products-quotes-a-6565-bd-4-e-0-ec-dee-9-64-fd-58-aca-11-b-3994-field:not([disabled])"),
    GAS_OTHEREAN_CHECKBOX("SELECTOR", "#dwp-other-ean-accounts-aos-quotes-aos-products-quotes-a-6565-bd-4-e-0-ec-dee-9-64-fd-58-aca-11-b-3994-field:not([disabled])"),
    GAS_EAN("SELECTOR", "#ean-c-accounts-aos-quotes-aos-products-quotes-a-6565-bd-4-e-0-ec-dee-9-64-fd-58-aca-11-b-3994-field"),
    GAS_METER_NR("SELECTOR", "#meter-no-c-accounts-aos-quotes-aos-products-quotes-a-6565-bd-4-e-0-ec-dee-9-64-fd-58-aca-11-b-3994-field"),
    ELEC_MARKET_MOCK("SELECTOR", "#test-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field"),
    GAS_MARKET_MOCK("SELECTOR", "#test-accounts-aos-quotes-aos-products-quotes-a-6565-bd-4-e-0-ec-dee-9-64-fd-58-aca-11-b-3994-field");

    private String searchBy;

    public String getQuery() {
        return query;
    }

    private String query;

    ConnectionElements(String searchBy, String query) {
        this.searchBy = searchBy;
        this.query = query;
    }

    public Model.Element element() {
        Model.Element element = new Model.Element().search(searchBy).query(this.query).key(this.name());
        return element;
    }
}
