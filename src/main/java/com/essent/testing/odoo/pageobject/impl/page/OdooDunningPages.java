package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OdooDunningPages extends Component {

    public WebElement getBundleIdElement(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@data-field='name']"));
    }
}
