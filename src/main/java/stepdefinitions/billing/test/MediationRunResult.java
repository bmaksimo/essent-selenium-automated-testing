package stepdefinitions.billing.test;

public class MediationRunResult {
    private String message;
    private Boolean result;

    public MediationRunResult(String message, Boolean result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
