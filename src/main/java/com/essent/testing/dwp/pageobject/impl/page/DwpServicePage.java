package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
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

    public void clickOnSubjectDropdownMenu()  {
        seleniumDriver.waitAndClick(subjectDropdownMenu());
    }

    public WebElement settlementInvoiceDropdownOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//option[@label='Afrekeningsfactuur']"));
    }

    public void clickOnSettlementInvoiceDropdownOption()  {
        seleniumDriver.waitAndClick(settlementInvoiceDropdownOption());
    }

    public WebElement decriptionField() {
        return seleniumDriver.findElementWhenVisible(By.id("cases-description-field"));
    }

    public void enterComplaintText(String string)  {
        seleniumDriver.waitAndSendKeys(decriptionField(),string);
    }

    public WebElement saveButton() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//button[contains(text(),'Opslaan')]"));
    }

    public void clickOnSaveButton()  {
         seleniumDriver.waitAndClick(saveButton());
    }

    public WebElement firstCaseInTheList()  {
          return seleniumDriver.findElementWhenVisible(By.xpath("(//h5)[1]"));
    }

    public void clickOnFirstCaseInTheList()  {
        seleniumDriver.waitAndClick(firstCaseInTheList());
    }

    public WebElement caseDetailsheader()  {
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

    public void clickOnDescribeTheSolutionDropdown()  {
         seleniumDriver.waitAndClick(describeTheSolutionDropdown());
    }

    public WebElement duplicateAccountOption() {
        return seleniumDriver.findElementWhenVisible(By.xpath("//span[contains(text(),'Duplicate account')]"));
    }

    public void clickOnDuplicateAccountOption()  {
         seleniumDriver.waitAndClick(duplicateAccountOption());
    }

    public WebElement duplicateAccountEnterNewNameField() {
        return seleniumDriver.findElementWhenVisible(By.id("name-field"));
    }

    public void enterNewNameForDuplicatedCustomer(String newName)  {
        seleniumDriver.waitAndSendKeys(duplicateAccountEnterNewNameField(),newName);

    }

    public void clickOnConfirmButtonOnDuplicateAccountForm()  {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("confirm-button")));
    }

}
