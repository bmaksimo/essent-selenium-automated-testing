package stepdefinitions.dwp.tables;

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
