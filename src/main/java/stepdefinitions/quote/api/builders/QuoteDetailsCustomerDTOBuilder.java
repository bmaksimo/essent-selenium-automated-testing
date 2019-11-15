package stepdefinitions.quote.api.builders;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import stepdefinitions.quote.api.model.dto.PayloadDTO;
import stepdefinitions.quote.api.model.dto.QuoteDetailsDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QuoteDetailsCustomerDTOBuilder {
    private static String PATH_TO_CUSTOMER_QUOTE = ConfigProvider.getProperty(ConfigKey. CRM_PATH_TO_QUOTE_CUSTOMER);
    private static String PATH_TO_PAYLOAD = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD);
    private static String PATH_TO_PAYLOAD_CUSTOMER = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD_CUSTOMER);
    private QuoteDetailsDTO quoteDetailsDTO;
    private ObjectMapper mapper;

    public QuoteDetailsCustomerDTOBuilder withBirthdate(String dateOfBirth) {
        this.quoteDetailsDTO.getModel().setBirthdate(dateOfBirth);
        return this;
    }

    public QuoteDetailsCustomerDTOBuilder withFirstName(String firstName) {
        this.quoteDetailsDTO.getModel().setFirstName(firstName);
        return this;
    }

    public QuoteDetailsCustomerDTOBuilder withLastName(String lastName) {
        this.quoteDetailsDTO.getModel().setLastName(lastName);
        return this;
    }

    public QuoteDetailsCustomerDTOBuilder withIban(String iBan) {
        this.quoteDetailsDTO.getModel().setIban(iBan);
        return this;
    }

    public QuoteDetailsCustomerDTOBuilder withSignInDate(String signInDate) {
        this.quoteDetailsDTO.getModel().setSignDateC(signInDate);
        return this;
    }

    public QuoteDetailsCustomerDTOBuilder withGeneralChannel(String generalChannel) {
        this.quoteDetailsDTO.getModel().setGeneralChannel(generalChannel);
        return this;
    }

    public QuoteDetailsCustomerDTOBuilder() throws IOException {
        mapper = new ObjectMapper();
        String pathToQuote = ResourceUtil.toPath(PATH_TO_CUSTOMER_QUOTE);
        String jsonQuote = new String(Files.readAllBytes(Paths.get(pathToQuote)));
        this.quoteDetailsDTO = mapper.readValue(jsonQuote, QuoteDetailsDTO.class);
    }

    public QuoteDetailsCustomerDTOBuilder withPayloadCustomer(String area,String tariffSheetId, String ean) throws IOException {
        String path = area.equals("Wallonia") ? PATH_TO_PAYLOAD_CUSTOMER : PATH_TO_PAYLOAD;
        String jsonPayload = new String(Files.readAllBytes(Paths.get(ResourceUtil.toPath(path))));
        PayloadDTO payload = mapper.readValue(jsonPayload, PayloadDTO.class);
        payload.setTariffsheetId(tariffSheetId);
        payload.setEan(ean);
        this.quoteDetailsDTO.getModel().getPayloadWrapper().setPayload(payload);
        return this;
    }

    public QuoteDetailsDTO build() {
        return this.quoteDetailsDTO;
    }
}
