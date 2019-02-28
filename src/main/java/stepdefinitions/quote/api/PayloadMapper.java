package stepdefinitions.quote.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import stepdefinitions.quote.api.model.GenericPayload;

/**
 * @author n.grkavac
 *
 */
public class PayloadMapper {

    public String createPayload() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // this is needed because we need to pass an empty model
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        GenericPayload check = new GenericPayload();
        check.setModel(new GenericPayload.Model());
        return mapper.writeValueAsString(check);
    }

}
