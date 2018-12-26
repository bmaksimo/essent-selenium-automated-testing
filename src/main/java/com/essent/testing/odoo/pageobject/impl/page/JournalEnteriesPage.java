package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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

    public void clickOnCreateJournalEntery(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_list_add oe_highlight']")));
    }

    public void clickOnDropDownButtonJurnal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//span[@class='oe_m2o_drop_down_button']")));
    }

    public void chooseDiverseDagboekKlanten(String journal){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("oe-field-input-16")), journal);
    }

    public void dateDocumentIsToday(){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("dp1545655698408")), date);
    }

    public void clickOnAddAnItem(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a [contains(text(),\"Add an item\")]")));
    }

    public void saveJournal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button_save oe_highlight']")));
    }
    public void postJournal(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button oe_highlight']")));
    }

    public void setName(String name){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='name']")), name);
    }
    public void setPartner(String partner){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='partner_id']")), partner);
    }
    public void setAccout(String accout){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='account_id']")), accout);
    }
    public void setDebit(String debit){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='debit']")), debit);
    }
    public void setCredit(String credit){
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='credit']")), credit);
    }

//    public void set(String field, String value){
//        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='"+field+"']")), value);
//    }

    public void createNewItem(List<List<String>> table, Integer row) {
        setName(table.get(row).get(0));
        setPartner(table.get(row).get(1));
        setAccout(table.get(row).get(2));
        setDebit(table.get(row).get(3));
        setCredit(table.get(row).get(4));

//        set("name",table.get(row).get(0));
//        set("partner_id",table.get(row).get(1));
//        set("account_id",table.get(row).get(2));
//        set("debit",table.get(row).get(3));
//        set("credit",table.get(row).get(4));

    }

    public WebElement journalItemsCheckBox(Integer row){
        return  seleniumDriver.findElementWhenVisible(By.xpath(("//th[@class='oe_list_record_selector'])["+row+"]//input"))); // first row starts with 81
    }

    public void clickOnJournalItemsCheckBox(Integer row){
        seleniumDriver.waitAndClick(journalItemsCheckBox(row));
    }

    public String journalImtesCredit(Integer row){
        return journalItemsCheckBox(row).findElement(By.xpath("//td[@data-field='credit']")).getText();
    }
    public String journalImtesDebit(Integer row){
        return journalItemsCheckBox(row).findElement(By.xpath("//td[@data-field='debit']")).getText();
    }

    public void clickOnMoreMenuItem(String item){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),'"+item+"')]")));
    }

    public void clickOnconfirm(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button oe_highlight']")));
    }

}
