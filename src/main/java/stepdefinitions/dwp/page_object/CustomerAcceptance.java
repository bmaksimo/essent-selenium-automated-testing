package stepdefinitions.dwp.page_object;


import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import cucumber.runtime.CucumberException;


public class CustomerAcceptance extends Component {

    public CustomerAcceptance(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void customerStatus(String status) {
        status = seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"accounts-aos-quotes-ca-status-c-field\"]")).getText();
        if (status == "Geaccepteerd") {
            Assert.assertEquals("Geaccepteerd", status);
        }
        else if (status == "Waarborg"){
            Assert.assertEquals("Waarborg", status);
        }
        else {  throw new CucumberException("Status not found by input " + status); }
    }


}
