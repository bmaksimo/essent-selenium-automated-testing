package com.essent.testing.odoo.pageobject.impl.modal.coda;

import static com.essent.automation.autocrat.Action.UPLOAD;
import static com.essent.testing.dwp.autocrat.timing.quote.TimeoutValues.UPLOAD_FILE;

import com.essent.automation.autocrat.Model;
import com.essent.testing.odoo.pageobject.impl.Component;
import com.essent.testing.odoo.pageobject.modal.CodaImportDialog;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class CodaImportDialogImpl extends Component implements CodaImportDialog {

  private static final By SELECTOR = By.cssSelector(".modal-content");
  private static final String FILE_SELECTOR =
      "form[action='/web/binary/upload'] > input[name='ufile']";
  private static final String IMPORT_BUTTON_SELECTOR_TEMPLATE =
      "//button[span[normalize-space(text())='${text}']]";
  private static final By RESULTS_NOTE_SELECTOR = By.cssSelector("textarea[name='note']");
  private String uploadFile;
  private String importButton;

  public CodaImportDialogImpl() {
    super(SELECTOR);
  }

  @Override
  public String getTitle() {
    WebElement titleElement = element.findElement(By.cssSelector("h3"));
    return titleElement.getText();
  }

  @Override
  public void setUploadFile(String path) {
    this.uploadFile = path;
  }

  @Override
  public void setImportButton(String text) {
    this.importButton = text;
  }

  @Override
  public boolean fillInFormData() {
    Model.Execution execution = createExecution();
    Model.Element element = createElement("SELECTOR", FILE_SELECTOR);
    execution
        .element("odoo.coda.upload.file.input", element)
        .flow()
        .step(
            createStep(UPLOAD)
                .element("odoo.coda.upload.file.input")
                .requireDisplayed(false)
                .value(uploadFile)
                .sleepInMillis(UPLOAD_FILE.getSleepInMillis()));
    return execute(execution);
  }

  @Override
  public void confirm() {
    String query = createQuery(IMPORT_BUTTON_SELECTOR_TEMPLATE, "text", importButton);
    FluentWait<WebDriver> waiter =
        new FluentWait<>(seleniumDriver.getDriver()).withTimeout(Duration.ofSeconds(10));
    WebElement codaElement =
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.xpath(query)));
    waiter.until(ExpectedConditions.elementToBeClickable(codaElement));
    codaElement.click();
  }

  public String getImportReport() {
    WebElement reportElement = findElementWhenVisible(RESULTS_NOTE_SELECTOR);
    return reportElement.getAttribute("value");
  }
}
