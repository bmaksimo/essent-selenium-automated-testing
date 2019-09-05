package stepdefinitions.billing.test;

import com.essent.automation.util.Sleeper;
import com.essent.be.api.config.BillingServiceFactory;
import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerBillRunRequest;
import com.essent.be.jbilling.api.rest.batch.RSTriggerMediationRequest;
import com.essent.be.jbilling.api.rest.client.BillingBatchRestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class BillingService {

    @Autowired
    private BillingServiceFactory billingServiceFactory;
    private final static Logger log = Logger.getLogger(BillingService.class);

    public BillingRunResult startBillRun(String jobName, String billingCustomerId, Date processDate) {
        RSTriggerBillRunRequest request = new RSTriggerBillRunRequest();
        request.setJobName(jobName);
        request.setProcessDate(processDate);
        request.setInvoiceDate(new Date());
        request.setBillingCustomerId(billingCustomerId);

        int maxAttempts = 5;
        int currentAttempt = 0;

        while (currentAttempt < maxAttempts) {
            RestResponse response = billingServiceFactory.createBatchService().triggerBillingJob(request);
            if (response.getResult())
                return new BillingRunResult(response.getMsg(), response.getResult());

            log.info("Response was: " + response.getMsg() + ". Retrying billing " + jobName + " run...");
            Sleeper.sleepTightInSeconds(60);
            currentAttempt++;
        }

        RestResponse response = billingServiceFactory.createBatchService().triggerBillingJob(request);
        return new BillingRunResult(response.getMsg(), response.getResult());
    }

    public MediationRunResult runMediationJob(String jobName, String billingCustomerId, String deliverypointId, Date settlementDate) {
        BillingBatchRestService mediationRun =  billingServiceFactory.createBatchService();

        RSTriggerMediationRequest request = new RSTriggerMediationRequest();
        request.setJobName(jobName);
        request.setSettlementDate(settlementDate);
        request.setBillingId(billingCustomerId);
        request.setDeliveryPointId(deliverypointId);

        int maxAttempts = 5;
        int currentAttempt = 0;

        while (currentAttempt < maxAttempts) {
            RestResponse response = mediationRun.triggerMediationJob(request);
            if (response.getResult())
                return new MediationRunResult(response.getMsg(), response.getResult());

            log.info("Response was: " + response.getMsg() + ". Retrying mediation " + jobName + " run...");
            Sleeper.sleepTightInSeconds(60);
            currentAttempt++;
        }

        RestResponse response = mediationRun.triggerMediationJob(request);
        return new MediationRunResult(response.getMsg(), response.getResult());
    }
}
