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
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='oe_list oe_view oe_cannot_create']//td[@data-field='state']"));
    }

    public WebElement getDunningInstanceDescription() {
        awaitOdooRequestToFinish(60);
        Sleeper.sleepTightInSeconds(4);
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='oe_list oe_view oe_cannot_create']//td[@data-field='description']"));
    }

    public WebElement getDunningInstanceCostEntry() {
        awaitOdooRequestToFinish(60);
        Sleeper.sleepTightInSeconds(4);
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='oe_list oe_view oe_cannot_create']//td[@data-field='cost_move_line_id']"));
    }

    public WebElement getDunningInstanceLetterState() {
        awaitOdooRequestToFinish(60);
        Sleeper.sleepTightInSeconds(4);
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='oe_list oe_view oe_cannot_create']//td[@data-field='correspondence_id']"));
    }

    public WebElement getDunningInvoiceNumber() {
        awaitOdooRequestToFinish(60);
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='oe_list oe_view oe_cannot_create']//td[@data-field='move_line_id'] "));
    }
}
