package stepdefinitions.odoo.navigation.menu;


import java.util.HashMap;
import java.util.Map;

public class OdooMenuList {

    private static Map<String, String> menuMap = new HashMap<>();

    static {
        menuMap.put("name", "Reference");
        menuMap.put("date", "Date");
        menuMap.put("period_id", "Period");
        menuMap.put("journal_id", "Journal");
        menuMap.put("balance_start", "Starting Balance");
        menuMap.put("balance_end_real", "Ending Balance");
        menuMap.put("state", "Status");
        menuMap.put("customer_ref", "Account Number");
        menuMap.put("generate_coda", "generate_coda");
        menuMap.put("reason_id", "Reason");
    }

    public static <V> String getKey(V value) {
        for (String key : menuMap.keySet()) {
            if (value.equals(menuMap.get(key))) {
                return key;
            }
        }
        return null;
    }

    public static Map<String, String> getMenu() {
        return menuMap;
    }
}
