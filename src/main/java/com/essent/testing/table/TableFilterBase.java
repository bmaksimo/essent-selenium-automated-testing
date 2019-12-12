package com.essent.testing.table;

import com.essent.automation.util.Sleeper;
import com.essent.testing.selenium.SeleniumDriver;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
            .getRow();

    The returning value is the row, which is a List<WebElement>, each item on the list is a cell of the row.
    Initially we'll support ONE filter, next step is to support more than one, that's why the method signature has a list of filters
 */
public class TableFilterBase {

    private final Logger logger = Logger.getLogger(TableFilterBase.class);
    private SeleniumDriver seleniumDriver;
    private List<String> headers;
    private List<WebElement> rows;
    private List<WebElement> selectedRow;
    private String tableName;
    private String tablePath;
    private String contextParameters;
    private String tablePathBase;
    private String tableRowsBase;

    public TableFilterBase(String contextParameters, SeleniumDriver seleniumDriver, String tablePathBase, String tableRowsBase) {
        this.seleniumDriver = seleniumDriver;
        this.contextParameters = contextParameters;
        this.tablePathBase = tablePathBase;
        this.tableRowsBase = tableRowsBase;
    }

    public TableFilterBase getTable(String tableName) {
        Sleeper.sleepTightInSeconds(5);
        this.tableName = tableName;
        this.tablePath = buildTablePath(tablePathBase, this.tableName);
        this.headers = getHeaders();
        this.rows = seleniumDriver.findElements(By.xpath(this.tablePath + this.tableRowsBase));
        return this;
    }

    public TableFilterBase findBy(List<Filter> filters) throws Exception {
        List<WebElement> currentRowCells = new ArrayList<WebElement>();
        logger.info(this.contextParameters + " rows: " + this.rows.size());
        for (WebElement row : this.rows) {
            currentRowCells = row.findElements(By.tagName("td"));
            if (isExpectedColumnValue(filters, currentRowCells)) {
                this.selectedRow = currentRowCells;
                return this;
            }
        }

        logErrors(filters);
        throw new Exception("Filter didn't match any rows on the table");
    }

    public List<WebElement> getRow() {
        return this.selectedRow;
    }

    public String getTextValueFromColumn(String column) {
        int columnIndex = headers.indexOf(column.toUpperCase());
        return StringUtils.isNotBlank(this.getRow().get(columnIndex).getText()) ?
            this.getRow().get(columnIndex).getText() : StringUtils.EMPTY;
    }

    private void logErrors(List<Filter> filters) {
        String currentContextParameters = StringUtils.isBlank(this.contextParameters) ? "" : this.contextParameters;
        logger.error(currentContextParameters + " - Table \"" + this.tableName + "\" did not contain row(s) with provided filter: " + getProvidedFilters(filters));
        logger.error(currentContextParameters + " - Table \"" + this.tableName + "\" contains the following rows: \n" + getActualRows());
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
                int columnIndex = headers.indexOf(filter.getColumnName().toUpperCase());
                logger.info("[COLUMN INDEX: " + columnIndex + "]");
                if (!cells.get(columnIndex).getText().toUpperCase().contains(filter.getColumnValue().toUpperCase()))
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected List<String> getHeaders() {
        return seleniumDriver
            .findElementsWithDefaultWaiting(By.xpath(this.tablePath + "//thead/tr/th"))
            .stream()
            .map(WebElement::getText)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }

    private String buildTablePath(String basePath, String tableName) {
        return basePath.replace("${tableName}", tableName);
    }
}
