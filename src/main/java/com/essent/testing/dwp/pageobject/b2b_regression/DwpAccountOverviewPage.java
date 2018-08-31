package com.essent.testing.dwp.pageobject.b2b_regression;

import com.essent.testing.selenium.SeleniumDriver;
import com.essent.testing.dwp.pageobject.b2b_regression.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import helpers.BaseDriver;
import com.essent.testing.selenium.SeleniumScenario;
import com.essent.testing.dwp.pageobject.Component;

public class DwpAccountOverviewPage extends Component {
    //BaseDriver baseDriver;

    SeleniumDriver webDriver;

    public DwpAccountOverviewPage(SeleniumDriver webDriver) {
        super(webDriver);
    }

    public WebElement overviewHeader() {
        return webDriver.findElementOrNull(By.className("nav-header"));
    }

    public WebElement serviceIcon() {
        return webDriver.findElementOrNull(By.xpath("(//span[@class='icon-agent'])[1]"));
    }

    public void clickOnServiceIcon() throws InterruptedException {
        Thread.sleep(4000);
        serviceIcon().click();
    }

    public void clickArrowUpButton() throws InterruptedException {
        Thread.sleep(4000);
        webDriver.findElementOrNull(By.className("icon-arrow-up")).click();
    }

    public WebElement arrowUpButton() throws InterruptedException {
        Thread.sleep(3000);
        return webDriver.findElementOrNull(By.className("icon-arrow-up"));
    }

    public WebElement workflowsicon() {
        return webDriver.findElementOrNull(By.xpath("(//span[@class='icon-flowchart'])[1]"));
    }

    public void clickOnWorkflowsIcon() throws InterruptedException {
        Thread.sleep(4000);
        workflowsicon().click();
    }

    public WebElement detailsIcon() throws InterruptedException {
        Thread.sleep(5000);
        return webDriver.findElementOrNull(By.xpath("(//span[@class='icon-bedrijf'])[1]"));
    }

    public void clickOnDetailsIcon() throws InterruptedException {
        detailsIcon().click();
        Thread.sleep(2000);

    }

    public WebElement contractIcon() throws InterruptedException {
        Thread.sleep(6000);
        return webDriver.findElementOrNull(By.xpath("//span[@class='icon-contract']"));
    }

    public void clickOnContractIcon() throws InterruptedException {
        contractIcon().click();
    }

    public WebElement plusIcon() {
        return webDriver.findElementOrNull(By.xpath("//a[@name='Plus Menu']"));
    }

    public void clickOnplusIcon() throws InterruptedException {
        Thread.sleep(3000);
        plusIcon().click();
    }

    public WebElement logAcaseForAccountOption() {
        return webDriver.findElementOrNull(By.xpath("//span[contains(text(),'Log a case for account')]"));
    }

    public void clickOnLogAcaseForAccountOption() throws InterruptedException {
        Thread.sleep(500);
        logAcaseForAccountOption().click();
    }

    public WebElement subjectDropdownMenu() {
        return webDriver.findElementOrNull(By.id("cases-name-field"));
    }

    public void clickOnSubjectDropdownMenu() throws InterruptedException {
        Thread.sleep(1000);
        subjectDropdownMenu().click();
    }

    public WebElement settlementInvoiceDropdownOption() {
        return webDriver.findElementOrNull(By.xpath("//option[@label='Settlement invoice']"));
    }

    public void clickOnSettlementInvoiceDropdownOption() throws InterruptedException {
        Thread.sleep(500);
        settlementInvoiceDropdownOption().click();
    }

    public WebElement descriptionField() {
        return webDriver.findElementOrNull(By.id("cases-description-field"));
    }

    public void clickOnDescriptionField() throws InterruptedException {
        Thread.sleep(1000);
        descriptionField();
    }

    public void enterComplaintText(String string) throws InterruptedException {
        Thread.sleep(200);
        descriptionField().click();
        descriptionField().clear();
        descriptionField().sendKeys(string);
    }

    public WebElement saveButton() {
        return webDriver.findElementOrNull(By.xpath("//button[contains(text(),'Save')]"));
    }

    public void clickOnSaveButton() throws InterruptedException {
        Thread.sleep(1000);
        saveButton().click();
    }

    public WebElement serviceDropdownMenu() {
        return webDriver.findElementOrNull(By.xpath("//span[contains(text(),'Service')]"));
    }

    public void clickOnServiceDropdownMenu() throws InterruptedException {
        Thread.sleep(2000);
        serviceDropdownMenu().click();
    }

    public WebElement accountChangesDropdownSubMenu() {
        return webDriver.findElementOrNull(By.xpath("//span[contains(text(),'Account changes')]"));
    }

    public void clickOnAccountChangesDropdownSubMenu() throws InterruptedException {
        Thread.sleep(1000);
        accountChangesDropdownSubMenu().click();
    }

    public WebElement updateAccountDetailsOption() {
        return webDriver.findElementOrNull(By.xpath("//span[contains(text(),'Update account details')]"));
    }

    public void clickOnUpdateAccountDetailsOption() throws InterruptedException {
        Thread.sleep(1000);
        updateAccountDetailsOption().click();
    }

    public WebElement duningStopCheckbox() {
        return webDriver.findElementOrNull(By.id("dunning-stop-c-field"));
    }

    public void clickOnDuningStopCheckbox() throws InterruptedException {
        Thread.sleep(4000);
        if (checkIfDunningStopCheckboxIsChecked()) {
            System.out.println("DUNNING STOP WAS ALREADY ACTIVE");
            webDriver.findElementOrNull(By.id("dunning_stop_c")).click();
            Thread.sleep(1000);
            duningStopCheckbox().click();
            Thread.sleep(2000);
        } else {
            duningStopCheckbox().click();
            Thread.sleep(2000);
        }
    }

