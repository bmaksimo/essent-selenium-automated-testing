package stepdefinitions.dwp.tables;

public enum SalesChannel {
    Inbound("Inbound"),
    Outbound("Outbound");
    private String label;

    SalesChannel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
