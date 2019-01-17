package com.essent.testing.odoo.pageobject.impl.page;

import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class JournalEnteriesPage extends Component {
    private String pattern = "MM/dd/yyyy";
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
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("oe-field-input-10")), journal);
    }

    public void dateDocumentIsToday(){
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//input[@name='date_document']")), date);
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
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='name']/input")), name);
    }

    public void setPartner(String partner){
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='partner_id']/div/input")), partner);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(//li[@class='ui-menu-item']/a)[2]")));
    }

    public WebElement account(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='account_id']/div/input"));
    }

    public void setAccout(String accout){
        Sleeper.sleepTightInSeconds(5);
        seleniumDriver.waitAndClick(account());
        account().clear();
        seleniumDriver.waitAndSendKeys(account(), accout);
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("(//li[@class='ui-menu-item']/a)[4]")));
    }

    public WebElement debit(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='debit']/input"));
    }

    public void setDebit(String debit){
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndClick(debit());
        debit().clear();
        seleniumDriver.waitAndSendKeys(debit(), debit);
    }

    public WebElement credit(){
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[@data-fieldname='credit']/input"));
    }

    public void setCredit(String credit){
        Sleeper.sleepTightInSeconds(3);
        seleniumDriver.waitAndClick(credit());
        credit().clear();
        seleniumDriver.waitAndSendKeys(credit(), credit);
    }

    public void createNewItem(List<List<String>> table, Integer row, String partnerNumber) {
        if (row == 2){
            setPartner(partnerNumber);
            setAccout(table.get(row).get(2));
        }else {
            setName(table.get(row).get(0));
            setPartner(partnerNumber);
            setAccout(table.get(row).get(2));
            setDebit(table.get(row).get(3));
            setCredit(table.get(row).get(4));
        }
    }

    public WebElement journalItemsCheckBox(Integer row){
        return  seleniumDriver.findElementWhenVisible(By.xpath(("(//tbody/tr["+row+"]/th/input)[2]")));
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

    public WebElement more(){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//button[@class='oe_dropdown_toggle oe_dropdown_arrow'])[8]"));
    }

    public void clickOnMoreMenuItem(String item){
        seleniumDriver.waitAndClick(more());
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//a[contains(text(),'"+item+"')]")));
    }

    public void clickOnconfirm(){
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.xpath("//button[@class='oe_button oe_form_button oe_highlight']")));
    }

    public WebElement reconcile(Integer row){
        return seleniumDriver.findElementWhenVisible(By.xpath("(//td[@data-field='reconcile_id'])["+row+"]/a"));
    }

    public String reconcileText(Integer row){
        return reconcile(row).getText();
    }

}
