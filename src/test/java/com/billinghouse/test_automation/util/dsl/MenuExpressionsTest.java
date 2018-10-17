package com.billinghouse.test_automation.util.dsl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuExpressionsTest {
    @Test
    public void testMenuPath() throws Exception {
        String menuPath = "A->B->C->d";
        String pathSeparator = "\\s*->\\s*";
        List<String> menu = new ArrayList<>(Arrays.asList(menuPath.split(pathSeparator)));
        List<String> path = menu.subList(0, menu.size() - 1);
        String action = menu.get(menu.size() - 1);
        System.out.println(StringUtils.join(path));
        System.out.println(action);
        findMenu(null, path);
    }

    private String findMenu(String item, List<String> menu) {
        if (menu == null) {
            return null;
        }
        if (menu.isEmpty()) {
            System.out.println("-TERMINAL MENU ITEM: " + item);
            return item;
        } else {
            System.out.println("-MENU ITEM: " + item);
        }
        String menuItem = menu.remove(0);
        return this.findMenu(menuItem, menu);
    }
}