    public WebElement saveButtonForFinanceAndLegalSection() throws InterruptedException {
        Thread.sleep(2000);
        return webDriver.findElementOrNull(By.xpath("//*[contains(text(),' Finance & legal ')]/preceding-sibling::*[1]"));
    }

    public void clickOnSaveButtonForFinanceAndLegalSection() throws InterruptedException {
        Thread.sleep(1000);
        saveButtonForFinanceAndLegalSection().click();
        Thread.sleep(2000);

    }

    public boolean checkIfDunningStopCheckboxIsChecked() throws InterruptedException {
        Thread.sleep(5000);
        String classValue = duningStopCheckbox().getAttribute("class");
        if (classValue.contains("not-empty")) {
            return true;
        } else {
            return false;
        }
    }

    public void putDuningStopBackToOff() throws InterruptedException {
        Thread.sleep(2000);
        clickOnplusIcon();
        clickOnServiceDropdownMenu();
        clickOnAccountChangesDropdownSubMenu();
        clickOnUpdateAccountDetailsOption();
        Thread.sleep(7000);
        webDriver.findElementOrNull(By.id("dunning_stop_c")).click();
        Thread.sleep(3000);
        clickOnSaveButtonForFinanceAndLegalSection();

    }

    public WebElement channelDropdownMenu() {
        return webDriver.findElementOrNull(By.id("cases-interaction-channel-c-field"));

    }

    public void clickOnChannelDropdownMenu() throws InterruptedException {
        Thread.sleep(1000);
        channelDropdownMenu();
    }

    public WebElement phoneDropdownOption() {
        return webDriver.findElementOrNull(By.xpath("//option[@label='Phone']"));
    }

    public void clickOnPhoneDropdownOption() throws InterruptedException {
        Thread.sleep(500);
        phoneDropdownOption().click();
    }

    public WebElement casePriorityField() {
        return webDriver.findElementOrNull(By.id("cases-priority-field"));
    }

    public void clickOnCasePriorityField() throws InterruptedException {
        Thread.sleep(1000);
        casePriorityField().click();
    }

    public WebElement highDropdownOption() {
        return webDriver.findElementOrNull(By.xpath("//option[@label='High']"));
    }

    public void clickOnHighDropdownOption() {
        highDropdownOption().click();
    }

    public WebElement solutionField() {
        return webDriver.findElementOrNull(By.id("cases-resolution-field"));
    }

    public void clickOnSolutionField() throws InterruptedException {
        Thread.sleep(1000);
        solutionField().click();

    }

    public WebElement describeTheSolutionDropdown() {
        return webDriver.findElementOrNull(By.xpath("//span[contains(text(),'DESCRIBE THE SOLUTION')]"));
    }

    public void clickOnDescribeTheSolutionDropdown() throws InterruptedException {
        Thread.sleep(300);
        describeTheSolutionDropdown().click();
    }

    public void setAllForNewCaseForCustomer(String descriptiontext, String solutionText) throws InterruptedException {
        Thread.sleep(2000);
        clickOnSubjectDropdownMenu();
        Thread.sleep(500);
        clickOnSettlementInvoiceDropdownOption();
        clickOnChannelDropdownMenu();
        Thread.sleep(500);
        clickOnPhoneDropdownOption();
        Thread.sleep(500);
        clickOnCasePriorityField();
        Thread.sleep(500);
        clickOnHighDropdownOption();
        Thread.sleep(500);
        clickOnDescriptionField();
        Thread.sleep(500);
        descriptionField().sendKeys(descriptiontext);
        Thread.sleep(1000);
        clickOnSolutionField();
        Thread.sleep(500);
        solutionField().sendKeys(solutionText);
        Thread.sleep(1000);
        clickOnDescribeTheSolutionDropdown();
        Thread.sleep(500);
        clickOnSaveButton();

    }

    public WebElement complaintField() {
        return webDriver.findElementOrNull(By.id("description-field"));
    }

    public String getComplaintText() {
        return webDriver.findElementOrNull(By.id("description-field")).getText();
    }

    public WebElement firstCaseInTheList() {
        return webDriver.findElementOrNull(By.xpath("(//h5)[1]"));
    }

    public void clickOnFirstCaseInTheList() throws InterruptedException {
        Thread.sleep(4000);
        firstCaseInTheList().click();
    }

    public WebElement priorityField() {
        return webDriver.findElementOrNull(By.id("priority-field"));
    }

    public boolean checkIfPriorityIsHigh() {
        if (priorityField().getText().equalsIgnoreCase("High")) {
            return true;
        }

        else {
            return false;
        }
    }

    public String getSolutionFieldText() {
        return webDriver.findElementOrNull(By.id("resolution-field")).getText();

    }

    public void clickOnMarketTransactionLink() throws InterruptedException {
        Thread.sleep(2000);
        webDriver.findElementOrNull(By.id("market-transactions-dashboard-link")).click();
    }

    public void clickOnFirstTransaction(String contractID) throws InterruptedException {
        Thread.sleep(10000);
        webDriver.findElementOrNull(By.xpath("(//h6[contains(text(),'"+contractID+"')]/preceding-sibling::*[1])[1]"))
            .click();
    }

    public void refreshMarketTransactions() throws InterruptedException {
        Thread.sleep(10000);
        webDriver.findElementOrNull(By.xpath("//span[contains(text(),'REFRESH MARKET TRANSACTIONS')]")).click();
        Thread.sleep(4000);
    }
}
