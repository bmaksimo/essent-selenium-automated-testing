package stepdefinitions.odoo.navigation.search;

import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.selenium.SeleniumDriver;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class AdvancedSearchComponent extends Component {

    private static final String ADVANCED_SEARCH_DRAWER_TOGGLE_SELECTOR = "//div[@class='oe_searchview_unfold_drawer']";
    private static final String ADVANCED_SEARCH_VIEW_SELECTOR = "//div[@class='oe_searchview_advanced']/h4";
    private static final String PROPERTIES_LIST_SELECTOR = "//select[@class='searchview_extended_prop_field']";
    private static final String OPERATORS_LIST_SELECTOR = "//select[@class='searchview_extended_prop_op']";
    private static final String SEARCH_TERM_INPUT_SELECTOR = "//span[@class='searchview_extended_prop_value']/input";
    private static final String APPLY_FILTER_BUTTON_SELECTOR = "//button[@class='oe_apply']";

    public AdvancedSearchComponent(SeleniumDriver seleniumDriver) {
        super(seleniumDriver);
    }

    public void runAdvancedSearch(AdvancedSearch advancedSearch) {
        fillInAdvancedSearchForm(advancedSearch);
        getApplyButton().click();
    }

    private void fillInAdvancedSearchForm(AdvancedSearch advancedSearch) {
        awaitOdooRequestToFinish(10);
        navigateToAdvancedSearchView();

        new Select(getPropertiesList()).selectByVisibleText(advancedSearch.getField());
        new Select(getOperatorsList()).selectByVisibleText(advancedSearch.getAdvancedSearchOperator().getOperator());

        if (StringUtils.isNotBlank(advancedSearch.getSearchTerm()))
            getSearchTermInput().sendKeys(advancedSearch.getSearchTerm());
    }

    private void navigateToAdvancedSearchView() {
        getDrawerToggle().click();
        getAdvancedSearchView().click();
    }

    private WebElement getDrawerToggle() {
        return seleniumDriver.findElement(By.xpath(ADVANCED_SEARCH_DRAWER_TOGGLE_SELECTOR));
    }

    private WebElement getAdvancedSearchView() {
        return seleniumDriver.findElement(By.xpath(ADVANCED_SEARCH_VIEW_SELECTOR));
    }

    private WebElement getPropertiesList() {
        return seleniumDriver.findElement(By.xpath(PROPERTIES_LIST_SELECTOR));
    }

    private WebElement getOperatorsList() {
        return seleniumDriver.findElement(By.xpath(OPERATORS_LIST_SELECTOR));
    }

    private WebElement getSearchTermInput() {
        return seleniumDriver.findElement(By.xpath(SEARCH_TERM_INPUT_SELECTOR));
    }

    private WebElement getApplyButton() {
        return seleniumDriver.findElement(By.xpath(APPLY_FILTER_BUTTON_SELECTOR));
    }
}
