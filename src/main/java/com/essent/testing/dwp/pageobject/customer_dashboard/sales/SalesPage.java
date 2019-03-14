package com.essent.testing.dwp.pageobject.customer_dashboard.sales;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;

public class SalesPage extends Component {
    public void inputText(String text) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//text-angular[@id='description-field']/div[2]/div[.=' ']")), text);
    }
}
