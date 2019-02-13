package stepdefinitions.quote.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Model {

    @JsonProperty("contact_type_c")
    private String contactType;
    @JsonProperty("accounts|aos_quotes|payment_details|payment_methods|payment_method")
    private String paymentMethod;
    @JsonProperty("accounts|aos_quotes|sales_channel_id")
    private String salesChannelId;
    @JsonProperty("birthdate")
    private String birthdate;
    @JsonProperty("dwp|alreadyContractedForDifferentClient")
    private boolean alreadyContractedForDifferent;
    @JsonProperty("current_user_id")
    private String currentUserId;
    @JsonProperty("accounts|aos_quotes|payment_details|addresses")
    private AccountsAddress accountAddresses;
    @JsonProperty("dwp|accounts|aos_quotes|payment_details|payment_methods|payment_method|matchedCondition")
    private String paymentMethodMatchedCondition;
    @JsonProperty("leads_contact_details(contact_details_phone_type='Mobile phone';contact_details_type='Phone')|id")
    private String leadsMobileContactDetailsId;
    @JsonProperty("dwp|package_properties")
    private String packageProperties;
    @JsonProperty("accounts|aos_quotes|digital_sign_agree")
    private String digitalSignAgree;
    @JsonProperty("accounts|aos_quotes|do_auto_communication_c")
    private String doAutoCommunication;
    @JsonProperty("id")
    private String id;
    @JsonProperty("dwp|discount_gen_conditons")
    private String discountGenConditions;
    @JsonProperty("recordTypeOfRecordId")
    private String recordTypeOfRecordId;
    @JsonProperty("accounts|aos_quotes|signed_contract_docguid_c")
    private List<String> signedContract = new ArrayList<>();
    @JsonProperty("dwp|alreadysigned")
    private String alreadySigned;
    @JsonProperty("com_prefs(type='GENERAL')|channel")
    private String generalChannel;
    @JsonProperty("dwp|accounts|aos_quotes|payment_details|com_prefs(type='LEGAL')|channel|matchedCondition")
    private String legalChannelMatchedCondition;
    @JsonProperty("current_primary_group_id")
    private String currentPrimaryGroupId;
    @JsonProperty("family_key")
    private String familyKey;
    @JsonProperty("baseModule")
    private String baseModule;
    @JsonProperty("accounts|aos_quotes|payment_details|paymentterms")
    private String paymentTerms;
    @JsonProperty("accounts|aos_quotes|sign_channel_c")
    private String signChannel;
    @JsonProperty("dwp|gender_c|matchedCondition")
    private String genderMatchedCondition;
    @JsonProperty("accounts|aos_quotes|sign_location_c")
    private String signLocation;
    @JsonProperty("gender_c")
    private String gender;
    @JsonProperty("accounts|aos_quotes|ca_status_c")
    private String caStatus;
    @JsonProperty("accounts|opportunities|opportunity_status_c")
    private String opportunityStatus;
    @JsonProperty("accounts|aos_quotes|aos_products_quotes|delivery_address_postalcode")
    private String deliveryAddressPostalCode;
    @JsonProperty("salutation")
    private String salutation;
    @JsonProperty("addresses_leads")
    private AccountsAddress address;
    @JsonProperty("accounts|aos_quotes|primary_group_id")
    private List<User> primaryGroupIds;
    @JsonProperty("dwp|informationAction")
    private String informationAction;
    @JsonProperty("accounts|aos_quotes|payment_details|com_prefs(type='MANDATE')|channel")
    private String mandateChannel;
    @JsonProperty("dwp|accounts|aos_quotes|signature_option_c|matchedCondition")
    private String signatureOptionMatchedCondition;
    @JsonProperty("language_c")
    private String language;
    @JsonProperty("accounts|aos_quotes|signature_option_c")
    private String signatureOption;
    @JsonProperty("leads_contact_details(contact_details_phone_type='Work phone';contact_details_type='Phone')|id")
    private String leadsPhoneContactDetailsId;
    @JsonProperty("dwp|recordtype")
    private String dwprecordType;
    @JsonProperty("accounts|aos_quotes|pricing_date_c")
    private String pricingDate;
    @JsonProperty("accounts|aos_quotes|payment_details|com_prefs(type='LEGAL')|channel")
    private String legalChannel;
    @JsonProperty("dwp|accounts|aos_quotes|payment_details|com_prefs(type='MANDATE')|channel|matchedCondition")
    private String mandateChannelMatchedCondition;
    @JsonProperty("dwp|received_commercial")
    private String receivedCommercial;
    @JsonProperty("accounts|aos_quotes|quote_draw_signature|draw")
    private String drawSignature;
    @JsonProperty("dwp|accounts|aos_quotes|send_quote_to_c|matchedCondition")
    private String sendQuoteMatchedCondition;
    @JsonProperty("accounts|aos_quotes|aos_products_quotes|addresses_aos_products_quotes")
    AccountsAsQuotes accountsAsQuotes;
    @JsonProperty("dwp|returnModule")
    private String returnModule;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("accounts|aos_quotes|assigned_user_id")
    private List<User> assignedUserId = new ArrayList<User>();
    @JsonProperty("com_prefs(type='COMMERCIAL')|channel")
    private String commercialChannel;
    @JsonProperty("dwp|alreadyContracted")
    private boolean alreadyContracted;
    @JsonProperty("accounts|aos_quotes|aos_products_quotes|advance_base_c")
    private String advanceBaseAccountQuotes;
    @JsonProperty("accounts|aos_quotes|aos_products_quotes|advance_frequency_c")
    private String advanceFrequencyAccount;
    @JsonProperty("accounts|aos_quotes|payment_details|bankaccounts|iban")
    private String iban;
    @JsonProperty("accounts|aos_quotes|send_quote_to_c")
    private String sendQuote;
    @JsonProperty("leads_contact_details(contact_details_type='Email')|id")
    private String leadsEmailContactDetails;
    @JsonProperty("accounts|aos_quotes|pricing_group_c")
    private String pricingGroup;
    @JsonProperty("accounts|aos_quotes|regularisation_c")
    private String regularisation;
    @JsonProperty("leads_contact_details(contact_details_phone_type='Work phone';contact_details_type='Phone')|contact_details_value")
    private String leadsPhoneContactDetailsValue;
    @JsonProperty("sales_channel_legal_label")
    private String salesChannel;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("accounts|aos_quotes|aos_products_quotes|line_status_c")
    private String lineStatus;
    @JsonProperty("active_lead_c")
    private boolean activeLead;
    @JsonProperty("accounts|aos_quotes|quote_draw_signature|id")
    private String drawSignatureId;
    @JsonProperty("accounts|aos_quotes|quote_type_c")
    private String quoteType;
    @JsonProperty("leads_contact_details(contact_details_phone_type='Mobile phone';contact_details_type='Phone')|contact_details_value")
    private String leadsMobileContactDetailsValue;
    @JsonProperty("record_type")
    private String recordType;
    @JsonProperty("dwp|blankfield")
    private String blankField;
    @JsonProperty("accounts|aos_quotes|aos_products_quotes|package_id")
    private String packageId;
    @JsonProperty("leads_contact_details(contact_details_type='Email')|contact_details_value")
    private String emailContactDetailsValue;
    @JsonProperty("dwp|tariff_sheet_pdf_guid")
    private String tariffSheetPdfGuide;
    @JsonProperty("stage")
    private String stage;
    @JsonProperty("dwp|sameasconnection")
    private boolean sameAsConnection;
    @JsonProperty("dwp|general_conditions_guid")
    private String generalConditionsGuid;
    @JsonProperty("accounts|aos_quotes|aos_products_quotes|tariffsheet_id")
    private String tariffsheetId;
    @JsonProperty("dwp|accounts|aos_quotes|payment_details|paymentterms|matchedCondition")
    private String paymentTermsatchedCondition;

    @JsonProperty("accounts|aos_quotes|aos_products_quotes")
    public Payload payload;
    @JsonProperty("dwp|is_main")
    public boolean isMain;
    @JsonProperty("accounts|bundle_on_account_c")
    public boolean bundleOnAccount;
    @JsonProperty("com_prefs(type='SMS')|receive_sms")
    public boolean receiveSms;
    @JsonProperty("dwp|available_product_ids")
    public List<String> availableProductIds = new ArrayList<>();
    @JsonProperty("dwp|customer_wants_to_signs")
    public boolean customerWantsToSign;
    @JsonProperty("dwp|discount_id")
    public List<String> discountIds = new ArrayList<>();
    @JsonProperty("dwp|line_items|package_properties|vooraf")
    private String lineItemPackageProperties;








    public String getContactType() {
        return contactType;
    }
    public void setContactType(String contactType) {
        this.contactType = contactType;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getSalesChannelId() {
        return salesChannelId;
    }
    public void setSalesChannelId(String salesChannelId) {
        this.salesChannelId = salesChannelId;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    public boolean getAlreadyContractedForDifferent() {
        return alreadyContractedForDifferent;
    }
    public void setAlreadyContractedForDifferent(boolean alreadyContractedForDifferent) {
        this.alreadyContractedForDifferent = alreadyContractedForDifferent;
    }
    public String getCurrentUserId() {
        return currentUserId;
    }
    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }
    public AccountsAddress getAccountAddresses() {
        return accountAddresses;
    }
    public void setAccountAddresses(AccountsAddress accountAddresses) {
        this.accountAddresses = accountAddresses;
    }
    public String getPaymentMethodMatchedCondition() {
        return paymentMethodMatchedCondition;
    }
    public void setPaymentMethodMatchedCondition(String paymentMethodMatchedCondition) {
        this.paymentMethodMatchedCondition = paymentMethodMatchedCondition;
    }
    public String getLeadsMobileContactDetailsId() {
        return leadsMobileContactDetailsId;
    }
    public void setLeadsMobileContactDetailsId(String leadsMobileContactDetailsId) {
        this.leadsMobileContactDetailsId = leadsMobileContactDetailsId;
    }
    public String getPackageProperties() {
        return packageProperties;
    }
    public void setPackageProperties(String packageProperties) {
        this.packageProperties = packageProperties;
    }
    public String getDigitalSignAgree() {
        return digitalSignAgree;
    }
    public void setDigitalSignAgree(String digitalSignAgree) {
        this.digitalSignAgree = digitalSignAgree;
    }
    public String getDoAutoCommunication() {
        return doAutoCommunication;
    }
    public void setDoAutoCommunication(String doAutoCommunication) {
        this.doAutoCommunication = doAutoCommunication;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDiscountGenConditions() {
        return discountGenConditions;
    }
    public void setDiscountGenConditions(String discountGenConditions) {
        this.discountGenConditions = discountGenConditions;
    }
    public String getRecordTypeOfRecordId() {
        return recordTypeOfRecordId;
    }
    public void setRecordTypeOfRecordId(String recordTypeOfRecordId) {
        this.recordTypeOfRecordId = recordTypeOfRecordId;
    }
    public List<String> getSignedContract() {
        return signedContract;
    }
    public void setSignedContract(List<String> signedContract) {
        this.signedContract = signedContract;
    }
    public String getAlreadySigned() {
        return alreadySigned;
    }
    public void setAlreadySigned(String alreadySigned) {
        this.alreadySigned = alreadySigned;
    }
    public String getGeneralChannel() {
        return generalChannel;
    }
    public void setGeneralChannel(String generalChannel) {
        this.generalChannel = generalChannel;
    }
    public String getLegalChannelMatchedCondition() {
        return legalChannelMatchedCondition;
    }
    public void setLegalChannelMatchedCondition(String legalChannelMatchedCondition) {
        this.legalChannelMatchedCondition = legalChannelMatchedCondition;
    }
    public String getCurrentPrimaryGroupId() {
        return currentPrimaryGroupId;
    }
    public void setCurrentPrimaryGroupId(String currentPrimaryGroupId) {
        this.currentPrimaryGroupId = currentPrimaryGroupId;
    }
    public String getFamilyKey() {
        return familyKey;
    }
    public void setFamilyKey(String familyKey) {
        this.familyKey = familyKey;
    }
    public String getBaseModule() {
        return baseModule;
    }
    public void setBaseModule(String baseModule) {
        this.baseModule = baseModule;
    }
    public String getPaymentTerms() {
        return paymentTerms;
    }
    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }
    public String getSignChannel() {
        return signChannel;
    }
    public void setSignChannel(String signChannel) {
        this.signChannel = signChannel;
    }
    public String getGenderMatchedCondition() {
        return genderMatchedCondition;
    }
    public void setGenderMatchedCondition(String genderMatchedCondition) {
        this.genderMatchedCondition = genderMatchedCondition;
    }
    public String getSignLocation() {
        return signLocation;
    }
    public void setSignLocation(String signLocation) {
        this.signLocation = signLocation;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getCaStatus() {
        return caStatus;
    }
    public void setCaStatus(String caStatus) {
        this.caStatus = caStatus;
    }
    public String getOpportunityStatus() {
        return opportunityStatus;
    }
    public void setOpportunityStatus(String opportunityStatus) {
        this.opportunityStatus = opportunityStatus;
    }
    public String getDeliveryAddressPostalCode() {
        return deliveryAddressPostalCode;
    }
    public void setDeliveryAddressPostalCode(String deliveryAddressPostalCode) {
        this.deliveryAddressPostalCode = deliveryAddressPostalCode;
    }
    public String getSalutation() {
        return salutation;
    }
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }
    public AccountsAddress getAddress() {
        return address;
    }
    public void setAddress(AccountsAddress address) {
        this.address = address;
    }
    public List<User> getPrimaryGroupIds() {
        return primaryGroupIds;
    }
    public void setPrimaryGroupIds(List<User> primaryGroupIds) {
        this.primaryGroupIds = primaryGroupIds;
    }
    public String getInformationAction() {
        return informationAction;
    }
    public void setInformationAction(String informationAction) {
        this.informationAction = informationAction;
    }
    public String getMandateChannel() {
        return mandateChannel;
    }
    public void setMandateChannel(String mandateChannel) {
        this.mandateChannel = mandateChannel;
    }
    public String getSignatureOptionMatchedCondition() {
        return signatureOptionMatchedCondition;
    }
    public void setSignatureOptionMatchedCondition(String signatureOptionMatchedCondition) {
        this.signatureOptionMatchedCondition = signatureOptionMatchedCondition;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getSignatureOption() {
        return signatureOption;
    }
    public void setSignatureOption(String signatureOption) {
        this.signatureOption = signatureOption;
    }
    public String getLeadsPhoneContactDetailsId() {
        return leadsPhoneContactDetailsId;
    }
    public void setLeadsPhoneContactDetailsId(String leadsPhoneContactDetailsId) {
        this.leadsPhoneContactDetailsId = leadsPhoneContactDetailsId;
    }
    public String getDwprecordType() {
        return dwprecordType;
    }
    public void setDwprecordType(String dwprecordType) {
        this.dwprecordType = dwprecordType;
    }
    public String getPricingDate() {
        return pricingDate;
    }
    public void setPricingDate(String pricingDate) {
        this.pricingDate = pricingDate;
    }
    public String getLegalChannel() {
        return legalChannel;
    }
    public void setLegalChannel(String legalChannel) {
        this.legalChannel = legalChannel;
    }
    public String getMandateChannelMatchedCondition() {
        return mandateChannelMatchedCondition;
    }
    public void setMandateChannelMatchedCondition(String mandateChannelMatchedCondition) {
        this.mandateChannelMatchedCondition = mandateChannelMatchedCondition;
    }
    public String getReceivedCommercial() {
        return receivedCommercial;
    }
    public void setReceivedCommercial(String receivedCommercial) {
        this.receivedCommercial = receivedCommercial;
    }
    public String getDrawSignature() {
        return drawSignature;
    }
    public void setDrawSignature(String drawSignature) {
        this.drawSignature = drawSignature;
    }
    public String getSendQuoteMatchedCondition() {
        return sendQuoteMatchedCondition;
    }
    public void setSendQuoteMatchedCondition(String sendQuoteMatchedCondition) {
        this.sendQuoteMatchedCondition = sendQuoteMatchedCondition;
    }
    public AccountsAsQuotes getAccountsAsQuotes() {
        return accountsAsQuotes;
    }
    public void setAccountsAsQuotes(AccountsAsQuotes accountsAsQuotes) {
        this.accountsAsQuotes = accountsAsQuotes;
    }
    public String getReturnModule() {
        return returnModule;
    }
    public void setReturnModule(String returnModule) {
        this.returnModule = returnModule;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public List<User> getAssignedUserId() {
        return assignedUserId;
    }
    public void setAssignedUserId(List<User> assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
    public String getCommercialChannel() {
        return commercialChannel;
    }
    public void setCommercialChannel(String commercialChannel) {
        this.commercialChannel = commercialChannel;
    }
    public boolean isAlreadyContracted() {
        return alreadyContracted;
    }
    public void setAlreadyContracted(boolean alreadyContracted) {
        this.alreadyContracted = alreadyContracted;
    }
    public String getAdvanceBaseAccountQuotes() {
        return advanceBaseAccountQuotes;
    }
    public void setAdvanceBaseAccountQuotes(String advanceBaseAccountQuotes) {
        this.advanceBaseAccountQuotes = advanceBaseAccountQuotes;
    }
    public String getAdvanceFrequencyAccount() {
        return advanceFrequencyAccount;
    }
    public void setAdvanceFrequencyAccount(String advanceFrequencyAccount) {
        this.advanceFrequencyAccount = advanceFrequencyAccount;
    }
    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }
    public String getSendQuote() {
        return sendQuote;
    }
    public void setSendQuote(String sendQuote) {
        this.sendQuote = sendQuote;
    }
    public String getLeadsEmailContactDetails() {
        return leadsEmailContactDetails;
    }
    public void setLeadsEmailContactDetails(String leadsEmailContactDetails) {
        this.leadsEmailContactDetails = leadsEmailContactDetails;
    }
    public String getPricingGroup() {
        return pricingGroup;
    }
    public void setPricingGroup(String pricingGroup) {
        this.pricingGroup = pricingGroup;
    }
    public String getRegularisation() {
        return regularisation;
    }
    public void setRegularisation(String regularisation) {
        this.regularisation = regularisation;
    }
    public String getLeadsPhoneContactDetailsValue() {
        return leadsPhoneContactDetailsValue;
    }
    public void setLeadsPhoneContactDetailsValue(String leadsPhoneContactDetailsValue) {
        this.leadsPhoneContactDetailsValue = leadsPhoneContactDetailsValue;
    }
    public String getSalesChannel() {
        return salesChannel;
    }
    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLineStatus() {
        return lineStatus;
    }
    public void setLineStatus(String lineStatus) {
        this.lineStatus = lineStatus;
    }
    public boolean isActiveLead() {
        return activeLead;
    }
    public void setActiveLead(boolean activeLead) {
        this.activeLead = activeLead;
    }
    public String getDrawSignatureId() {
        return drawSignatureId;
    }
    public void setDrawSignatureId(String drawSignatureId) {
        this.drawSignatureId = drawSignatureId;
    }
    public String getQuoteType() {
        return quoteType;
    }
    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }
    public String getLeadsMobileContactDetailsValue() {
        return leadsMobileContactDetailsValue;
    }
    public void setLeadsMobileContactDetailsValue(String leadsMobileContactDetailsValue) {
        this.leadsMobileContactDetailsValue = leadsMobileContactDetailsValue;
    }
    public String getRecordType() {
        return recordType;
    }
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
    public String getBlankField() {
        return blankField;
    }
    public void setBlankField(String blankField) {
        this.blankField = blankField;
    }
    public String getPackageId() {
        return packageId;
    }
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    public String getEmailContactDetailsValue() {
        return emailContactDetailsValue;
    }
    public void setEmailContactDetailsValue(String emailContactDetailsValue) {
        this.emailContactDetailsValue = emailContactDetailsValue;
    }
    public String getTariffSheetPdfGuide() {
        return tariffSheetPdfGuide;
    }
    public void setTariffSheetPdfGuide(String tariffSheetPdfGuide) {
        this.tariffSheetPdfGuide = tariffSheetPdfGuide;
    }
    public String getStage() {
        return stage;
    }
    public void setStage(String stage) {
        this.stage = stage;
    }
    public boolean isSameAsConnection() {
        return sameAsConnection;
    }
    public void setSameAsConnection(boolean sameAsConnection) {
        this.sameAsConnection = sameAsConnection;
    }
    public String getGeneralConditionsGuid() {
        return generalConditionsGuid;
    }
    public void setGeneralConditionsGuid(String generalConditionsGuid) {
        this.generalConditionsGuid = generalConditionsGuid;
    }
    public String getTariffsheetId() {
        return tariffsheetId;
    }
    public void setTariffsheetId(String tariffsheetId) {
        this.tariffsheetId = tariffsheetId;
    }
    public String getPaymentTermsatchedCondition() {
        return paymentTermsatchedCondition;
    }
    public void setPaymentTermsatchedCondition(String paymentTermsatchedCondition) {
        this.paymentTermsatchedCondition = paymentTermsatchedCondition;
    }
    public Payload getPayload() {
        return payload;
    }
    public void setPayload(Payload payload) {
        this.payload = payload;
    }
    public boolean isMain() {
        return isMain;
    }
    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }
    public boolean isBundleOnAccount() {
        return bundleOnAccount;
    }
    public void setBundleOnAccount(boolean bundleOnAccount) {
        this.bundleOnAccount = bundleOnAccount;
    }
    public boolean isReceiveSms() {
        return receiveSms;
    }
    public void setReceiveSms(boolean receiveSms) {
        this.receiveSms = receiveSms;
    }
    public List<String> getAvailableProductIds() {
        return availableProductIds;
    }
    public void setAvailableProductIds(List<String> availableProductIds) {
        this.availableProductIds = availableProductIds;
    }
    public boolean isCustomerWantsToSign() {
        return customerWantsToSign;
    }
    public void setCustomerWantsToSign(boolean customerWantsToSign) {
        this.customerWantsToSign = customerWantsToSign;
    }
    public List<String> getDiscountIds() {
        return discountIds;
    }
    public void setDiscountIds(List<String> discountIds) {
        this.discountIds = discountIds;
    }
    public String getLineItemPackageProperties() {
        return lineItemPackageProperties;
    }
    public void setLineItemPackageProperties(String lineItemPackageProperties) {
        this.lineItemPackageProperties = lineItemPackageProperties;
    }



}
