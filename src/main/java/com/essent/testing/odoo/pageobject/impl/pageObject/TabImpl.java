package com.essent.testing.odoo.pageobject.impl.pageObject;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;

public class TabImpl extends Component {

    public void clickOnTabMenu(String tab){
        awaitOdooRequestToFinish(20);
//        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.linkText(tab)));
//        seleniumDriver.waitAndClick(seleniumDriver.findElements(By.linkText(tab)).get(1));
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//li[@class='ui-state-default ui-corner-top']/a[contains(text(),'"+tab+"')] ")));
    }
}
