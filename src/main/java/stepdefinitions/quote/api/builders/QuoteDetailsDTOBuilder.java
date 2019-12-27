package stepdefinitions.quote.api.builders;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.essent.testing.util.resource.ResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import stepdefinitions.quote.api.model.dto.PayloadDTO;
import stepdefinitions.quote.api.model.dto.QuoteDetailsDTO;

public class QuoteDetailsDTOBuilder {
  private static String PATH_TO_QUOTE = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_QUOTE);
  private static String PATH_TO_PAYLOAD = ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD);
  private static String PATH_TO_PAYLOAD_SUPPLIER_SWITCH =
      ConfigProvider.getProperty(ConfigKey.CRM_PATH_TO_PAYLOAD_SUPPLIER_SWITCH);
  private QuoteDetailsDTO quoteDetailsDTO;
  private ObjectMapper mapper;

  public QuoteDetailsDTOBuilder() throws IOException {
    mapper = new ObjectMapper();
    String pathToQuote = ResourceUtil.toPath(PATH_TO_QUOTE);
    String jsonQuote = new String(Files.readAllBytes(Paths.get(pathToQuote)));
    this.quoteDetailsDTO = mapper.readValue(jsonQuote, QuoteDetailsDTO.class);
  }

  public QuoteDetailsDTOBuilder withBirthdate(String dateOfBirth) {
    this.quoteDetailsDTO.getModel().setBirthdate(dateOfBirth);
    return this;
  }

  public QuoteDetailsDTOBuilder withFirstName(String firstName) {
    this.quoteDetailsDTO.getModel().setFirstName(firstName);
    return this;
  }

  public QuoteDetailsDTOBuilder withLastName(String lastName) {
    this.quoteDetailsDTO.getModel().setLastName(lastName);
    return this;
  }

  public QuoteDetailsDTOBuilder withIban(String iBan) {
    this.quoteDetailsDTO.getModel().setIban(iBan);
    return this;
  }

  public QuoteDetailsDTOBuilder withSignInDate(String signInDate) {
    this.quoteDetailsDTO.getModel().setSignDateC(signInDate);
    return this;
  }

  public QuoteDetailsDTOBuilder withGeneralChannel(String generalChannel) {
    this.quoteDetailsDTO.getModel().setGeneralChannel(generalChannel);
    return this;
  }

  public QuoteDetailsDTOBuilder withPayload(String meterOpen, String tariffSheetId, String ean)
      throws IOException {
    String path = meterOpen.equals("On") ? PATH_TO_PAYLOAD_SUPPLIER_SWITCH : PATH_TO_PAYLOAD;
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
