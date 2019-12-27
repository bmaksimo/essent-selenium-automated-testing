package stepdefinitions.quote.api.builders;

import stepdefinitions.quote.api.model.QuoteDetails;

public class QuoteDetailsBuilder {
  private QuoteDetails quoteDetails;

  public QuoteDetailsBuilder() {
    this.quoteDetails = new QuoteDetails();
  }

  public QuoteDetailsBuilder withBirthdate(String dateOfBirth) {
    this.quoteDetails.setDateOfBirth(dateOfBirth);
    return this;
  }

  public QuoteDetailsBuilder withFirstName(String firstName) {
    this.quoteDetails.setFirstName(firstName);
    return this;
  }

  public QuoteDetailsBuilder withLastName(String lastName) {
    this.quoteDetails.setLastName(lastName);
    return this;
  }

  public QuoteDetailsBuilder withIban(String iBan) {
    this.quoteDetails.setiBan(iBan);
    return this;
  }

  public QuoteDetailsBuilder withEan(String ean) {
    this.quoteDetails.setEan(ean);
    return this;
  }

  public QuoteDetailsBuilder withAccountName(String accountName) {
    this.quoteDetails.setAccountName(accountName);
    return this;
  }

  public QuoteDetailsBuilder withCompanyNumber(String companyNumber) {
    this.quoteDetails.setCompanyNumber(companyNumber);
    return this;
  }

  public QuoteDetailsBuilder withDateOfBirth(String dateOfBirth) {
    this.quoteDetails.setDateOfBirth(dateOfBirth);
    return this;
  }

  public QuoteDetailsBuilder withRecordId(String recordId) {
    this.quoteDetails.setRecordId(recordId);
    return this;
  }

  public QuoteDetailsBuilder withAccountNumber(String accountNumber) {
    this.quoteDetails.setAccountNumber(accountNumber);
    return this;
  }

  public QuoteDetailsBuilder withAccountId(String accountId) {
    this.quoteDetails.setAccountId(accountId);
    return this;
  }

  public QuoteDetailsBuilder withQuoteNumber(String quoteNumber) {
    this.quoteDetails.setQuoteNumber(quoteNumber);
    return this;
  }

  public QuoteDetailsBuilder withQuoteId(String quoteId) {
    this.quoteDetails.setQuoteId(quoteId);
    return this;
  }

  public QuoteDetails build() {
    return this.quoteDetails;
  }
}
