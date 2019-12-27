package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.odoo.pageobject.impl.Component;
import org.openqa.selenium.By;

public class CodaPage extends Component {

  public void clickOnClose() {
    seleniumDriver.waitAndClick(
        seleniumDriver.findElementWhenVisible(
            By.xpath(
                "//button[@class='oe_button oe_form_button oe_highlight']/span[contains(text(), 'Close')]")));
  }
}
