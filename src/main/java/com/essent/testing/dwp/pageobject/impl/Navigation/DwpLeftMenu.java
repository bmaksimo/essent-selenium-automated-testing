package com.essent.testing.dwp.pageobject.impl.Navigation;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DwpLeftMenu extends Component {

    public DwpLeftMenu(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public WebElement salesMarketingLink() throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.id("sales-marketing-link"));
    }

    public void clickOnsalesMarketingLink() throws InterruptedException {
        salesMarketingLink().click();
    }
}
