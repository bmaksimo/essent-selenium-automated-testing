package stepdefinitions.quote.api.model;

public class QuoteDetails {

  private String recordId;
  private String quoteId;
  private String quoteNumber;
  private String accountNumber;
  private String accountId;
  private String ean;
  private String dateOfBirth;
  private String accountName;
  private String firstName;
  private String lastName;
  private String iBan;
  private String companyNumber;

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

  public String getEan() {
    return ean;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getiBan() {
    return iBan;
  }

  public void setiBan(String iBan) {
    this.iBan = iBan;
  }

  public String getCompanyNumber() {
    return companyNumber;
  }

  public void setCompanyNumber(String companyNumber) {
    this.companyNumber = companyNumber;
  }

  @Override
  public String toString() {
    return "QuoteDetails{"
        + "recordId='"
        + recordId
        + '\''
        + ", quoteId='"
        + quoteId
        + '\''
        + ", quoteNumber='"
        + quoteNumber
        + '\''
        + ", accountNumber='"
        + accountNumber
        + '\''
        + ", accountId='"
        + accountId
        + '\''
        + ", ean='"
        + ean
        + '\''
        + ", dateOfBirth='"
        + dateOfBirth
        + '\''
        + ", accountName='"
        + accountName
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", iBan='"
        + iBan
        + '\''
        + ", companyNumber='"
        + companyNumber
        + '\''
        + '}';
  }
}
