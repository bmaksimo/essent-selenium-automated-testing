package stepdefinitions.quote.api.helper;

import io.restassured.response.Response;
import org.apache.log4j.Logger;


public class ExceptionChecker {
    private final static Logger LOGGER = Logger.getLogger(RequestHelper.class);

    public boolean checkForErrorInResponse(Response response){
        boolean errorExists = false;
        String pathToError = "data.arguments.errors";
        String errorFromResponse;

        checkForWarningInResponse(response);

        errorFromResponse = response.jsonPath().getString(pathToError);

        if (errorFromResponse !=null){
            LOGGER.error("ERROR: " + errorFromResponse);
            errorExists = true;
        }

        return errorExists;
    }

    public void checkForWarningInResponse(Response response) {

        String pathToWarning = "flashMessages";
        String warningFromResponse;


       warningFromResponse = response.jsonPath().getString(pathToWarning);
       if (warningFromResponse != null) {
           LOGGER.warn("FLASH MESSAGE:" + warningFromResponse);
       }

    }
}
