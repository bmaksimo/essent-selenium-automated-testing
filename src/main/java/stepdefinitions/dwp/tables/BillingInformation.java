package stepdefinitions.dwp.tables;

public class BillingInformation {
    private String paymentMethod;
    private String eban;
    private String bic;

    public BillingInformation(String paymentMethod, String eban, String bic) {
        this.paymentMethod = paymentMethod;
        this.eban = eban;
        this.bic = bic;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getEban() {
        return eban;
    }

    public void setEban(String eban) {
        this.eban = eban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}
