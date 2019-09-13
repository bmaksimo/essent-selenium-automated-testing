package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;

public class AccountBlockPage extends Component {
    private static String ACCOUNT_BLOCK_REASON = "//table[@class='oe_list_content'][1]//tbody//tr[1]//td[@data-field='reason_id']";
    private static String ACCOUNT_BLOCK_START_DATE = "//table[@class='oe_list_content'][1]//tbody//tr[1]//td[@data-field='start_date']";
    private static String ACCOUNT_BLOCK_END_DATE = "//table[@class='oe_list_content'][1]//tbody//tr[1]//td[@data-field='end_date']";

    public String getAccountBlockReason() {
        return seleniumDriver.findElementWhenVisible(By.xpath(ACCOUNT_BLOCK_REASON)).getText();
    }

    public String getAccountBlockStartDate() {
        return seleniumDriver.findElementWhenVisible(By.xpath(ACCOUNT_BLOCK_START_DATE)).getText();
    }

    public String getAccountBlockEndDate() {
        return seleniumDriver.findElementWhenVisible(By.xpath(ACCOUNT_BLOCK_END_DATE)).getText();
    }
}
