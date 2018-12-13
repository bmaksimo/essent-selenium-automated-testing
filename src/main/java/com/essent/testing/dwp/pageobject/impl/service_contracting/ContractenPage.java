package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class ContractenPage extends Component {
    private static  String payDate;

    public ContractenPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public String findActiveContract(String input) throws InterruptedException {
        waitForRequestsToFinish();
        int counter = 2;
        String eanCode;
        String action = findElementWhenVisible(By.xpath("(//h6)[" + counter + "]")).getText();
        while (!action.equalsIgnoreCase(input)) {
            counter = counter + 2;
            action = findElementWhenVisible(By.xpath("(//h6)[" + counter + "]")).getText();
        }
        counter--;
        eanCode = findElementWhenVisible(By.xpath("(//h5)[" + counter + "]")).getText();

        return eanCode;
    }

    public void searchForEanCode(String eanCode) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("search-input")).clear();
        findElementWhenVisible(By.id("search-input")).sendKeys(eanCode);
        findElementWhenVisible(By.xpath("//input[@value='Search']")).click();
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//div[@class='multi-select__results']//ul[2]")).click();
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//section[@class='view__modal']//a[@href='']")).click();
    }

    public void fieldDropDownLabel(String label, String input) {
        waitForRequestsToFinish();
        seleniumDriver.findElementWhenVisible(By.xpath("//validation-wrapper[@label='" + label + "']/div/div/ng-form/div/select-form-element/div/select/option[@label='" + input + "']")).click();
    }

    public void turnOnTestingAndMarketMock(String label) {
        if (label.equalsIgnoreCase("Testing")) {
            findElementWhenVisible(By.id("dwp|toggle_testing")).click();
        } else if (label.equalsIgnoreCase("Market mock")) {
            findElementWhenVisible(By.id("aos_products_quotes|market_mock_c")).click();
        }
    }

    public void confirmTaskStatus(String input) {
        waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("(//h6)[.='" + input + "']")).isDisplayed());
    }

    public void searchForTaskId(String taskId) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//input[@type='search']")).clear();
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//input[@type='search']")), taskId);
        findElementWhenVisible(By.xpath("//input[@type='search']")).sendKeys(Keys.ENTER);
    }

    public void findRejectionReason(String input) {
        waitForRequestsToFinish();
        Assert.assertTrue(seleniumDriver.findElementWhenVisible(By.xpath("(//span[.='" + input + "'])[1]")).isDisplayed());
    }

    public void inputText(String text) {
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.xpath("//text-angular[@id='description-field']/div[2]/div[.=' ']")), text);
    }

    public void sendEmailToCustomer(String test) {
        waitForRequestsToFinish();
        BaseObject baseObject = new BaseObject(seleniumDriver);
        baseObject.clickOnPlus();
        waitForRequestsToFinish();
        seleniumDriver.findElementWhenVisible(By.xpath("//list-row-action[@label='"+test+"']/a")).click();
    }

    public void openListOption(String option) {
        seleniumDriver.waitAndClick(findElementWhenVisible(By.xpath("//span[.='" + option + "']")));
    }

    public void checkPayDate() {
        waitForRequestsToFinish();
        final String newPayDate = findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell[@icon='null']//span)[6]")).getText();
        Assert.assertFalse("Date was not changed. Old date is : " + payDate + ", and new date is same : " + newPayDate, newPayDate.equalsIgnoreCase(payDate));
    }

    public void findIssuedAndPayDelay(String type, String option) {
        /* I must use tr and td html elements to locate correct list element*/
        int counter = 1;
        String payType = findElementWhenVisible(By.xpath("//*[@id='rows']/tr[1]/td[8]//span[1]")).getText();
        while(!payType.equalsIgnoreCase(type)){
            counter = counter + 2;
            payType = findElementWhenVisible(By.xpath("//*[@id='rows']/tr[" + counter + "]/td[8]//span[1]")).getText();
        }
        payDate = findElementWhenVisible(By.xpath("//*[@id='rows']/tr[" + counter + "]/td[7]//span[2]")).getText();
        findElementWhenVisible(By.xpath("(//*[@id='rows']/tr[" + counter + "]/td[10]/list-plus-cell//a)[1]")).click();
        findElementWhenVisible(By.xpath("//list-row-action[@label='" + option + "']/a")).click();
    }

    public void findIban(String iban) {
        waitForRequestsToFinish();
        Assert.assertTrue(seleniumDriver.findElementWhenVisible(By.xpath("//span[.='" + iban + "']")).isDisplayed());
    }

}
