package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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

    public void ChooseDiverseDagboekKlanten(String journal){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("oe-field-input-16")), journal);
    }

    public void DateDocumentIsToday(){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("dp1545655698408")), date);
    }

    public void ClickOnAddAnItem(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a [contains(text(),\"Add an item\")]")));
    }

    public void SaveJournal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button_save oe_highlight']")));
    }
    public void PostJournal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button oe_highlight']")));
    }

    public void SetName(String name){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='name']")), name);
    }
    public void SetPartner(String partner){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='partner_id']")), partner);
    }
    public void SetAccout(String accout){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='account_id']")), accout);
    }
    public void SetDebit(String debit){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='debit']")), debit);
    }
    public void SetCredit(String credit){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='credit']")), credit);
    }

//    public void Set(String field, String value){
//        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='"+field+"']")), value);
//    }

    public void createNewItem(List<List<String>> table, Integer row) {
        SetName(table.get(row).get(0));
        SetPartner(table.get(row).get(1));
        SetAccout(table.get(row).get(2));
        SetDebit(table.get(row).get(3));
        SetCredit(table.get(row).get(4));

//        Set("name",table.get(row).get(0));
//        Set("partner_id",table.get(row).get(1));
//        Set("account_id",table.get(row).get(2));
//        Set("debit",table.get(row).get(3));
//        Set("credit",table.get(row).get(4));

    }

}
