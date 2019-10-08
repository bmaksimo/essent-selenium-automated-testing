package com.essent.testing.dwp.pageobject.table;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/*
    Usage example:
       List<Filter> filters = Arrays.asList(new Filter("COMMUNICATIETYPE", "Legal"));
       List<WebElement> row = new TableFilter()
            .getTable("CommunicationPreferencesOnAccount")
            .findBy(filters)
            .get();

    The returning value is the row, which is a List<WebElement>, each item on the list is a cell of the row.
    Initially we'll support ONE filter, next step is to support more than one, that's why the method signature has a list of filters
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

    public TableFilter findBy(List<Filter> filters) throws Exception {
        for (WebElement row : this.rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (isExpectedColumnValue(filters, cells)) {
                selectedRow = cells;
                return this;
            }
        }
        throw new Exception("Filter didn't match any rows on the table");
    }

    public List<WebElement> get() {
        return this.selectedRow;
    }

    private boolean isExpectedColumnValue(List<Filter> filters, List<WebElement> cells) {
        try {
            Filter filter = filters.get(0);
            int columnIndex = headers.indexOf(filter.getColumnName());
            return cells.get(columnIndex).getText().equals(filter.getColumnValue());
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
