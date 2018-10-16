package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;

public class ChangeAccountStatusPage extends Component {
    public ChangeAccountStatusPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public ChangeAccountStatusPage(By selector, SeleniumDriver seleniumDriver) {
        super(selector, seleniumDriver);
    }

    public ChangeAccountStatusPage(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
    }

    public void chooseAccountStatus(String status) {
//        Date date = new Date();
        seleniumDriver.findElementWhenVisible(By.id("status-field")).sendKeys(status);
    }
}
