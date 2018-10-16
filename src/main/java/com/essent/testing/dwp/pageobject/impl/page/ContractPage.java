package com.essent.testing.dwp.pageobject.impl.page;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ContractPage extends Component {

    public ContractPage(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

//    public WebElement startData(){
//        return SeleniumDriver.fi
//    }

    public void saveButtton()throws InterruptedException {
        seleniumDriver.waitAndClick(seleniumDriver.findElementWhenVisible(By.id("confirm-button")));
    }

    public String getClientNumber()throws InterruptedException {
        return seleniumDriver.findElementWhenVisible(By.xpath("(//h4)[2]")).getText();
    }

    public void searchByClientNuiber(String nubmer)throws InterruptedException{
        seleniumDriver.waitAndSendKeys(seleniumDriver.findElementWhenVisible(By.id("search-input")),nubmer);
    }
}
