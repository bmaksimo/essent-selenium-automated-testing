package stepdefinitions.billing.test;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.be.jbilling.api.rest.batch.RSTriggerBillRunRequest;
import com.essent.be.jbilling.api.rest.batch.RSTriggerMediationRequest;
import com.essent.be.jbilling.api.rest.batch.admin.RSGetThreadsExecutingJobResponse;
import com.essent.be.jbilling.api.rest.batch.admin.RSShowRunningJobsResponse;
import com.essent.be.jbilling.api.rest.batch.admin.RSStopRunningJobRequest;
import com.essent.be.jbilling.api.rest.batch.admin.RunningJobItem;
import com.essent.testing.client.billing.BillingBatch;
import com.essent.testing.scenario.RegisteredScenario;
import cucumber.api.Scenario;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import stepdefinitions.transformers.DateMapper;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.Assert.fail;

public class BatchSteps extends RegisteredScenario {

	// Time between calls to figure out when a job has finished
	private long POLLING_INTERVAL = 250;

    @Before("@DWP, @E2E")
    public void setupTest(Scenario scenario) throws Throwable {
        registerActiveScenario(scenario);
    }

	@When("^[E|e]xecute mediation job \"([^\"]*)\"$")
	public void execute_mediation_job(String jobName) throws InterruptedException {
		run_and_wait_for_mediation_job(jobName, null, null, null);
	}

	@When("^[E|e]xecute mediation job \"([^\"]*)\" for settlement date \"(.*?)\"$")
	public void execute_mediation_job_for_settlement_date(String jobName, @Transform(DateMapper.class) Date settlementDate) throws InterruptedException {
		run_and_wait_for_mediation_job(jobName, null, null, settlementDate);
	}

	@When("^[E|e]xecute mediation job without waiting \"([^\"]*)\" for settlement date \"(.*?)\"$")
	public void execute_mediation_job_no_wait_for_settlement_date(String jobName, @Transform(DateMapper.class) Date settlementDate) throws InterruptedException {
		run_and_do_not_wait_for_mediation_job(jobName, null, null, settlementDate, true);
	}

    @When("^Try mediation job without waiting \"([^\"]*)\" for settlement date \"(.*?)\"$")
    public void try_second_job_no_wait_for_settlement_date(String jobName, @Transform(DateMapper.class) Date settlementDate) throws InterruptedException {
        run_and_do_not_wait_for_mediation_job(jobName, null, null, settlementDate, false);
    }

    @Then("^Execute mediation job \"([^\"]*)\" for billingid \"([^\"]*)\" and settlement date \"([^\"]*)\"$")
    public void execute_mediation_job_for_billingid_and_settlement_date(String jobName, String billingid,
            @Transform(DateMapper.class) Date settlementDate) throws Throwable {
		run_and_wait_for_mediation_job(jobName, billingid, null, settlementDate);
    }

	@Given("^Execute mediation job \"([^\"]*)\" for deliverpoint \"([^\"]*)\" and settlement date \"([^\"]*)\"$")
	public void execute_mediation_job_for_deliverpoint_and_settlement_date(String jobName, String deliverypointId,
	        @Transform(DateMapper.class) Date settlementDate) throws Throwable {
		run_and_wait_for_mediation_job(jobName, null, deliverypointId, settlementDate);
	}

	@And("^[E|e]xecute billing run \"([^\"]*)\" for process date \"([^\"]*)\"$")
	public void start_bill_run_for_process_date(String jobName, @Transform(DateMapper.class) Date processDate) throws Throwable {
	    startBillRun(jobName, null, processDate, null, null, true, true);
	}

	   @And("^[E|e]xecute billing run \"([^\"]*)\" for process date \"([^\"]*)\" no invoice created$")
	    public void start_bill_run_for_process_date_no_invoice_created(String jobName, @Transform(DateMapper.class) Date processDate) throws Throwable {
	        startBillRun(jobName, null, processDate, null, null, true, false);
	    }

	@And("^Execute billing run \"([^\"]*)\" for billingUser \"([^\"]*)\" and process date \"([^\"]*)\"$")
	public void start_bill_run_for_billingUser_and_process_date(String jobName, String billingCustomerId,
			@Transform(DateMapper.class) Date processDate) {
        startBillRun(jobName, billingCustomerId, processDate, null, null, true, true);
	}

