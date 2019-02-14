package stepdefinitions.quote.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayloadWrapper {

    @JsonProperty("c0f94c2f-72e0-51b9-ce93-58930799ecf1")
    public Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }



}
