package com.essent.testing.client.billing;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.dunning.ReverseDunningInvoiceRequest;
import com.essent.be.jbilling.api.rest.dunning.ReverseDunningInvoiceResponse;
import com.essent.be.jbilling.api.rest.invoice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillingInvoiceRest extends BillingRootClient {
  private static final Logger LOG = LoggerFactory.getLogger(BillingInvoiceRest.class);

  protected static final String restUrl = "/api/rest/invoice";

  protected static final String getInvoicesForBillingIdUrl = "/getInvoicesForBillingIDs";
  protected static final String getInvoiceIDsForBillingIDsUrl = "/getInvoiceIDsForBillingIDs";
  protected static final String getInvoiceByIdUrl = "/getInvoice";
  protected static final String reverseDunningCost = "/reverse-dunning-invoice";
  protected static final String changeInvoiceBalance = "/changeInvoiceBalance";
  protected static final String getInvoiceDetails = "/getInvoiceDetails";
  protected static final String recalcInvoicedAdvance = "/recalculate-invoiced-advance";
  protected static final String recalculateInvoicedMetered = "/recalculate-invoiced-metered";
  protected static final String redoExportForBillingRun = "/redoExportForBillingRun";
  protected static final String redoExportForInvoices = "/redoExportForInvoices";
  protected static final String setInvoiceBlock = "/setInvoiceBlock";
  protected static final String creditInvoice = "/creditInvoice";
  protected static final String recalcInvoice = "/recalcInvoice";
  protected static final String getInvoiceExternalReferenceUrl = "/getInvoiceExternalReference";
  protected static final String getNonCommodityInvoices = "/getNonCommodityInvoices";
  protected static final String invoiceOfTypeExists = "/invoiceOfTypeExists";

  public BillingInvoiceRest() {
    super(restUrl, LOG);
  }

  public RSInvoicesForBillingIDsResp getInvoiceDetails(BillingInvoiceRequest request) {
    return call(getInvoiceDetails, request, RSInvoicesForBillingIDsResp.class);
  }

  public RSInvoicesForBillingIDsResp getInvoicesForBillingIds(RSGetInvoicesForBillingIDs request) {
    return call(getInvoicesForBillingIdUrl, request, RSInvoicesForBillingIDsResp.class);
  }

  public RSGetInvoiceIDsForBillingIDsResp getInvoiceIDsForBillingIDs(
      RSGetInvoiceIDsForBillingIDs request) {
    return call(getInvoiceIDsForBillingIDsUrl, request, RSGetInvoiceIDsForBillingIDsResp.class);
  }

  public BillingInvoiceResponse getInvoiceById(BillingInvoiceRequest request) {

    return call(getInvoiceByIdUrl, request, BillingInvoiceResponse.class);
  }

  public ReverseDunningInvoiceResponse reverseDunningCostInvoice(
      ReverseDunningInvoiceRequest request) {

    return call(reverseDunningCost, request, ReverseDunningInvoiceResponse.class);
  }

  public RestResponse changeInvoiceBalance(RSChangeInvoiceBalanceRequest request) {
    return call(changeInvoiceBalance, request, RestResponse.class);
  }

  public RecalculateInvoicedMeteredResponse recalculateInvoicedMetered(
      RecalculateInvoicedMeteredRequest request) {

    return call(recalculateInvoicedMetered, request, RecalculateInvoicedMeteredResponse.class);
  }

  public RecalcInvoicedAdvanceResponse recalculateInvoicedAdvance(
      RecalcInvoicedAdvanceRequest request) {

    return call(recalcInvoicedAdvance, request, RecalcInvoicedAdvanceResponse.class);
  }

  public RestResponse redoExportForBillingRun(RedoExportForBillingRunRequest request) {
    return call(redoExportForBillingRun, request, RestResponse.class);
  }

  public RestResponse redoExportForInvoices(RedoExportForInvoicesRequest request) {
    return call(redoExportForInvoices, request, RestResponse.class);
  }

  public InvoiceBlockResponse setInvoiceBlock(InvoiceBlockRequest request) {
    return call(setInvoiceBlock, request, InvoiceBlockResponse.class);
  }

  public RestResponse creditInvoice(CreditInvoiceRequest request) {
    return call(creditInvoice, request, RestResponse.class);
  }

  public RestResponse recalcInvoice(RecalcInvoiceRequest request) {
    return call(recalcInvoice, request, RestResponse.class);
  }

  public BillingInvoiceExternalReferences getInvoiceExternalReference(String invoiceNumber) {
    // We cannot use callWIthParameters as this is not a key-value pair.
    String url = getInvoiceExternalReferenceUrl + "/" + invoiceNumber;
    return call(url, BillingInvoiceExternalReferences.class);
  }

  public RSGetNonCommodityInvoiceResponse getNonCommodityInvoices(
      RSGetNonCommodityInvoiceRequest request) {
    return call(getNonCommodityInvoices, request, RSGetNonCommodityInvoiceResponse.class);
  }

  public InvoiceOfTypeExistsResponse invoiceOfTypeExists(InvoiceOfTypeExistsRequest request) {
    return call(invoiceOfTypeExists, request, InvoiceOfTypeExistsResponse.class);
  }
}
