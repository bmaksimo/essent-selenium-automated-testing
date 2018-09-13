package com.essent.testing.dwp.pageobject.impl.Page;

import com.essent.testing.dwp.pageobject.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DwpServicePage extends Component {

    public DwpServicePage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public WebElement subjectDropdownMenu() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-name-field"));
    }

    public void clickOnSubjectDropdownMenu() throws InterruptedException {
        subjectDropdownMenu().click();
    }

    public WebElement settlementInvoiceDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Afrekeningsfactuur']"));
    }

    public void clickOnSettlementInvoiceDropdownOption() throws InterruptedException {
        settlementInvoiceDropdownOption().click();
    }

    public WebElement decriptionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-description-field"));
    }

    public void enterComplaintText(String string) throws InterruptedException {
        decriptionField().click();
        decriptionField().clear();
        seleniumDriver.waitAndSendKeys(decriptionField(),string);
       // decriptionField().sendKeys(string);
    }

    public WebElement saveButton() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//button[contains(text(),'Opslaan')]"));
    }

    public void clickOnSaveButton() throws InterruptedException {
         saveButton().click();
    }

    public WebElement firstCaseInTheList() throws InterruptedException {
          return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]"));
    }

    public void clickOnFirstCaseInTheList() throws InterruptedException {
        firstCaseInTheList().click();
    }

    public WebElement caseDetailsheader() throws InterruptedException {
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
         describeTheSolutionDropdown().click();
    }

    public WebElement duplicateAccountOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Duplicate account')]"));
    }

    public void clickOnDuplicateAccountOption() throws InterruptedException {
         duplicateAccountOption().click();
    }

    public WebElement duplicateAccountEnterNewNameField() {
        return seleniumDriver.findElementWhenVisible(By.id("name-field"));
    }

    public void enterNewNameForDuplicatedCustomer(String newName) throws InterruptedException {
        duplicateAccountEnterNewNameField().click();
        duplicateAccountEnterNewNameField().clear();
        duplicateAccountEnterNewNameField().sendKeys(newName);

    }

    public void clickOnConfirmButtonOnDuplicateAccountForm() throws InterruptedException {
        seleniumDriver.findElementWhenVisible(By.id("confirm-button")).click();
    }

}
