package stepdefinitions.dwp.tables;

public enum PaymentMethod {
    DirectDebit("Direct Debit"),
    BankTransfer("Bank Transfer");
    private String label;

    private PaymentMethod(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
