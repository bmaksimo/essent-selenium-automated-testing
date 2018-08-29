package com.essent.testing.client.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerBillRunRequest;
import com.essent.be.jbilling.api.rest.batch.RSTriggerDunningRequest;
import com.essent.be.jbilling.api.rest.batch.RSTriggerMediationRequest;
import com.essent.be.jbilling.api.rest.batch.admin.RSGetThreadsExecutingJobResponse;
import com.essent.be.jbilling.api.rest.batch.admin.RSShowRunningJobsResponse;
import com.essent.be.jbilling.api.rest.batch.admin.RSStopRunningJobRequest;

public class BillingBatch extends BillingRootClient {
    private static final Logger LOG = LoggerFactory.getLogger(BillingBatch.class);

    protected final static String restUrl = "/api/rest/batch";
	protected final static String triggerMediationUrl = "/triggerMediation";
	protected final static String triggerDunningUrl = "/triggerDunning";
	protected final static String checkDunningUrl = "/dunningRunning";
	protected final static String checkMediationUrl = "/mediationRunning";
	protected final static String triggerBillingUrl = "/triggerBilling";
	protected final static String checkBillingUrl = "/billingRunning";
	protected final static String showRunningJobsUrl = "/showRunningJobs";
	protected final static String stopRunningJobUrl = "/stopRunningJob";
	protected final static String getThreadsExecutingJob = "/getThreadsExecutingJob";

	
	public BillingBatch() {
		super(restUrl, LOG);
	}

	public RestResponse triggerMediation(RSTriggerMediationRequest request) {
		return call(triggerMediationUrl, request, RestResponse.class);
	}


	public RestResponse checkMediationRunning() {
		return callSilently( checkMediationUrl, RestResponse.class);
	}

	public RestResponse checkDunningRunning() {
		// This is called a lot and obfuscates the log, so be quit about it.
		return callSilently( checkDunningUrl, RestResponse.class);
	}

	public RestResponse checkBillingRunning() {
		return callSilently(checkBillingUrl, RestResponse.class);
	}

	public RSShowRunningJobsResponse showRunningJobs() {
		return callSilently(showRunningJobsUrl, RSShowRunningJobsResponse.class);
	}

	public RestResponse stopRunningJob(RSStopRunningJobRequest request) {
		return call(stopRunningJobUrl, request, RestResponse.class);
	}
	
	public RestResponse triggerDunning(RSTriggerDunningRequest request) {
		return call(triggerDunningUrl, request, RestResponse.class);
	}

	public RestResponse triggerBilling(RSTriggerBillRunRequest request) {
		return call(triggerBillingUrl, request, RestResponse.class);
	}
	
	public RSGetThreadsExecutingJobResponse getThreadsExecutingJob() {
	    return call(getThreadsExecutingJob, RSGetThreadsExecutingJobResponse.class);
	}
	
}
