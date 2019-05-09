package stepdefinitions.dwp.tables;

import java.util.Arrays;

//TODO
//This enum should be removed.
//It distracts the developers from basic rule: DWP should support at least 2 languages.
//Any workaround, circumventing this basic rule, is prohibited
public enum CustomerStatus {
  ACCEPTED("Geaccepteerd"),
  GUARANTEE("Waarborg"),
  DECLINED("Geweigerd");

  private String customerStatusName;

  CustomerStatus(String customerStatusName) {
    this.customerStatusName = customerStatusName;
  }

  public String getCustomerStatusName() {
    return this.customerStatusName;
  }

  public static boolean containsStatus(String customerStatusName) {
    return Arrays.stream(CustomerStatus.values())
        .anyMatch(
            customerStatus ->
                customerStatus.customerStatusName.equalsIgnoreCase(customerStatusName));
  }
}
