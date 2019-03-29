package stepdefinitions.quote.api.helper;

import io.restassured.http.Cookies;
import stepdefinitions.quote.api.ContractDetailsAPI;
import stepdefinitions.quote.api.model.ContractDetails;
import stepdefinitions.quote.api.model.QuoteDetails;

import java.util.concurrent.Callable;

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

    public static Callable<Boolean> isOrderCreated(Cookies cookie, QuoteDetails quoteDetails, ContractDetails contractDetails) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return new ContractDetailsAPI().getOrderDetails(cookie, quoteDetails, contractDetails);
            }
        };
    }


}
