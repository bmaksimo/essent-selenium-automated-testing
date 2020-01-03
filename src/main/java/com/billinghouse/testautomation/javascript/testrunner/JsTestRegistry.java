package com.billinghouse.testautomation.javascript.testrunner;

import java.util.HashSet;
import java.util.Set;

public class JsTestRegistry {
  public static final String JS_TR_FETCH_DATA_SELECTION = "TrFetchDataSelection";
  public static final String JS_TR_GET_TABLE_MODEL = "TrGetTableModel";
  public static final String JS_TR_CLICK_TABLE_CELL_URL = "TrClickTableCellUrl";
  public static final String JS_BASE_FORM_INPUT = "BaseFormInput";
  public static final String JS_TR_DATE_PICKER_INPUT = "TrDatePickerInput";
  public static final String JS_TR_APPLY_FORM_INPUT = "TrApplyFormInput";
  public static final String JS_TR_CLICK_TOGGLE_INPUT = "TrClickToggleInput";
  public static final String JS_TR_CHECK_TABLE_CELL_VALUE = "TrCheckTableCellValue";

  private static final JsTestRegistry instance = new JsTestRegistry();

  public static final JsTestRegistry get() {
    return instance;
  }

  private static final Set<String> javaScriptClasses = new HashSet<>();

  private JsTestRegistry() {}

  public boolean register(String jsClass) {
    return javaScriptClasses.add(jsClass);
  }

  public boolean contains(String jsClass) {
    return javaScriptClasses.contains(jsClass);
  }
}
