package stepdefinitions.quote.api.model;

public class QuoteDetails {

    private String recordId;
    private String quoteId;
    private String quoteNumber;
    private String accountNumber;
    private String accountId;
    private String ean;
    public String getRecordId() {
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    public String getQuoteId() {
        return quoteId;
    }
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }
    public String getQuoteNumber() {
        return quoteNumber;
    }
    public void setQuoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }
    public String getEan(){
        return ean;
    }

}
