package com.essent.testing.client.billing;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerBillRunRequest;
import com.essent.be.jbilling.api.rest.batch.RSTriggerDunningRequest;
import com.essent.be.jbilling.api.rest.batch.RSTriggerMediationRequest;
import com.essent.be.jbilling.api.rest.batch.admin.RSGetThreadsExecutingJobResponse;
import com.essent.be.jbilling.api.rest.batch.admin.RSShowRunningJobsResponse;
import com.essent.be.jbilling.api.rest.batch.admin.RSStopRunningJobRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillingBatch extends BillingRootClient {
  private static final Logger LOG = LoggerFactory.getLogger(BillingBatch.class);

  protected static final String restUrl = "/api/rest/batch";
  protected static final String triggerMediationUrl = "/triggerMediation";
  protected static final String triggerDunningUrl = "/triggerDunning";
  protected static final String checkDunningUrl = "/dunningRunning";
  protected static final String checkMediationUrl = "/mediationRunning";
  protected static final String triggerBillingUrl = "/triggerBilling";
  protected static final String checkBillingUrl = "/billingRunning";
  protected static final String showRunningJobsUrl = "/showRunningJobs";
  protected static final String stopRunningJobUrl = "/stopRunningJob";
  protected static final String getThreadsExecutingJob = "/getThreadsExecutingJob";

  public BillingBatch() {
    super(restUrl, LOG);
  }

  public RestResponse triggerMediation(RSTriggerMediationRequest request) {
    return call(triggerMediationUrl, request, RestResponse.class);
  }

  public RestResponse checkMediationRunning() {
    return callSilently(checkMediationUrl, RestResponse.class);
  }

  public RestResponse checkDunningRunning() {
    // This is called a lot and obfuscates the log, so be quit about it.
    return callSilently(checkDunningUrl, RestResponse.class);
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
