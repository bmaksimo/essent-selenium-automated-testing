package com.billinghouse.javascript.testrunner.dwp.menu;

public enum MenuTests {
    LEFT_MENU_ITEM_TEST("var runner = new TestRunner('menu_has_link_id', {menu: 'mainMenu', linkId: '${value}'}, arguments[arguments.length - 1]);"),
    GET_COLUMN_INDEX("var runner = new TestRunner('get_column_index_list', {column: '${value}', ordinal: ${value1}}, arguments[arguments.length - 1]);"),
    PLUS_MENU_ITEM_TEST("var runner = new TestRunner('plus_menu_has_item', {item: '${value}', position: ${value1}}, arguments[arguments.length - 1]);"),
    GET_OVERVIEW_MENU_INDEX("var runner = new TestRunner('get_overview_menu_index', {menu: '${value}'}, arguments[arguments.length - 1]);"),
    SELECT_PLUS_MENU_ITEM("var runner = new TestRunner('select_plus_menu_item', {menu: '${value}'}, arguments[arguments.length - 1]);"),
    GET_TOP_MENU("var runner = new TestRunner('get_top_menu', {menu: '${value}'}, arguments[arguments.length - 1]);"),
    GET_LEFT_MENU("var runner = new TestRunner('get_left_menu', {menu: '${value}'}, arguments[arguments.length - 1]);"),
    GET_TOP_TAB("var runner = new TestRunner('get_top_tab', {menu: '${value}'}, arguments[arguments.length - 1]);"),
    SELECT_ASSIGNMENT_CATEGORY("var runner = new TestRunner('select_assignment_category', {category: '${value}'}, arguments[arguments.length - 1]);"),
    SELECT_ASSIGNMENT_TYPE("var runner = new TestRunner('select_assignment_type', {type: '${value}'}, arguments[arguments.length - 1]);");

    private MenuTests(String test) {
        this.test = test;
    }
    private String test;
    public String getTest() {
        return test;
    }
}
