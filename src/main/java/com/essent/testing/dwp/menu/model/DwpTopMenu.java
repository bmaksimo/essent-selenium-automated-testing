package com.essent.testing.dwp.menu.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DwpTopMenu {
    TASKS("Tasks"),
    MARKET_TRANSACTIONS("Market Transaction"),
    LEADS("Leads"),
    MY_ACCOUNTS("My Accounts"),
    ACCOUNTS("Accounts"),
    QUOTES("Quotes"),
    CONTRACTS("Contracts"),
    PRICING_TOOL("Pricing Tool"),
    CAMPAIGNS("Campaigns"),
    EUROCCOR_QUOTES("Euroccor Quotes");

    private DwpTopMenu(String name) {
        this.label = name;
    }

    private String label;

    public String getLabel() { return label; }

    private static final Map<String, DwpTopMenu> lookup = new HashMap<>();

    static {
        for (DwpTopMenu d : EnumSet.allOf(DwpTopMenu.class)) {
            lookup.put(d.getLabel(), d);
        }

    }

    public static DwpTopMenu get(String label) {
        return lookup.get(label);
    }
}
