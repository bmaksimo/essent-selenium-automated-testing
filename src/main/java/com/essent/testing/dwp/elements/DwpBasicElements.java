package com.essent.testing.dwp.elements;

import com.essent.automation.autocrat.Model;

public enum DwpBasicElements {
    SIBLING_OVERLAYING_ICONS_XPATH("XPATH", "../span[contains(@class, 'icon')]"),
    PLUS_BUTTON("SELECTOR", "a.icon-plus", "first"),
    NEXT_BUTTON("SELECTOR", "#primaryButton:not([disabled])"),
    SALES_MENUITEM("XPATH", "//labeled-accordion-wrapper[@label='Sales']/a"),
    SALES_TC1_MENUITEM("XPATH", "//labeled-accordion-wrapper[@label='Sales']/div/div/ul/menu-button/labeled-accordion-wrapper[@label='TC1']/a"),
    DWP_SALES_TC1_NEWQUOTE_MENUITEM("XPATH", "//labeled-accordion-wrapper[@label='TC1']/div/div/ul/menu-button//menu-link[@label='Create new quote B2C']/li/a"),
    DWP_FIRST_ERROR("SELECTOR", "flash-message-renderer flash-message:nth-child(1) div a");

    private String searchBy;
    private String query;
    private String pick;

    private DwpBasicElements(String searchBy, String query, String pick) {
        this.searchBy = searchBy;
        this.query = query;
        this.pick = pick;
    }
    private DwpBasicElements(String searchBy, String query) {
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
