package stepdefinitions.dwp.page_object;


import com.essent.automation.util.Sleeper;
import com.essent.testing.dwp.pageobject.impl.Component;
import com.essent.testing.selenium.webdriver.dwp.SeleniumDriverDwpImpl;
import org.junit.Assert;
import org.openqa.selenium.By;
import cucumber.runtime.CucumberException;


public class CustomerAcceptance extends Component {

    public CustomerAcceptance(SeleniumDriverDwpImpl seleniumDriver) {
        super(seleniumDriver);
    }

    public void customerStatus(String status) {
       Sleeper.sleepTightInSeconds(3);
       String actualStatus = seleniumDriver.findElementWhenVisible(By.xpath("//*[@id=\"accounts-aos-quotes-ca-status-c-field\"]")).getText();

        if (actualStatus.equals("Geaccepteerd")) {
            Assert.assertEquals(actualStatus, status);
        }
        else if (actualStatus.equals("Waarborg")){
            Assert.assertEquals(actualStatus, status);
        }
        else {  throw new CucumberException("Status not found by input " + status); }
    }


}
