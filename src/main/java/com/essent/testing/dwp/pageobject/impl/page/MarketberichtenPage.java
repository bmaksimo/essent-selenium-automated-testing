package com.essent.testing.dwp.pageobject.impl.page;

import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MarketberichtenPage  extends Component {

    public MarketberichtenPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }
    public WebElement listActionsElemet(String element) throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.name(element));
    }

    public void takenOver(String taken, String signed) throws InterruptedException {
        String line1 = seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell[@icon='null']//span)[1]")).getText();
        String line2 = seleniumDriver.findElementWhenVisible(By.xpath("(//list-simple-two-liner-cell[@icon='null']//span)[2]")).getText();
        Assert.assertEquals(taken, line1);
        Assert.assertEquals(signed, line2);
    }
}

