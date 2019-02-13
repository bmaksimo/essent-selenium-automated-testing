package stepdefinitions.quote.api;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {

    @JsonProperty("dgo_id_c")
    private String id;
    @JsonProperty("line_status_c")
    private String lineStatus;
    @JsonProperty("dwp|is_main")
    private boolean isMain;
    @JsonProperty("residential | startdate")
    private String residentialStartDate;
    @JsonProperty("addresses_aos_products_quotes")
    QuoteAddress quoteAddress;
    @JsonProperty("delivery_address_postalcode")
    private String postalCode;
    @JsonProperty("addresses_aos_products_quotes | is_main")
    private boolean isMainAddress;
    @JsonProperty("tariffsheet_id")
    private String tariffsheetId;
    @JsonProperty("package_id")
    private String packageId;
    @JsonProperty("advance_base_c")
    private String baseAdvance;
    @JsonProperty("advance_frequency_c")
    private String frequencyAdvance;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("productType")
    private String product_type_c;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("recordTypeOfRecordId")
    private String recordTypeOfRecordId;
    @JsonProperty("baseModule")
    private String baseModule;
    @JsonProperty("package_product_properties")
    private String packageProductProperties;
    @JsonProperty("dwp | tariffsheet_price | selling_price_high")
    private String tarifsheetHighPrice;
    @JsonProperty("dwp | tariffsheet_price | selling_price_low")
    private String tarifsheetLowPrice;
    @JsonProperty("dwp | tariffsheet_price | selling_price_th")
    private String tarifsheetThPrice;
    @JsonProperty("dwp | tariffsheet_price | selling_price_exclnight")
    private String tarifsheetExclNightPrice;
    @JsonProperty("dwp | tariffsheet_price | selling_price_oneoff")
    private String tarifsheetOneOffPrice;
    @JsonProperty("dwp | tariffsheet_price | selling_price_fixedfee")
    private String tarifsheetFixedFeePrice;
    @JsonProperty("dwp | alreadyContracted")
    private boolean alreadyContracted;
    @JsonProperty("dwp | alreadyContractedForDifferentClient")
    private boolean alreadyContractedForDifferentClient;
    @JsonProperty("dwp | line_items | properties | duration_from")
    private String durationFrom;
    @JsonProperty("dwp | tariffsheet_price | id")
    private String tarifsheetPriceId;
    @JsonProperty("dwp | tariffsheet_price | selling_price_high_incl_vat")
    private String tarifsheetHighPriceInclVat;
    @JsonProperty("dwp | tariffsheet_price | selling_price_low_incl_vat")
    private String tarifsheetLowPriceInclVat;
    @JsonProperty("dwp | tariffsheet_price | selling_price_th_incl_vat")
    private String tarifsheetThPriceInclVat;
    @JsonProperty("dwp | tariffsheet_price | selling_price_exclnight_incl_vat")
    private String tarifsheetExclNightPriceInclVat;
    @JsonProperty("dwp | tariffsheet_price | selling_price_fixedfee_incl_vat")
    private String tarifsheetFixedFeePriceInclVat;
    @JsonProperty("dwp | tariffsheet_price | selling_price_oneoff_incl_vat")
    private String tarifsheetOneOffPriceInclVat;
    @JsonProperty("dwp | tariffsheet_price | indexation_parameter")
    private String tarifsheetPriceIndexationParameter;
    @JsonProperty("dwp | tariffsheet_price | sourcing_product")
    private String tarifsheetPriceSourcingProduct;
    @JsonProperty("dwp | available_product_ids")
    ArrayList <Object> availableProductIds = new ArrayList <Object>();
    @JsonProperty("dwp | discount_assigned")
    ArrayList <Object> discountAssigned = new ArrayList <Object>();
    @JsonProperty("meter_open_c")
    private boolean meterOpen;
    @JsonProperty("move_in_c")
    private boolean moveIn;
    @JsonProperty("switchtype_c")
    private String switchType;
    @JsonProperty("dwp | mig_module_c")
    private String migModule;
    @JsonProperty("dwp | mig_label_c")
    private String migLabel;
    @JsonProperty("mig_start_context_c")
    private String migStartContext;
    @JsonProperty("previous_supplier_c")
    private String previousSupplier;
    @JsonProperty("up_start_date_c")
    private String upStartDate;
    @JsonProperty("prev_contract_line_id")
    private String prevContractLineId;
    @JsonProperty("current_dealer_id")
    private String currentDealerId;
    @JsonProperty("contract_line_status_c")
    private String contractLineStatus;
    @JsonProperty("up_end_date_c")
    private String upEndDate;
    @JsonProperty("dwp | external_message_GUID_3")
    private String externalMessageGUID_3;
    @JsonProperty("dwp | external_message_GUID_4")
    private String externalMessageGUID_4;
    @JsonProperty("dwp | external_message_GUID_1")
    private String externalMessageGUID_1;
    @JsonProperty("dwp | external_message_GUID_2")
    private String externalMessageGUID_2;
    @JsonProperty("ean_c")
    private String ean;
    @JsonProperty("meter_type_c")
    private String meterType;
    @JsonProperty("meter_configuration_c")
    private String meterConfiguration;
    @JsonProperty("meter_no_c")
    private String meterNo;
    @JsonProperty("meter_no_excl_night_c")
    private String meterNoExclNight;
    @JsonProperty("solar_panels_c")
    private boolean solarPanels;
    @JsonProperty("solar_panels_power_c")
    private String solarPanelsPower;
    @JsonProperty("solar_panels_power_unknown_c")
    private boolean solarPanelsPowerUnknown;
    @JsonProperty("reading_date_c")
    private String readingDate;
    @JsonProperty("indexTh")
    private String index_th_c;
    @JsonProperty("index_high_c")
    private String indexHigh;
    @JsonProperty("index_low_c")
    private String indexLow;
    @JsonProperty("index_exclnight_c")
    private String indexExclNight;
    @JsonProperty("dwp | copy_eplus_from_parent_address")
    private String EplusCopy;
    @JsonProperty("test")
    private boolean test;
    @JsonProperty("market_mock_c")
    private boolean marketMock;
    @JsonProperty("dwp | market_mock_scenario_mig6")
    private String marketMockScenario;
    @JsonProperty("external_message_id")
    private String externalMessageId;
    @JsonProperty("mig_version")
    private String migVersion;
    @JsonProperty("mm_preswitch_template_c")
    private String preswitchTemplate;
    @JsonProperty("service_c")
    private String service;
    @JsonProperty("mig_connectionparameters | desync")
    private boolean desync;
    @JsonProperty("mig_connectionparameters | request_unlock")
    private boolean requestUnlock;
    @JsonProperty("mig_connectionparameters | takeover")
    private boolean takeover;
    @JsonProperty("mig_connectionparameters | contestation")
    private boolean contestation;
    @JsonProperty("mig_connectionparameters | related_transaction_id")
    private String relatedTransactionId;
    @JsonProperty("mig_connectionparameters | smart_meter")
    private String smartMeter;
    @JsonProperty("mig_connectionparameters | supplier_time_frame")
    private String supplierTimeframe;
    @JsonProperty("mig_connectionparameters | billing_frequency")
    private String billingFrequency;
    @JsonProperty("mig_connectionparameters | information_frequency")
    private String informationFrequency;
    @JsonProperty("mig_connectionparameters | service_component_change")
    private boolean serviceComponentChange;
    @JsonProperty("dwp | mig_start_context_c | matchedCondition")
    private String startContextMatchedCondition;
    @JsonProperty("dwp | dwp | external_message_GUID_3 | matchedCondition")
    private String matchedConditionGUID_3;
    @JsonProperty("dwp | dwp | external_message_GUID_4 | matchedCondition")
    private String matchedConditionGUID_4;
    @JsonProperty("dwp | dwp | external_message_GUID_1 | matchedCondition")
    private String matchedConditionGUID_1;
    @JsonProperty("dwp | dwp | external_message_GUID_2 | matchedCondition")
    private String matchedConditionGUID_2;
    @JsonProperty("dwp | meter_type_c | matchedCondition")
    private String meterTypeMatchedCondition;
    @JsonProperty("dwp | meter_configuration_c | matchedCondition")
    private String meterConfigurationMatchedCondition;
    @JsonProperty("dwp | external_message_id | matchedCondition")
    private String externalMessageIdMatchedCondition;
    @JsonProperty("advance_amount_incl_vat_c")
    private String advanceAmountInclVat;
    @JsonProperty("usage_single_c")
    private String singleUsage;
    @JsonProperty("usage_high_c")
    private String highUsage;
    @JsonProperty("usage_low_c")
    private String lowUsage;
    @JsonProperty("usage_excl_night_c")
    private String usageExclNight;
    @JsonProperty("dwp | discount_details")
    private String discountDetails;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLineStatus() {
        return lineStatus;
    }
    public void setLineStatus(String lineStatus) {
        this.lineStatus = lineStatus;
    }
    public boolean isMain() {
        return isMain;
    }
    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }
    public String getResidentialStartDate() {
        return residentialStartDate;
    }
    public void setResidentialStartDate(String residentialStartDate) {
        this.residentialStartDate = residentialStartDate;
    }
    public QuoteAddress getQuoteAddress() {
        return quoteAddress;
    }
    public void setQuoteAddress(QuoteAddress quoteAddress) {
        this.quoteAddress = quoteAddress;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public boolean isMainAddress() {
        return isMainAddress;
    }
    public void setMainAddress(boolean isMainAddress) {
        this.isMainAddress = isMainAddress;
    }
    public String getTariffsheetId() {
        return tariffsheetId;
    }
    public void setTariffsheetId(String tariffsheetId) {
        this.tariffsheetId = tariffsheetId;
    }
    public String getPackageId() {
        return packageId;
    }
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    public String getBaseAdvance() {
        return baseAdvance;
    }
    public void setBaseAdvance(String baseAdvance) {
        this.baseAdvance = baseAdvance;
    }
    public String getFrequencyAdvance() {
        return frequencyAdvance;
    }
    public void setFrequencyAdvance(String frequencyAdvance) {
        this.frequencyAdvance = frequencyAdvance;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProduct_type_c() {
        return product_type_c;
    }
    public void setProduct_type_c(String product_type_c) {
        this.product_type_c = product_type_c;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getRecordTypeOfRecordId() {
        return recordTypeOfRecordId;
    }
    public void setRecordTypeOfRecordId(String recordTypeOfRecordId) {
        this.recordTypeOfRecordId = recordTypeOfRecordId;
    }
    public String getBaseModule() {
        return baseModule;
    }
    public void setBaseModule(String baseModule) {
        this.baseModule = baseModule;
    }
    public String getPackageProductProperties() {
        return packageProductProperties;
    }
    public void setPackageProductProperties(String packageProductProperties) {
        this.packageProductProperties = packageProductProperties;
    }
    public String getTarifsheetHighPrice() {
        return tarifsheetHighPrice;
    }
    public void setTarifsheetHighPrice(String tarifsheetHighPrice) {
        this.tarifsheetHighPrice = tarifsheetHighPrice;
    }
    public String getTarifsheetLowPrice() {
        return tarifsheetLowPrice;
    }
    public void setTarifsheetLowPrice(String tarifsheetLowPrice) {
        this.tarifsheetLowPrice = tarifsheetLowPrice;
    }
    public String getTarifsheetThPrice() {
        return tarifsheetThPrice;
    }
    public void setTarifsheetThPrice(String tarifsheetThPrice) {
        this.tarifsheetThPrice = tarifsheetThPrice;
    }
    public String getTarifsheetExclNightPrice() {
        return tarifsheetExclNightPrice;
    }
    public void setTarifsheetExclNightPrice(String tarifsheetExclNightPrice) {
        this.tarifsheetExclNightPrice = tarifsheetExclNightPrice;
    }
    public String getTarifsheetOneOffPrice() {
        return tarifsheetOneOffPrice;
    }
    public void setTarifsheetOneOffPrice(String tarifsheetOneOffPrice) {
        this.tarifsheetOneOffPrice = tarifsheetOneOffPrice;
    }
    public String getTarifsheetFixedFeePrice() {
        return tarifsheetFixedFeePrice;
    }
    public void setTarifsheetFixedFeePrice(String tarifsheetFixedFeePrice) {
        this.tarifsheetFixedFeePrice = tarifsheetFixedFeePrice;
    }
    public boolean isAlreadyContracted() {
        return alreadyContracted;
    }
    public void setAlreadyContracted(boolean alreadyContracted) {
        this.alreadyContracted = alreadyContracted;
    }
    public boolean isAlreadyContractedForDifferentClient() {
        return alreadyContractedForDifferentClient;
    }
    public void setAlreadyContractedForDifferentClient(boolean alreadyContractedForDifferentClient) {
        this.alreadyContractedForDifferentClient = alreadyContractedForDifferentClient;
    }
    public String getDurationFrom() {
        return durationFrom;
    }
    public void setDurationFrom(String durationFrom) {
        this.durationFrom = durationFrom;
    }
    public String getTarifsheetPriceId() {
        return tarifsheetPriceId;
    }
    public void setTarifsheetPriceId(String tarifsheetPriceId) {
        this.tarifsheetPriceId = tarifsheetPriceId;
    }
    public String getTarifsheetHighPriceInclVat() {
        return tarifsheetHighPriceInclVat;
    }
    public void setTarifsheetHighPriceInclVat(String tarifsheetHighPriceInclVat) {
        this.tarifsheetHighPriceInclVat = tarifsheetHighPriceInclVat;
    }
    public String getTarifsheetLowPriceInclVat() {
        return tarifsheetLowPriceInclVat;
    }
    public void setTarifsheetLowPriceInclVat(String tarifsheetLowPriceInclVat) {
        this.tarifsheetLowPriceInclVat = tarifsheetLowPriceInclVat;
    }
    public String getTarifsheetThPriceInclVat() {
        return tarifsheetThPriceInclVat;
    }
    public void setTarifsheetThPriceInclVat(String tarifsheetThPriceInclVat) {
        this.tarifsheetThPriceInclVat = tarifsheetThPriceInclVat;
    }
    public String getTarifsheetExclNightPriceInclVat() {
        return tarifsheetExclNightPriceInclVat;
    }
    public void setTarifsheetExclNightPriceInclVat(String tarifsheetExclNightPriceInclVat) {
        this.tarifsheetExclNightPriceInclVat = tarifsheetExclNightPriceInclVat;
    }
    public String getTarifsheetFixedFeePriceInclVat() {
        return tarifsheetFixedFeePriceInclVat;
    }
    public void setTarifsheetFixedFeePriceInclVat(String tarifsheetFixedFeePriceInclVat) {
        this.tarifsheetFixedFeePriceInclVat = tarifsheetFixedFeePriceInclVat;
    }
    public String getTarifsheetOneOffPriceInclVat() {
        return tarifsheetOneOffPriceInclVat;
    }
    public void setTarifsheetOneOffPriceInclVat(String tarifsheetOneOffPriceInclVat) {
        this.tarifsheetOneOffPriceInclVat = tarifsheetOneOffPriceInclVat;
    }
    public String getTarifsheetPriceIndexationParameter() {
        return tarifsheetPriceIndexationParameter;
    }
    public void setTarifsheetPriceIndexationParameter(String tarifsheetPriceIndexationParameter) {
        this.tarifsheetPriceIndexationParameter = tarifsheetPriceIndexationParameter;
    }
    public String getTarifsheetPriceSourcingProduct() {
        return tarifsheetPriceSourcingProduct;
    }
    public void setTarifsheetPriceSourcingProduct(String tarifsheetPriceSourcingProduct) {
        this.tarifsheetPriceSourcingProduct = tarifsheetPriceSourcingProduct;
    }
    public ArrayList<Object> getAvailableProductIds() {
        return availableProductIds;
    }
    public void setAvailableProductIds(ArrayList<Object> availableProductIds) {
        this.availableProductIds = availableProductIds;
    }
    public ArrayList<Object> getDiscountAssigned() {
        return discountAssigned;
    }
    public void setDiscountAssigned(ArrayList<Object> discountAssigned) {
        this.discountAssigned = discountAssigned;
    }
    public boolean isMeterOpen() {
        return meterOpen;
    }
    public void setMeterOpen(boolean meterOpen) {
        this.meterOpen = meterOpen;
    }
    public boolean isMoveIn() {
        return moveIn;
    }
    public void setMoveIn(boolean moveIn) {
        this.moveIn = moveIn;
    }
    public String getSwitchType() {
        return switchType;
    }
    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }
    public String getMigModule() {
        return migModule;
    }
    public void setMigModule(String migModule) {
        this.migModule = migModule;
    }
    public String getMigLabel() {
        return migLabel;
    }
    public void setMigLabel(String migLabel) {
        this.migLabel = migLabel;
    }
    public String getMigStartContext() {
        return migStartContext;
    }
    public void setMigStartContext(String migStartContext) {
        this.migStartContext = migStartContext;
    }
    public String getPreviousSupplier() {
        return previousSupplier;
    }
    public void setPreviousSupplier(String previousSupplier) {
        this.previousSupplier = previousSupplier;
    }
    public String getUpStartDate() {
        return upStartDate;
    }
    public void setUpStartDate(String upStartDate) {
        this.upStartDate = upStartDate;
    }
    public String getPrevContractLineId() {
        return prevContractLineId;
    }
    public void setPrevContractLineId(String prevContractLineId) {
        this.prevContractLineId = prevContractLineId;
    }
    public String getCurrentDealerId() {
        return currentDealerId;
    }
    public void setCurrentDealerId(String currentDealerId) {
        this.currentDealerId = currentDealerId;
    }
    public String getContractLineStatus() {
        return contractLineStatus;
    }
    public void setContractLineStatus(String contractLineStatus) {
        this.contractLineStatus = contractLineStatus;
    }
    public String getUpEndDate() {
        return upEndDate;
    }
    public void setUpEndDate(String upEndDate) {
        this.upEndDate = upEndDate;
    }
    public String getExternalMessageGUID_3() {
        return externalMessageGUID_3;
    }
    public void setExternalMessageGUID_3(String externalMessageGUID_3) {
        this.externalMessageGUID_3 = externalMessageGUID_3;
    }
    public String getExternalMessageGUID_4() {
        return externalMessageGUID_4;
    }
    public void setExternalMessageGUID_4(String externalMessageGUID_4) {
        this.externalMessageGUID_4 = externalMessageGUID_4;
    }
    public String getExternalMessageGUID_1() {
        return externalMessageGUID_1;
    }
    public void setExternalMessageGUID_1(String externalMessageGUID_1) {
        this.externalMessageGUID_1 = externalMessageGUID_1;
    }
    public String getExternalMessageGUID_2() {
        return externalMessageGUID_2;
    }
    public void setExternalMessageGUID_2(String externalMessageGUID_2) {
        this.externalMessageGUID_2 = externalMessageGUID_2;
    }
    public String getEan() {
        return ean;
    }
    public void setEan(String ean) {
        this.ean = ean;
    }
    public String getMeterType() {
        return meterType;
    }
    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }
    public String getMeterConfiguration() {
        return meterConfiguration;
    }
    public void setMeterConfiguration(String meterConfiguration) {
        this.meterConfiguration = meterConfiguration;
    }
    public String getMeterNo() {
        return meterNo;
    }
    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }
    public String getMeterNoExclNight() {
        return meterNoExclNight;
    }
    public void setMeterNoExclNight(String meterNoExclNight) {
        this.meterNoExclNight = meterNoExclNight;
    }
    public boolean isSolarPanels() {
        return solarPanels;
    }
    public void setSolarPanels(boolean solarPanels) {
        this.solarPanels = solarPanels;
    }
    public String getSolarPanelsPower() {
        return solarPanelsPower;
    }
    public void setSolarPanelsPower(String solarPanelsPower) {
        this.solarPanelsPower = solarPanelsPower;
    }
    public boolean isSolarPanelsPowerUnknown() {
        return solarPanelsPowerUnknown;
    }
    public void setSolarPanelsPowerUnknown(boolean solarPanelsPowerUnknown) {
        this.solarPanelsPowerUnknown = solarPanelsPowerUnknown;
    }
    public String getReadingDate() {
        return readingDate;
    }
    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }
    public String getIndex_th_c() {
        return index_th_c;
    }
    public void setIndex_th_c(String index_th_c) {
        this.index_th_c = index_th_c;
    }
    public String getIndexHigh() {
        return indexHigh;
    }
    public void setIndexHigh(String indexHigh) {
        this.indexHigh = indexHigh;
    }
    public String getIndexLow() {
        return indexLow;
    }
    public void setIndexLow(String indexLow) {
        this.indexLow = indexLow;
    }
    public String getIndexExclNight() {
        return indexExclNight;
    }
    public void setIndexExclNight(String indexExclNight) {
        this.indexExclNight = indexExclNight;
    }
    public String getEplusCopy() {
        return EplusCopy;
    }
    public void setEplusCopy(String eplusCopy) {
        EplusCopy = eplusCopy;
    }
    public boolean isTest() {
        return test;
    }
    public void setTest(boolean test) {
        this.test = test;
    }
    public boolean isMarketMock() {
        return marketMock;
    }
    public void setMarketMock(boolean marketMock) {
        this.marketMock = marketMock;
    }
    public String getMarketMockScenario() {
        return marketMockScenario;
    }
    public void setMarketMockScenario(String marketMockScenario) {
        this.marketMockScenario = marketMockScenario;
    }
    public String getExternalMessageId() {
        return externalMessageId;
    }
    public void setExternalMessageId(String externalMessageId) {
        this.externalMessageId = externalMessageId;
    }
    public String getMigVersion() {
        return migVersion;
    }
    public void setMigVersion(String migVersion) {
        this.migVersion = migVersion;
    }
    public String getPreswitchTemplate() {
        return preswitchTemplate;
    }
    public void setPreswitchTemplate(String preswitchTemplate) {
        this.preswitchTemplate = preswitchTemplate;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    public boolean isDesync() {
        return desync;
    }
    public void setDesync(boolean desync) {
        this.desync = desync;
    }
    public boolean isRequestUnlock() {
        return requestUnlock;
    }
    public void setRequestUnlock(boolean requestUnlock) {
        this.requestUnlock = requestUnlock;
    }
    public boolean isTakeover() {
        return takeover;
    }
    public void setTakeover(boolean takeover) {
        this.takeover = takeover;
    }
    public boolean isContestation() {
        return contestation;
    }
    public void setContestation(boolean contestation) {
        this.contestation = contestation;
    }
    public String getRelatedTransactionId() {
        return relatedTransactionId;
    }
    public void setRelatedTransactionId(String relatedTransactionId) {
        this.relatedTransactionId = relatedTransactionId;
    }
    public String getSmartMeter() {
        return smartMeter;
    }
    public void setSmartMeter(String smartMeter) {
        this.smartMeter = smartMeter;
    }
    public String getSupplierTimeframe() {
        return supplierTimeframe;
    }
    public void setSupplierTimeframe(String supplierTimeframe) {
        this.supplierTimeframe = supplierTimeframe;
    }
    public String getBillingFrequency() {
        return billingFrequency;
    }
    public void setBillingFrequency(String billingFrequency) {
        this.billingFrequency = billingFrequency;
    }
    public String getInformationFrequency() {
        return informationFrequency;
    }
    public void setInformationFrequency(String informationFrequency) {
        this.informationFrequency = informationFrequency;
    }
    public boolean isServiceComponentChange() {
        return serviceComponentChange;
    }
    public void setServiceComponentChange(boolean serviceComponentChange) {
        this.serviceComponentChange = serviceComponentChange;
    }
    public String getStartContextMatchedCondition() {
        return startContextMatchedCondition;
    }
    public void setStartContextMatchedCondition(String startContextMatchedCondition) {
        this.startContextMatchedCondition = startContextMatchedCondition;
    }
    public String getMatchedConditionGUID_3() {
        return matchedConditionGUID_3;
    }
    public void setMatchedConditionGUID_3(String matchedConditionGUID_3) {
        this.matchedConditionGUID_3 = matchedConditionGUID_3;
    }
    public String getMatchedConditionGUID_4() {
        return matchedConditionGUID_4;
    }
    public void setMatchedConditionGUID_4(String matchedConditionGUID_4) {
        this.matchedConditionGUID_4 = matchedConditionGUID_4;
    }
    public String getMatchedConditionGUID_1() {
        return matchedConditionGUID_1;
    }
    public void setMatchedConditionGUID_1(String matchedConditionGUID_1) {
        this.matchedConditionGUID_1 = matchedConditionGUID_1;
    }
    public String getMatchedConditionGUID_2() {
        return matchedConditionGUID_2;
    }
    public void setMatchedConditionGUID_2(String matchedConditionGUID_2) {
        this.matchedConditionGUID_2 = matchedConditionGUID_2;
    }
    public String getMeterTypeMatchedCondition() {
        return meterTypeMatchedCondition;
    }
    public void setMeterTypeMatchedCondition(String meterTypeMatchedCondition) {
        this.meterTypeMatchedCondition = meterTypeMatchedCondition;
    }
    public String getMeterConfigurationMatchedCondition() {
        return meterConfigurationMatchedCondition;
    }
    public void setMeterConfigurationMatchedCondition(String meterConfigurationMatchedCondition) {
        this.meterConfigurationMatchedCondition = meterConfigurationMatchedCondition;
    }
    public String getExternalMessageIdMatchedCondition() {
        return externalMessageIdMatchedCondition;
    }
    public void setExternalMessageIdMatchedCondition(String externalMessageIdMatchedCondition) {
        this.externalMessageIdMatchedCondition = externalMessageIdMatchedCondition;
    }
    public String getAdvanceAmountInclVat() {
        return advanceAmountInclVat;
    }
    public void setAdvanceAmountInclVat(String advanceAmountInclVat) {
        this.advanceAmountInclVat = advanceAmountInclVat;
    }
    public String getSingleUsage() {
        return singleUsage;
    }
    public void setSingleUsage(String singleUsage) {
        this.singleUsage = singleUsage;
    }
    public String getHighUsage() {
        return highUsage;
    }
    public void setHighUsage(String highUsage) {
        this.highUsage = highUsage;
    }
    public String getLowUsage() {
        return lowUsage;
    }
    public void setLowUsage(String lowUsage) {
        this.lowUsage = lowUsage;
    }
    public String getUsageExclNight() {
        return usageExclNight;
    }
    public void setUsageExclNight(String usageExclNight) {
        this.usageExclNight = usageExclNight;
    }
    public String getDiscountDetails() {
        return discountDetails;
    }
    public void setDiscountDetails(String discountDetails) {
        this.discountDetails = discountDetails;
    }


}
