package com.essent.testing.dwp.pageobject.impl.service_contracting;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContractenPage extends Component {

    public ContractenPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public ContractenPage(WebElement element, SeleniumDriver seleniumDriver) {
        super(element, seleniumDriver);
    }

    public String findActiveContract(String input) {
        int counter = 2;
        final String eanCode;
        String action = findElementWhenVisible(By.xpath("(//h6)[2]")).getText();
        System.out.println("frist action " + action);
        while (!action.equalsIgnoreCase(input)){
            counter = counter + 2;
            action = findElementWhenVisible(By.xpath("(//h6)[" + counter + "]")).getText();
            System.out.println("other action " + action);
        }
        eanCode = findElementWhenVisible(By.xpath("(//h5)[" + (counter - 1) +"]")).getText();
        System.out.println("ean code " + eanCode);

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

    public void fieldDropDownLabel(String label, String input) {
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

    public void confirmTaskStatus(String input) {
        waitForRequestsToFinish();
        Assert.assertTrue(findElementWhenVisible(By.xpath("//tbody[@id='rows']/tr[1]/td[2]/list-link-bold-top-two-liner-cell[@icon='null']//h6[.='" + input + "']")).isDisplayed());
    }
}
