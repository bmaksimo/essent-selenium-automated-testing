package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OdooDunningPages extends Component {

    public WebElement getBundleIdElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@data-field='name']"));
    }

    public WebElement getDunningInstanceStatus() {
        awaitOdooRequestToFinish(60);
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@data-field='name']"));
    }

    public WebElement getDunningInvoiceNumber() {
        awaitOdooRequestToFinish(60);
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@data-field='name']"));
    }
}
