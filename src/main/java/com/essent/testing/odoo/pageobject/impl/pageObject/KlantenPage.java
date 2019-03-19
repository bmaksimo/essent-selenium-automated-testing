package com.essent.testing.odoo.pageobject.impl.pageObject;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;

public class KlantenPage extends Component {

    public String getBankAccountAsString(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//td[@data-field=\"acc_number\"]")).getText();
    }
}
