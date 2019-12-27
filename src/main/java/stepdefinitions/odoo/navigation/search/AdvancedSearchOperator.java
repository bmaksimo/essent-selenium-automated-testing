package stepdefinitions.odoo.navigation.search;

public enum AdvancedSearchOperator {
  IS_EQUAL_TO("is equal to"),
  IS_NOT_EQUAL_TO("is not equal to"),
  CONTAINS("contains"),
  DOES_NOT_CONTAIN("doesn't contain"),
  IS_SET("is set"),
  IS_NOT_SET("is not set");

  AdvancedSearchOperator(String operator) {
    this.operator = operator;
  }

  private String operator;

  public String getOperator() {
    return operator;
  }

  public static AdvancedSearchOperator fromOperator(String operator) {
    AdvancedSearchOperator result = AdvancedSearchOperator.IS_EQUAL_TO;

    for (AdvancedSearchOperator AdvancedSearchOperator : values()) {
      if (AdvancedSearchOperator.getOperator().equalsIgnoreCase(operator)) {
        result = AdvancedSearchOperator;
        break;
      }
    }
    return result;
  }
}
