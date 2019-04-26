package com.essent.testing.odoo.pageobject.impl.elements;

import com.billinghouse.exception.ExtendedCucumberException;
import com.essent.testing.odoo.pageobject.elements.ListView;
import com.essent.testing.odoo.pageobject.impl.Component;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static stepdefinitions.odoo.navigation.menu.OdooMenuList.getKey;

public class DefaultListView extends Component implements ListView {

    private static final String TABLE_CELL_SELECTOR_TEMPLATE = "//table[@class='oe_list_content'][1]//tbody//tr[${rowIndex}]//td[@data-field='${key}'][1]";

    @Override
    public void checkCellAt(String columnName, String rowIndex, String value) {
        logger().info("STEP: checkValueAt");
        Map<String, String> valuesMapper = new HashMap<>();
        valuesMapper.put("rowIndex", rowIndex);
        valuesMapper.put("key", getKey(columnName));
        String query = createQuery(TABLE_CELL_SELECTOR_TEMPLATE, valuesMapper);
        By xpathCheck = By.xpath(query);
        awaitOdooRequestToFinish(5);
        WebElement elementWhenVisible = seleniumDriver.findElementWhenVisible(xpathCheck);
        if (null == elementWhenVisible) {
            logger().error(" - TABLE_NOT_VISIBLE");
            throw new ExtendedCucumberException("Table is not visible");
        }
        String cellText = elementWhenVisible.getText();
        if (!value.equals(cellText)) {
            logger().error(" - DIFFERENT_VALUE: expected" + value + " actual: " + cellText);
            throw new ExtendedCucumberException(String.format("The value %s at column %s row %s does not match the expected one (%s)", cellText, columnName, rowIndex, value));
        }
    }

    @Override
    public void clickCellAt(String columnName, String rowIndex) {
        logger().info("STEP: clickCellAt");
        Map<String, String> valuesMapper = new HashMap<>();
        valuesMapper.put("rowIndex", rowIndex);
        valuesMapper.put("key", getKey(columnName));
        String query = createQuery(TABLE_CELL_SELECTOR_TEMPLATE, valuesMapper);
        By xpathCheck = By.xpath(query);
        awaitOdooRequestToFinish(5);
        List<WebElement> rows = seleniumDriver.findElements(xpathCheck);
        awaitOdooRequestToFinish(5);
        if (CollectionUtils.isEmpty(rows)) {
            logger().error(" - CELL_NOT_FOUND: column name" + columnName + " rowIndex: " + rowIndex);
            throw new ExtendedCucumberException(String.format("Cell at column %s, row %s was not found", columnName, rowIndex));
        } else {
            WebElement webElement = rows.get(rows.size() - 1);
            logger().info(" - CELL_TEXT: " + webElement.getText());
            webElement.click();
            logger().info(" - CELL_ACTION: click()");
        }
    }

    @Override
    public void clickValueAt(String columnName, String value) {
        logger().info("STEP: clickValueAt");
        awaitOdooRequestToFinish(30);
        List<WebElement> rows = extractTable();
        WebElement currentRow;
        for (int i = 1; i <= rows.size(); i++) {
            currentRow = seleniumDriver.findElementWhenVisible(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr[" + i + "]//td[@data-field='"
                + getKey(columnName)
                + "'][1]"));

            if (currentRowEquals(currentRow, value)) {
                logger().info(" - CELL_TEXT: " + currentRow.getText());
                currentRow.click();
                logger().info(" - CELL_ACTION: click()");
                awaitOdooRequestToFinish(10);
                return;
            }
        }
        logger().error(" - CELL_NOT_FOUND: column name" + columnName + " cell text: " + value);
        throw new ExtendedCucumberException(String.format("Cell at column %s having value %s was not found", columnName, value));
    }

    private boolean currentRowEquals(WebElement currentRow, String value) {
        return currentRow != null && currentRow.isDisplayed() && value.equalsIgnoreCase(currentRow.getText());
    }

    @Override
    public void checkValueAt(String columnName, String value) {
        awaitOdooRequestToFinish(10);
        logger().info("STEP: clickValueAt");
        List<WebElement> rows = extractTable();
        WebElement currentRow;
        for (int i = 1; i <= rows.size(); i++) {
            currentRow = seleniumDriver.findElementWhenPresent(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr[" + i + "]//td[@data-field='"
                + getKey(columnName)
                + "'][1]"),
                Duration.ofSeconds(30),
                Duration.ofSeconds(5));
            if (currentRow != null && value.equalsIgnoreCase(currentRow.getText())) {
                logger().info(" - CELL_TEXT: " + currentRow.getText());
                return;
            }
        }
        logger().error(" - CELL_NOT_FOUND: column name" + columnName + " cell text: " + value);
        throw new ExtendedCucumberException(String.format("Cell at column %s having value %s was not found", columnName, value));
    }

    private List<WebElement> extractTable() {
        List<WebElement> rows = seleniumDriver.findElements(By.xpath("//table[@class='oe_list_content'][1]//tbody//tr"),
            Duration.ofSeconds(30),
            Duration.ofSeconds(5));
        if(rows.isEmpty()) {
            throw new ExtendedCucumberException("Table is empty");
        }
        return rows;
    }
}
