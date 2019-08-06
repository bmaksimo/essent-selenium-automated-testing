package stepdefinitions.billing.test;

import com.essent.be.api.config.RestServiceFactory;
import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerBillRunRequest;
import com.essent.be.jbilling.api.rest.batch.RSTriggerMediationRequest;
import com.essent.be.jbilling.api.rest.client.BillingBatchRestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class BillingService {

    @Autowired
    private RestServiceFactory billingServiceFactory;

    public RestResponse startBillRun(String jobName, String billingCustomerId, Date processDate, String selectionDay, boolean wait, boolean expectSuccess) {

        RSTriggerBillRunRequest request = new RSTriggerBillRunRequest();
        request.setJobName(jobName);
        request.setProcessDate(processDate);
        request.setInvoiceDate(new Date());
        request.setBillingCustomerId(billingCustomerId);

        return billingServiceFactory.createBatchService().triggerBillingJob(request);
    }

    public RestResponse runMediationJob(String jobName, String billingCustomerId, String deliverypointId, Date settlementDate) throws InterruptedException {
        BillingBatchRestService mediationRun =  billingServiceFactory.createBatchService();

        RSTriggerMediationRequest request = new RSTriggerMediationRequest();
        request.setJobName(jobName);
        request.setSettlementDate(settlementDate);
        request.setBillingId(billingCustomerId);
        request.setDeliveryPointId(deliverypointId);

        return mediationRun.triggerMediationJob(request);
    }
}
