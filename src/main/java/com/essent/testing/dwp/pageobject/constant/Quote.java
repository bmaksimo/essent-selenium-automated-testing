package com.essent.testing.dwp.pageobject.constant;

public enum Quote {
    QUOTE_DETAILS("Quote details"),
    PERSONAL_DETAILS("Personal details"),
    PACKAGE_FUEL_TYPE("Select package & fuel type"),
    CONNECTION_DETAILS("Connection details"),
    BILLING_DETAILS("Billing details");
    private String text;
    Quote(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
