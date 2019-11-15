package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OdooDunningPages extends Component {

    public WebElement getBundleIdElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@data-field='name']"));
    }

    public WebElement getDunningInstanceState() {
        awaitOdooRequestToFinish(60);
        Sleeper.sleepTightInSeconds(4);
        return seleniumDriver.findElementWhenVisible(buildQueryByXPath("state"));
    }

    public WebElement getDunningInstanceDescription() {
        awaitOdooRequestToFinish(60);
        Sleeper.sleepTightInSeconds(4);
        return seleniumDriver.findElementWhenVisible(buildQueryByXPath("description"));
    }

    public WebElement getDunningInstanceCostEntry() {
        awaitOdooRequestToFinish(60);
        Sleeper.sleepTightInSeconds(4);
        return seleniumDriver.findElementWhenVisible(buildQueryByXPath("cost_move_line_id"));
    }

    public WebElement getDunningInstanceLetterState() {
        awaitOdooRequestToFinish(60);
        Sleeper.sleepTightInSeconds(4);
        return seleniumDriver.findElementWhenVisible(buildQueryByXPath("correspondence_id"));
    }

    public WebElement getDunningInvoiceNumber() {
        awaitOdooRequestToFinish(60);
        return seleniumDriver.findElementWhenVisible(buildQueryByXPath("move_line_id"));
    }

    private By buildQueryByXPath(String dataField) {
        String dunningFieldBase = "//div[@class='oe_list oe_view oe_cannot_create']//td[@data-field='${dataField}']";
        return By.xpath(dunningFieldBase.replace("${dataField}", dataField));
    }
}
