package stepdefinitions.quote.api.helper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import stepdefinitions.quote.api.model.dto.FlashMessagesDTO;

import java.io.IOException;
import java.util.List;


public class ExceptionChecker {
    private final static Logger LOGGER = Logger.getLogger(RequestHelper.class);

    public boolean checkForErrorInResponse(Response response)throws JsonParseException, JsonMappingException, IOException
    {
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

    public boolean checkForErrorInFlashMessages(Response response)throws JsonParseException, JsonMappingException, IOException {

        boolean errorExists = false;
        ObjectMapper mapper = new ObjectMapper();


        String pathToWarning = "flashMessages";
        String warningFromFlashMessage;


       warningFromFlashMessage = response.jsonPath().getString(pathToWarning);
       if (warningFromFlashMessage !=null) {
           List<FlashMessagesDTO> flashMessages = mapper.readValue(warningFromFlashMessage, List.class);

           for (FlashMessagesDTO flashMessage : flashMessages) {
               if (flashMessage.getType() != null) {
                   if (flashMessage.getType().equals("ERROR")) {
                       errorExists = true;
                       LOGGER.error("ERROR: " + flashMessage.getText());
                   } else if (flashMessage.getType().equals("WARNING")) {
                       LOGGER.warn("FLASH MESSAGE:" + flashMessage.getText());
                   }
               }
           }
       }



        return errorExists;

    }
}
