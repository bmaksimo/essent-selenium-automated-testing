package com.essent.restclients;

import com.essent.be.jbilling.api.rest.energycomm.MarketMessageResponse;
import com.essent.be.jbilling.api.rest.energycomm.RSCheckGridfeeToRectifyBilledResponse;
import com.essent.be.jbilling.api.rest.energycomm.UpdateMasterDataResponse;
import com.essent.be.jbilling.api.rest.energycomm.ValidateGridfeeResponse;
import com.essent.belgium.energycomm.ws_to_bo.BasePayload;
import com.essent.testing.client.billing.BillingRootClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillingEnergyCommRest extends BillingRootClient {
  private static final Logger LOG = LoggerFactory.getLogger(BillingEnergyCommRest.class);

  protected static final String restUrl = "/api/rest/energycomm";

  protected static final String updateMasterUrl = "/updateMasterData";
  protected static final String gridfeecheckUrl = "/validateGridfee";
  protected static final String checkGridfeeToRectifyBilled = "/checkGridfeeToRectifyBilled";

  public BillingEnergyCommRest() {
    super(restUrl, LOG);
  }

  public MarketMessageResponse postEnergyCommMessage(BasePayload req) {
    // Special case, we already have the correct payload.
    return call("", req, MarketMessageResponse.class);
  }

  public ValidateGridfeeResponse checkGridfee(BasePayload req) {
    return call(gridfeecheckUrl, req, ValidateGridfeeResponse.class);
  }

  public RSCheckGridfeeToRectifyBilledResponse checkIfOrginalAlreadyBilled(BasePayload req) {
    return call(checkGridfeeToRectifyBilled, req, RSCheckGridfeeToRectifyBilledResponse.class);
  }

  public UpdateMasterDataResponse postUpdateMasterData(BasePayload req) {
    return call(updateMasterUrl, req, UpdateMasterDataResponse.class);
  }
}
