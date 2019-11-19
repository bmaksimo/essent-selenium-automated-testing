package com.essent.testing.odoo.table;

import com.essent.testing.context.ContextService;
import com.essent.testing.selenium.SeleniumDriver;
import com.essent.testing.table.TableFilterBase;

public class OdooTableFilter extends TableFilterBase {

    private static final String TABLE_PATH_BASE = "//div[@data-view-type='list']//table[@class='oe_list_content']";
    private static final String TABLE_ROWS_BASE = "//tbody/tr";

    public OdooTableFilter(String contextParameters) {
        super(contextParameters, (SeleniumDriver) ContextService.getContext().getBean("odooSeleniumDriver"), TABLE_PATH_BASE, TABLE_ROWS_BASE);
    }
}
