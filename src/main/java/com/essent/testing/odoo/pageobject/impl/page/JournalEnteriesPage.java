package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.Date;


public class JournalEnteriesPage extends Component {
    private String pattern = "dd/MM/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    private String date = simpleDateFormat.format(new Date());

    public JournalEnteriesPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void ClickOnCreateJournalEntery(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_list_add oe_highlight']")));
    }

    public void ClickOnDropDownButtonJurnal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='oe_m2o_drop_down_button']")));
    }

    public void ChooseDiverseDagboekKlanten(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("oe-field-input-16")));
    }

    public void DateDocumentIsToday(){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("dp1545655698408")),date);
    }

}
