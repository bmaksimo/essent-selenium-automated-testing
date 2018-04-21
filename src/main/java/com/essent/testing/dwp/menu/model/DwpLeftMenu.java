package com.essent.testing.dwp.menu.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jim on 3-1-2018.
 */
public enum DwpLeftMenu {
    SALES_MARKETING("sales-marketing-link", "Sales Marketing"),
    CONTRACTING_SWITCHING("contracting-switching-link", "Contracting Switching"),
    BILLING("billing-link", "Billing"),
    CREDIT_MANAGEMENT("credit-management-link", "Credit Management"),
    FINANCE("finance-link", "Finance"),
    SERVICE("service-link", "Service"),
    ESS("ess-link", "Ess"),
    TASKS("tasks-link", "Tasks"),
    ADMIN("admin-link", "Admin"),
    TEST("test-link", "Test");

    private DwpLeftMenu(String menuItemLink, String name) {
        this.menuItemLink = menuItemLink;
        this.label = name;
    }

    private String menuItemLink;
    private String label;

    public String getMenuItemLink() { return menuItemLink; }
    public String getLabel() { return label; }

    private static final Map<String, DwpLeftMenu> lookup = new HashMap<>();

    static {
        for (DwpLeftMenu d : EnumSet.allOf(DwpLeftMenu.class)) {
            lookup.put(d.getLabel(), d);
        }

    }

    public static DwpLeftMenu get(String label) {
        return lookup.get(label);
    }
}
