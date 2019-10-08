package com.essent.testing.dwp.pageobject.table;

import com.essent.testing.dwp.pageobject.impl.Component;
import cucumber.runtime.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/*
    Usage example:
       List<WebElement> row = new TableFilter()
            .getTable("CommunicationPreferencesOnAccount")
            .filterBy("COMMUNICATIETYPE", "Legal")
            .get();

    The returning value is the row, which is a List<WebElement>, each item on the list is a cell of the row.
 */
public class TableFilter extends Component {

    private List<String> headers;
    private List<WebElement> rows;
    private List<WebElement> selectedRow;

    public TableFilter getTable(String tableName) {
        seleniumDriver.waitForRequestsToFinish();
        this.headers = getHeaders(tableName);
        this.rows = seleniumDriver.findElements(By.xpath("//list[@list-key='" + tableName + "']//tbody[@id='rows']/tr"));
        return this;
    }

    public TableFilter filterBy(String columnName, String columnValue) {
        for (WebElement row : this.rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (isExpectedColumnValue(columnName, columnValue, cells)) {
                selectedRow = cells;
                return this;
            }
        }
        throw new CucumberException(columnValue + " was not found on column " + columnName + " on any row of the table");
    }

    public List<WebElement> get() {
        return this.selectedRow;
    }

    private boolean isExpectedColumnValue(String columnName, String columnValue, List<WebElement> cells) {
        try {
            int columnIndex = headers.indexOf(columnName);
            return cells.get(columnIndex).getText().equals(columnValue);
        } catch (Exception e) {
            return false;
        }
    }

    private List<String> getHeaders(String table) {
        return seleniumDriver
            .findElements(By.xpath("//list[@list-key='" + table + "']//thead/tr/th"))
            .stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }
}