	@Then("^Execute billing run \"([^\"]*)\" for process date \"([^\"]*)\" and selectionDay \"([^\"]*)\"$")
	public void execute_billing_run_for_process_date_and_selectionDay(String jobName, @Transform(DateMapper.class) Date processDate, String selectionDay) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
        startBillRun(jobName, null, processDate, selectionDay, null, true, true);
	}

    @And("^Execute billing run \"([^\"]*)\" for process date \"([^\"]*)\" and invoice date \"([^\"]*)\"$")
    public void execute_billing_run_for_process_date_and_invoice_date(String jobName,
            @Transform(DateMapper.class) Date processDate, String invoiceDate) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        startBillRun(jobName, null, processDate, null, invoiceDate, true, true);
    }

    @And("^Execute billing run without waiting \"([^\"]*)\" for process date \"([^\"]*)\"")
    public void execute_billing_run_no_wait_for_process_date(String jobName,
    		@Transform(DateMapper.class) Date processDate) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        startBillRun(jobName, null, processDate, null, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), false, true);
    }

    @And("^Try billing run without waiting \"([^\"]*)\" for process date \"([^\"]*)\"")
    public void try_billing_run_no_wait_for_process_date(String jobName,
            @Transform(DateMapper.class) Date processDate) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        startBillRun(jobName, null, processDate, null, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), false, false);
    }

    @And("^Execute billing run \"([^\"]*)\" for billingUser \"([^\"]*)\" and process date \"([^\"]*)\" and invoice date \"([^\"]*)\"$")
    public void execute_billing_run_for_billingUser_process_date_and_invoice_date(String jobName, String billingCustomerId,
            @Transform(DateMapper.class) Date processDate, String invoiceDate) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        startBillRun(jobName, billingCustomerId, processDate, null, invoiceDate, true, true);
    }

    @And("^Terminate all running jobs")
    public void terminate_all_running_jobs() throws Throwable {
    	BillingBatch billingBatch=new BillingBatch();
		RSShowRunningJobsResponse showJobsResponse = billingBatch.showRunningJobs();
		for (RunningJobItem job:showJobsResponse.getJobs()) {
			RSStopRunningJobRequest request = new RSStopRunningJobRequest();
			request.setJobId(Long.valueOf(job.getJobId()));
			RestResponse restResponse = billingBatch.stopRunningJob(request);
			Assert.assertTrue(restResponse.getMsg(), restResponse.getResult());
		}

    }

    @And("^Terminate job \"([^\"]*)\"")
    public void terminate_job(String jobId) throws Throwable {
    	BillingBatch billingBatch=new BillingBatch();
		RSStopRunningJobRequest request = new RSStopRunningJobRequest();
		request.setJobId(Long.valueOf(Long.valueOf(jobId)));
		RestResponse restResponse = billingBatch.stopRunningJob(request);
		Assert.assertTrue(restResponse.getResult());
    }


    @And("^Wait for slave threads to start")
    public void wait_for_slave_threads_to_start() throws Throwable {

        int running = getNrThreadsExecutingJob();
        if( running < 0 ) {
            // Stand alone, we're done.
            return;
        }

        LocalTime timeOutAfter = LocalTime.now().plusSeconds(5);
        while (running == 0 && timeOutAfter.isAfter(LocalTime.now())) {
            waitMillis(POLLING_INTERVAL);
            running = getNrThreadsExecutingJob();
        }

        if (running == 0 ) {
            fail("No threads started processing job within 5 seconds");
        }

    }


    @Then("^I expect a single running job of type \"([^\"]*)\"$")
    public void i_expect_a_single_running_job_of_type(String jobType) throws Throwable {
		RSShowRunningJobsResponse restResponse = new BillingBatch().showRunningJobs();
		Assert.assertEquals(1, restResponse.getJobs().size());
		Assert.assertEquals(jobType, restResponse.getJobs().get(0).getJobName());
    }

    @Then("^I expect no running jobs")
    public void i_expect_no_running_jobs() throws Throwable {
		RSShowRunningJobsResponse restResponse = new BillingBatch().showRunningJobs();
		String jobs = "";
		for( RunningJobItem item : restResponse.getJobs() ) {
		    jobs = jobs + "[name:"+item.getJobName() + ", id:" + item.getJobId() + "] ";
		}
		Assert.assertEquals("jobsStillRunning: " + jobs, 0, restResponse.getJobs().size());
    }

    @Then("^I expect more than zero running threads")
    public void i_expect_more_than_zero_running_threads() throws Throwable {
        Assert.assertTrue("No threads processing a job", getNrThreadsExecutingJob() > 0);
    }

    @Then("^I expect no running threads")
    public void i_expect_no_running_threads() throws Throwable{
        // number of active threads can also be -1, if it is a standalone instance
        Assert.assertTrue("There were unexpected running threads", getNrThreadsExecutingJob() <= 0);
    }

    public void startBillRun(String jobName, String billingCustomerId, Date processDate, String selectionDay,
      String invoiceDateAsString, boolean wait, boolean expectSuccess)
	{
    DateTime invoiceDate;
    if (invoiceDateAsString == null) {
      invoiceDate = new DateTime(processDate).plusDays(7);

    } else {
      invoiceDate = new DateTime(invoiceDateAsString);
    }

		RSTriggerBillRunRequest request = new RSTriggerBillRunRequest();
		request.setJobName(jobName);
		request.setProcessDate(processDate);
		request.setInvoiceDate(invoiceDate.toDate());
		request.setBillingCustomerId(billingCustomerId);
		if( StringUtils.isEmpty(billingCustomerId)) {
		  // Only set selectionInvoiceDay when no customer is given.
		  if (StringUtils.isEmpty(selectionDay) ) {
		    request.setSelectionInvoiceDay(31); // everything
		  } else {
		    request.setSelectionInvoiceDay(Integer.valueOf(selectionDay));
		  }
		}
		RestResponse resp = new BillingBatch().triggerBilling(request);
		if( expectSuccess ) {
            Assert.assertTrue(resp.getMsg(), resp.getResult());
		} else {
            Assert.assertFalse("Expected start billing run to fail, but didn't fail", resp.getResult());
		}
		if (wait) {
			waitForBillingRunFinished();
		}
	}

	private void waitMillis(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Ignore
		}
	}

	private void waitForBillingRunFinished() {
			boolean running = true;
			BillingBatch batchProxy = new BillingBatch();

			while (running) {
				RestResponse resp = batchProxy.checkBillingRunning();
				running = resp.getResult();
				if( running ) {
					waitMillis(POLLING_INTERVAL);
				}
			}
	}

	public void run_and_wait_for_mediation_job(String jobName, String billingCustomerId, String deliverypointId,
			Date settlementDate) throws InterruptedException
	{
		BillingBatch mediationRun = new BillingBatch();

		RSTriggerMediationRequest request = new RSTriggerMediationRequest();
		request.setJobName(jobName);
		request.setSettlementDate(settlementDate);
		request.setBillingId(billingCustomerId);
		request.setDeliveryPointId(deliverypointId);

		RestResponse response = mediationRun.triggerMediation(request);

		Assert.assertTrue(response.getMsg(), response.getResult());

		// Now wait until it is finished
		waitForMediationRunFinished();
	}

	public void run_and_do_not_wait_for_mediation_job(String jobName, String billingCustomerId, String deliverypointId,
			Date settlementDate, boolean expectSuccess) throws InterruptedException
	{
		BillingBatch mediationRun = new BillingBatch();

		RSTriggerMediationRequest request = new RSTriggerMediationRequest();
		request.setJobName(jobName);
		request.setSettlementDate(settlementDate);
		request.setBillingId(billingCustomerId);
		request.setDeliveryPointId(deliverypointId);

		RestResponse response = mediationRun.triggerMediation(request);
		if( expectSuccess ) {
		    Assert.assertTrue(response.getMsg(), response.getResult());
		} else {
            Assert.assertFalse("job " + jobName + " started, but should have failed", response.getResult());
		}
	}


	private void waitForMediationRunFinished() {
		BillingBatch mediationRun = new BillingBatch();
		boolean running = true;

		while (running) {

			RestResponse resp = mediationRun.checkMediationRunning();
			running = resp.getResult();
			if( running ) {
				waitMillis(POLLING_INTERVAL);
			}
		}

	}

	private int getNrThreadsExecutingJob() {
        RSGetThreadsExecutingJobResponse restResponse = new BillingBatch().getThreadsExecutingJob();

        Assert.assertNotNull("No response received", restResponse);
        Assert.assertTrue(restResponse.getMsg(), restResponse.getResult());

        return restResponse.getNumberOfActiveThreads();

	}

	private void waitForAllRunsFinished() {
		int threadsRunning = getNrThreadsExecutingJob();

		// This should be null
		Assert.assertTrue("Not all slave threads were stopped when stopping job", threadsRunning <= 0);

		// We can wait forever in the thread that initiated the stop, but the job will not get stopped
		// status there. So we wait here.
		boolean jobsRunning=true;
		while (jobsRunning) {
		    RSShowRunningJobsResponse jobsResponse = new BillingBatch().showRunningJobs();
		    jobsRunning = jobsResponse.getJobs().size() !=0;
		    if( jobsRunning ) {
		        waitMillis(POLLING_INTERVAL);
		    }
		}
	}

    @After("@DWP, @E2E")
    public void afterScenario(Scenario scenario) {
        // We need to kill any pending jobs and wait until they are finished.
        if( scenario.isFailed() ) {
            logger().error("Cleaning up after failure.....");
            try {
                BillingBatch billingBatch=new BillingBatch();
                RSShowRunningJobsResponse showJobsResponse = billingBatch.showRunningJobs();
                for (RunningJobItem job:showJobsResponse.getJobs()) {
                    RSStopRunningJobRequest request = new RSStopRunningJobRequest();
                    request.setJobId(Long.valueOf(job.getJobId()));
                    RestResponse restResponse = billingBatch.stopRunningJob(request);
                    Assert.assertTrue(restResponse.getMsg(), restResponse.getResult());
                }

                waitForAllRunsFinished();
            } catch (Throwable t) {
                // if soft method failed, just wait for 60 seconds and hope for the best.
                logger().error("Cleaning up FAILED, waiting for one minute (fingers crossed)");
                waitMillis(60_000L);
            }

        }
        else {
            System.out.println("No Cleaning up needed");

        }
    }

}
