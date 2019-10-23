package com.essent.testing.dwp.pageobject.table;

import com.essent.testing.dwp.pageobject.impl.Component;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
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

    private static final String REPLACEMENT_KEY = "replacement_key";
    private static final String TABLE_PATH_BASE = "//list//div//h2[text()='${" + REPLACEMENT_KEY + "}']/parent::div/parent::div";

    private List<String> headers;
    private List<WebElement> rows;
    private List<WebElement> selectedRow;
    private String tableName;
    private String tablePath;
    private String contextParameters;

    public TableFilter(String contextParameters) {
        this.contextParameters = contextParameters;
    }

    public TableFilter getTable(String tableName) {
        seleniumDriver.waitForRequestsToFinish();
        this.tableName = tableName;
        this.tablePath = createQuery(TABLE_PATH_BASE, REPLACEMENT_KEY, this.tableName);
        this.headers = getHeaders();
        this.rows = seleniumDriver.findElements(By.xpath(this.tablePath + "//tbody[@id='rows']/tr"));
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

        logErrors(filters);
        throw new Exception("Filter didn't match any rows on the table");
    }

    public List<WebElement> get() {
        return this.selectedRow;
    }

    private void logErrors(List<Filter> filters) {
        String currentContextParameters = StringUtils.isBlank(this.contextParameters) ? "" : this.contextParameters;
        logger().error(currentContextParameters + " - Table \"" + this.tableName + "\" did not contain row(s) with provided filter: " + getProvidedFilters(filters));
        logger().error(currentContextParameters + " - Table \"" + this.tableName + "\" contains the following rows: \n" + getActualRows());
    }


    private String getProvidedFilters(List<Filter> filters) {
        return filters.stream()
            .map(Filter::toString)
            .reduce("", (result, currentFilter) -> result + " " + currentFilter);
    }

    private String getActualRows() {
        return this.rows.stream()
            .map(we -> rowToString(we.findElements(By.tagName("td"))))
            .reduce("", (result, currentRow) -> result + " " + currentRow + "\n");
    }

    private String rowToString(List<WebElement> row) {
        return row.stream().map(WebElement::getText).reduce("| ", (result, cell) -> result + " | " + cell);
    }

    private boolean isExpectedColumnValue(List<Filter> filters, List<WebElement> cells) {
        try {
            for (Filter filter : filters) {
                int columnIndex = headers.indexOf(filter.getColumnName());
                if (!cells.get(columnIndex).getText().contains(filter.getColumnValue()))
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private List<String> getHeaders() {
        return seleniumDriver
            .findElements(By.xpath(this.tablePath + "//thead/tr/th"))
            .stream()
            .map(WebElement::getText)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }
}
