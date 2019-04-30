package stepdefinitions.billing.test;

import com.essent.be.jbilling.api.rest.dunning.DunningStepRequest;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

public class DunningService {
  private static final String BASE_URL = ConfigProvider.getProperty(ConfigKey.BILLING_BASE_URL);
  private static final String REST_URL = "/api/rest/test/dunning";
  private static final String STEP_URL = "/step";

  public Object callDunningStep(DunningStepRequest dunningStepRequest) {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("Content-Type", "application/json");
    headers.add("Accept", "text/plain");

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(Arrays.asList(TEXT_PLAIN, APPLICATION_JSON));

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(converter);

    HttpEntity<DunningStepRequest> request = new HttpEntity<>(dunningStepRequest, headers);

    return restTemplate.postForObject(BASE_URL + REST_URL + STEP_URL, request, Object.class);
  }
}
