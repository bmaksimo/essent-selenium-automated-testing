package stepdefinitions.dwp.page_object;


import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.dwp.pageobject.impl.page.BaseObject;
import com.essent.testing.selenium.SeleniumDriver;
import com.essent.testing.dwp.scenario.DwpScenario;

import cucumber.api.java.en.Then;

import org.junit.Assert;
import org.openqa.selenium.By;


public class CustomerAcceptance extends Component {

    public CustomerAcceptance(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void customerStatus(String status) {
        status = seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"accounts-aos-quotes-ca-status-c-field\"]")).getText();
        Assert.assertEquals("Geaccepteerd", status);
    }


}
