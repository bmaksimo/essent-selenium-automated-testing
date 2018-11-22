package stepdefinitions.odoo.navigation.menu;


import java.util.HashMap;
import java.util.Map;

public class OdooMenuList {
    public static Map<String, String> menuMap = new HashMap<String, String>();

    static {
        menuMap.put("name", "Reference");
        menuMap.put("date", "Date");
        menuMap.put("period_id", "Period");
        menuMap.put("journal_id", "Journal");
        menuMap.put("balance_start", "Starting Balance");
        menuMap.put("balance_end_real", "Ending Balance");
        menuMap.put("state", "Status");
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    public Map getMenu() {
        return menuMap;
    }
}
