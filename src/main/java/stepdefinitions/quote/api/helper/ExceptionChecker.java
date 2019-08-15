package stepdefinitions.quote.api.helper;


import io.restassured.response.Response;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Map;


public class ExceptionChecker {
    private final static Logger LOGGER = Logger.getLogger(RequestHelper.class);

    public boolean checkForErrorInResponse(Response response){
        boolean errorExists = false;
        String pathToError = "data.arguments.errors";
        String errorFromResponse;



        errorFromResponse = response.jsonPath().getString(pathToError);

        if (errorFromResponse != null){
            LOGGER.error("ERROR: " + errorFromResponse);
            errorExists = true;
        }

        if (checkForErrorInFlashMessages(response)){
            errorExists = true;
        }
        return errorExists;
    }


        public boolean checkForErrorInFlashMessages(Response response){

            boolean errorExists = false;

            String pathToWarning = "flashMessages";

            List<Map<String, String>> warningFromFlashMessage = response.jsonPath().getList(pathToWarning);

            if (warningFromFlashMessage !=null) {

                for (Map<String, String> warningMap : warningFromFlashMessage) {
                    if (warningMap != null) {
                        for (Map.Entry<String, String> entry : warningMap.entrySet()) {
                            if (entry.getKey().equals("type") && entry.getValue().equals("ERROR")) {
                                errorExists = true;
                                LOGGER.error("ERROR: " + warningMap.get("text"));
                            } else if (entry.getKey().equals("type") && entry.getValue().equals("WARNING")) {
                                LOGGER.warn("FLASH MESSAGE:" + warningMap.get("text"));
                            }
                        }
                    }
                }
            }

            return errorExists;

        }
}
