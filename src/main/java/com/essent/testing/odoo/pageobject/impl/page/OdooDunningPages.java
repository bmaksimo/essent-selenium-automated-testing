package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OdooDunningPages extends Component {

    public WebElement getBundleIdElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@data-field='name']"));
    }

    public WebElement getDunningInstanceState() {
        awaitOdooRequestToFinish(60);
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='oe_list oe_view oe_cannot_create']//td[@data-field='state']"));
    }

    public WebElement getDunningInvoiceNumber() {
        awaitOdooRequestToFinish(60);
        return seleniumDriver.findElementWhenVisible(By.xpath("//div[@class='oe_list oe_view oe_cannot_create']//td[@data-field='move_line_id'] "));
    }
}
