package stepdefinitions.dwp.tables;

// TODO
// This enum should be removed.
// It distracts the developers from basic rule: DWP should support at least 2 languages.
// Any workaround, circumventing this basic rule, is prohibited
public enum PaymentMethod {
  DirectDebit("Direct Debit"),
  BankTransfer("Bank Transfer"),
  DOM("Domiciliëring"),
  Overschrijving("Overschrijving");
  private String label;

  PaymentMethod(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
