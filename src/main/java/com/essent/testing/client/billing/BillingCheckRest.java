package com.essent.testing.client.billing;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerBillRunRequest;
import com.essent.be.jbilling.api.rest.check.RSBillingCheckResponse;
import com.essent.be.jbilling.api.rest.check.RSCheckResponse;
import com.essent.be.jbilling.api.rest.check.RSVersionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillingCheckRest extends BillingRootClient {

	private static final Logger LOG = LoggerFactory.getLogger(BillingCheckRest.class);

	protected static final String rootUrl = "/api/rest/check";
	protected static final String pingUrl = "/ping";
	protected static final String healthUrl = "/health";
	protected static final String versionUrl = "/version";
	protected static final String checkBillingForCustomerUrl = "/checkSelectedBillRun";

	public BillingCheckRest() {
		super(rootUrl, LOG);
	}

	public RSCheckResponse healthCheck() {
		return call(healthUrl, RSCheckResponse.class);
	}

	public RestResponse ping() {
		return call(pingUrl, RestResponse.class);
	}

	public RSVersionResponse version() {
		return call(versionUrl, RSVersionResponse.class);
	}

	public RSBillingCheckResponse checkBillingForCustomer(RSTriggerBillRunRequest request) {
		return call(checkBillingForCustomerUrl, request, RSBillingCheckResponse.class);
	}
}
