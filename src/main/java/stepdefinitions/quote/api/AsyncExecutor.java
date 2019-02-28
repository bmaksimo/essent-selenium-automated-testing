package stepdefinitions.quote.api;

import java.util.concurrent.Callable;

import io.restassured.http.Cookies;
import stepdefinitions.quote.api.model.ContractDetails;

/**
 * @author n.grkavac
 *
 */
public class AsyncExecutor {

    public static Callable<Boolean> isStatusSuccessfull(Cookies cookie, ContractDetails contractDetails) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return new ContractDetailsAPI().getContractStatus(cookie, contractDetails.getContractRecordId());
            }
        };
    }


}
