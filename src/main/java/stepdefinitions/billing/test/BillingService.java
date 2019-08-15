package stepdefinitions.billing.test;

import com.essent.be.api.config.BillingServiceFactory;
import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerBillRunRequest;
import com.essent.be.jbilling.api.rest.batch.RSTriggerMediationRequest;
import com.essent.be.jbilling.api.rest.client.BillingBatchRestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class BillingService {

    @Autowired
    private BillingServiceFactory billingServiceFactory;

    public BillingRunResult startBillRun(String jobName, String billingCustomerId, Date processDate) {

        RSTriggerBillRunRequest request = new RSTriggerBillRunRequest();
        request.setJobName(jobName);
        request.setProcessDate(processDate);
        request.setInvoiceDate(new Date());
        request.setBillingCustomerId(billingCustomerId);

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

        RestResponse response = mediationRun.triggerMediationJob(request);

        return new MediationRunResult(response.getMsg(), response.getResult());
    }
}
