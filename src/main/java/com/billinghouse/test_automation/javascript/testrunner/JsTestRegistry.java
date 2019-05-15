package com.billinghouse.test_automation.javascript.testrunner;

import java.util.HashSet;
import java.util.Set;

public class JsTestRegistry {
  public static final String JS_TR_SELECT_LIST_ROWS = "TrSelectListRows";
  public static final String JS_TR_FETCH_DATA_SELECTION = "TrFetchDataSelection";
  public static final String JS_TR_GET_TABLE_MODEL = "TrGetTableModel";
  public static final String JS_TR_CLICK_TABLE_CELL_URL = "TrClickTableCellUrl";
  public static final String JS_TR_PLUS_ACTION_IN_MARKET_MESSAGE_TABLE =
      "TrPlusActionInMarketMessageTable";
  public static final String JS_TR_PLUS_ACTION_IN_BILLING_CUSTOMER_TABLE =
      "TrPlusActionInBillingCustomerTable";
  public static final String JS_TR_CLICK_TABLE_ROW_ACTION = "TrClickTableRowAction";
  public static final String JS_TR_CHECK_EMPTY_TABLE = "TrCheckEmptyTable";
  public static final String JS_TR_CHECK_FIRST_ROW_BY_OPTION = "TrCheckFirstRowByOption";
  public static final String JS_BASE_FORM_INPUT = "BaseFormInput";
  public static final String JS_TR_FORM_SELECTION = "TrFormSelection";
  public static final String JS_TR_DATE_PICKER_INPUT = "TrDatePickerInput";
  public static final String JS_TR_TOGGLE_CHECK_BOX = "TrToggleCheckBox";
  public static final String JS_TR_APPLY_FORM_INPUT = "TrApplyFormInput";
  public static final String JS_TR_TOGGLE_INPUT_STATE = "TrToggleInputState";
  public static final String JS_TR_GET_RANDOM_USER = "TrGetRandomUser";
  public static final String JS_TR_CHECK_VIEW_LIST_HEADER = "TrCheckViewListHeader";
  public static final String JS_TR_GET_LIST_ACTION = "TrGetListAction";
  public static final String JS_TR_SELECT_LIST_ROW = "TrSelectListRow";
  public static final String JS_TR_OPEN_LIST_PLUS_ACTIONS = "TrOpenListPlusActions";
  public static final String JS_TR_CHECK_FORM_HEADER = "TrCheckFormHeader";
  public static final String JS_TR_EAN_CHECK_BOX = "TrEanCheckBox";
  public static final String JS_TR_SUBMIT_FORM = "TrSubmitForm";
  public static final String JS_TR_CLICK_TOGGLE_INPUT = "TrClickToggleInput";
  public static final String JS_TR_OPEN_MULTIPLE_INPUT_DIALOG = "TrOpenMultipleInputDialog";
  public static final String JS_TR_APPLY_MULTIPLE_FILTER_INPUT = "TrApplyMultipleFilterInput";
  public static final String JS_TR_CHECK_MODAL_DIALOG = "TrCheckModalDialog";
  public static final String JS_TR_IS_NEXT_BUTTON_ENABLED = "TrIsNextButtonEnabled";
  public static final String JS_TR_GET_COLUMN_INDEX_LIST = "TrGetColumnIndexList";
  public static final String JS_TR_SELECT_EAN_CODE = "TrSelectEanCode";
  public static final String JS_TR_CHECK_DOCUMENT_TYPE = "TrCheckDocumentType";
  public static final String JS_TR_CHECK_TABLE_CELL_VALUE = "TrCheckTableCellValue";
  public static final String JS_TR_PLUS_MENU_SELECT_ACTION = "TrPlusMenuSelectAction";
  public static final String JS_TR_GET_COCKPIT_ITEM = "TrGetCockpitItem";
  public static final String JS_TR_LIST_PLUS_MENU_ACTION = "TrListPlusMenuAction";
  public static final String JS_TR_ARROW_ACTION = "TrArrowAction";
  public static final String JS_TR_SELECT_BUTTON = "TrSelectButton";
  public static final String JS_TR_CLICK_DASHBOARD_MENU_BUTTON = "TrClickDashboardMenuButton";
  public static final String JS_TR_FIND_CUSTOMER = "TrFindCustomer";
  public static final String JS_TR_SEARCH_CUSTOMER = "TrSearchCustomer";
  public static final String JS_TR_SELECT_CONTRACTLINE = "TrSelectContractline";
  public static final String JS_TR_PAYMENT_DETAILS_MODAL_SAVE_ACTION =
      "TrPaymentDetailsModalSaveAction";
  public static final String JS_TR_SWITCH_PAYMENT_METHOD = "TrSwitchPaymentMethod";
  public static final String JS_TR_ADD_IBAN_TO_PAYMENT_DETAILS = "TrAddIBANToPaymentDetails";
  private static final JsTestRegistry instance = new JsTestRegistry();
    public static final JsTestRegistry get() { return instance; }
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
