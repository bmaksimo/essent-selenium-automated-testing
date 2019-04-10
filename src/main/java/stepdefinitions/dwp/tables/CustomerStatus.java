package stepdefinitions.dwp.tables;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;

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
            .anyMatch(customerStatus -> customerStatus.customerStatusName.equalsIgnoreCase(customerStatusName));
    }
}
