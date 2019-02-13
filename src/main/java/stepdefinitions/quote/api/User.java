package stepdefinitions.quote.api;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

    @JsonProperty("key")
    private String key;
    @JsonProperty("label")
    private String label;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public static void main(String [] args) throws JsonParseException, JsonMappingException, IOException {
        String json = "{ \"key\" : \"Black\", \"label\" : \"BMW\" }";
        ObjectMapper mapper = new ObjectMapper();
        User car = mapper.readValue(json, User.class);
        System.out.println(car.getKey() + " " + car.getLabel());

    }



}
