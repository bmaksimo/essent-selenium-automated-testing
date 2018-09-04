package com.essent.testing.dwp.pageobject.b2b_regression;

import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DwpServicePage  {

    private SeleniumDriver seleniumDriver;

    public DwpServicePage(SeleniumDriver seleniumDriver) {
        this.seleniumDriver = seleniumDriver;
    }

    public WebElement plusIcon() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//a[@name='Plus Menu']"));
    }

    public void clickOnplusIcon() throws InterruptedException {
        Thread.sleep(3000);
        plusIcon().click();
    }

    public WebElement serviceDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Service')]"));
    }

    public void clickOnServiceDropdownMenu() throws InterruptedException {
        Thread.sleep(2000);
        serviceDropdownMenu().click();
    }

    public WebElement logAcaseForAccountOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Log a case for account')]"));
    }

    public void clickOnLogAcaseForAccountOption() throws InterruptedException {
        Thread.sleep(500);
        logAcaseForAccountOption().click();
    }

    public WebElement subjectDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-name-field"));
    }

    public void clickOnSubjectDropdownMenu() throws InterruptedException {
        Thread.sleep(1000);
        subjectDropdownMenu().click();
    }

    public WebElement settlementInvoiceDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Settlement invoice']"));
    }

    public void clickOnSettlementInvoiceDropdownOption() throws InterruptedException {
        Thread.sleep(500);
        settlementInvoiceDropdownOption().click();
    }

    public WebElement decriptionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-description-field"));
    }

    public void enterComplaintText(String string) throws InterruptedException {
        Thread.sleep(1000);
        decriptionField().click();
        decriptionField().clear();
        decriptionField().sendKeys(string);
    }

    public WebElement saveButton() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//button[contains(text(),'Save')]"));
    }

    public void clickOnSaveButton() throws InterruptedException {
        Thread.sleep(500);
        saveButton().click();
    }

    public WebElement firstCaseInTheList() throws InterruptedException {
        Thread.sleep(4000);
        return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]"));
    }

    public void clickOnFirstCaseInTheList() throws InterruptedException {
        firstCaseInTheList().click();
    }

    public WebElement caseDetailsheader() throws InterruptedException {
        //Thread.sleep(3000);
        return seleniumDriver.findElementWhenVisible(By.xpath("//*[contains(text(),' Case Details ')]"));
    }

    public WebElement complaintField() {
        return seleniumDriver.findElementWhenVisible(By.id("description-field"));
    }

    public String getComplaintText() {
        return seleniumDriver.findElementWhenVisible(By.id("description-field")).getText();
    }

    public WebElement describeTheSolutionDropdown() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'DESCRIBE THE SOLUTION')]"));
    }

    public void clickOnDescribeTheSolutionDropdown() throws InterruptedException {
        Thread.sleep(300);
        describeTheSolutionDropdown().click();
    }

    public WebElement duplicateAccountOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Duplicate account')]"));
    }

    public void clickOnDuplicateAccountOption() throws InterruptedException {
        Thread.sleep(300);
        duplicateAccountOption().click();
    }

    public WebElement duplicateAccountEnterNewNameField() {
        return seleniumDriver.findElementWhenVisible(By.id("name-field"));
    }

    public void enterNewNameForDuplicatedCustomer(String newName) throws InterruptedException {
        Thread.sleep(2000);
        duplicateAccountEnterNewNameField().click();
        duplicateAccountEnterNewNameField().clear();
        Thread.sleep(100);
        duplicateAccountEnterNewNameField().sendKeys(newName);

    }

    public void clickOnConfirmButtonOnDuplicateAccountForm() throws InterruptedException {
        Thread.sleep(300);
        seleniumDriver.findElementWhenVisible(By.id("confirm-button")).click();
        Thread.sleep(4000);
    }

}
