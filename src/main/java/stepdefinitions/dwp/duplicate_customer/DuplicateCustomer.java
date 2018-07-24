package stepdefinitions.dwp.duplicate_customer;

import cucumber.api.java.en.And;
import stepdefinitions.dwp.NavigationElements;

public class DuplicateCustomer extends NavigationElements {
    @And("Search input is \"([^\"]*)\"$")
    public void input(String name) throws Throwable {
        super.searchForCustomer(name.toLowerCase());
    }

    @And("^([^\"]*) Change$")
    public void confirmChange(String action) {
        clickConfirm(action);
    }
}
