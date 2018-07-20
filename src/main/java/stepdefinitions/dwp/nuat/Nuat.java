package stepdefinitions.dwp.nuat;

import cucumber.api.java.en.And;
import stepdefinitions.dwp.NavigationElements;

public class Nuat extends NavigationElements {
    @And("^Tap on element ([^\"]*)")
    public void tapOnElementValueIs(String element) {
        super.clickOnElement(element);
    }
}
