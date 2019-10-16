package com.billinghouse.test_automation.javascript.testrunner;

import java.util.HashSet;
import java.util.Set;

public class JsTestRegistry {
    public static final String JS_TR_FETCH_DATA_SELECTION = "TrFetchDataSelection";
    public static final String JS_TR_GET_TABLE_MODEL = "TrGetTableModel";
    public static final String JS_TR_CLICK_TABLE_CELL_URL = "TrClickTableCellUrl";
    public static final String JS_TR_PLUS_ACTION_IN_MARKET_MESSAGE_TABLE = "TrPlusActionInMarketMessageTable";
    public static final String JS_TR_PLUS_ACTION_IN_BILLING_CUSTOMER_TABLE = "TrPlusActionInBillingCustomerTable";
    public static final String JS_BASE_FORM_INPUT = "BaseFormInput";
    public static final String JS_TR_FORM_SELECTION = "TrFormSelection";
    public static final String JS_TR_DATE_PICKER_INPUT = "TrDatePickerInput";
    public static final String JS_TR_TOGGLE_CHECK_BOX = "TrToggleCheckBox";
    public static final String JS_TR_APPLY_FORM_INPUT = "TrApplyFormInput";
    public static final String JS_TR_TOGGLE_INPUT_STATE = "TrToggleInputState";
    public static final String JS_TR_GET_LIST_ACTION = "TrGetListAction";
    public static final String JS_TR_CLICK_TOGGLE_INPUT = "TrClickToggleInput";
    public static final String JS_TR_CHECK_TABLE_CELL_VALUE = "TrCheckTableCellValue";
    public static final String JS_TR_LIST_PLUS_MENU_ACTION = "TrListPlusMenuAction";
    public static final String JS_TR_ARROW_ACTION = "TrArrowAction";
    public static final String JS_TR_CLICK_DASHBOARD_MENU_BUTTON = "TrClickDashboardMenuButton";
    public static final String JS_TR_SELECT_CONTRACTLINE = "TrSelectContractline";

    private static final JsTestRegistry instance = new JsTestRegistry();

    public static final JsTestRegistry get() {
        return instance;
    }

    private static final Set<String> javaScriptClasses = new HashSet<>();

    private JsTestRegistry() {
    }

    public boolean register(String jsClass) {
        return javaScriptClasses.add(jsClass);
    }

    public boolean contains(String jsClass) {
        return javaScriptClasses.contains(jsClass);
    }
}
