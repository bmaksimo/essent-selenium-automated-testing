package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ContractenPage extends Component {

    public ContractenPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public ContractenPage(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
    }

    public String findActiveContract() {
        waitForRequestsToFinish();
        int counter = 1;
        final String eanCode;
        String cActive = "actief";
        String active = findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[" + counter + "]/td[4]/list-link-bold-top-two-liner-cell[@icon='null']//h6")).getText();
        while (!active.equalsIgnoreCase(cActive)) {
            counter = counter + 2;
            active = findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[" + counter + "]/td[4]/list-link-bold-top-two-liner-cell[@icon='null']//h6")).getText();
        }
        eanCode = findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[" + counter +"]/td[3]/list-link-bold-top-two-liner-cell[@icon='null']//a/h5")).getText();

        return eanCode;
    }

    public void searchForEanCode(String eanCode) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("search-input")).clear();
        waitForRequestsToFinish();
        findElementWhenVisible(By.id("search-input")).sendKeys(eanCode);
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//input[@value='Search']")).click();
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//div[@class='multi-select__results']//ul[2]")).click();
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//section[@class='view__modal']//a[@href='']")).click();
    }

    public void fielInputModule(String label, String input) {
        waitForRequestsToFinish();
        findElementWhenVisible(By.xpath("//select[@id='dwp-mig-" + label.toLowerCase() + "-c-field']/option[@label='" + input + "']")).click();
    }

    public void turnOnCheckBox(String label) {
        if (label.equalsIgnoreCase("Testing")) {
            findElementWhenVisible(By.id("dwp|toggle_testing")).click();
        } else if (label.equalsIgnoreCase("Market mock")) {
            findElementWhenVisible(By.id("aos_products_quotes|market_mock_c")).click();
        }
    }

    public void confirmNonResidential() {
        waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[2]/list-link-bold-top-two-liner-cell[@icon='null']//a/h5[.='INITIATE STOP ACCESS']")).isDisplayed());
        Assert.assertTrue(findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[2]/list-link-bold-top-two-liner-cell[@icon='null']//h6[.='Non-Residential End-of-Contract']")).isDisplayed());
    }
}
