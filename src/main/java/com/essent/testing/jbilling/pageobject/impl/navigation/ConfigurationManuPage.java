package com.essent.testing.jbilling.pageobject.impl.navigation;

import com.essent.testing.jbilling.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.jbilling.SeleniumDriverJBillingImpl;
import org.openqa.selenium.By;

;

public class ConfigurationManuPage extends Component {

    public ConfigurationManuPage(SeleniumDriverJBillingImpl seleniumDriver){
        super(seleniumDriver);
    }

    public void configurationTopMenu(String top){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"breadcrumbs\"]/ul/li/a[contains(text(),'"+top+"')]")));
    }

    public void configurationLeftMenu(String left){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"left-column\"]/div/ul/li/a[contains(text(),'"+left+"')]")));
    }

}
