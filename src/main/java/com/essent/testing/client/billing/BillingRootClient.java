package com.essent.testing.client.billing;

import com.essent.be.jbilling.api.rest.RestResponse;
import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BillingRootClient {

    // This can be overruled by tests using a setter, hence not final.
    protected String baseUrl = ConfigProvider.getProperty(ConfigKey.BILLING_BASE_URL);

	private final Logger logger;

	protected final String restUrl;

	protected static RestTemplate restTemplate = null;
	
	protected static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public BillingRootClient(String restUrl, Logger logger) {
		this.restUrl = restUrl;
		this.logger = logger;
	}

	public String getBaseUrl() {
		logger.info("Using base url : " + baseUrl);
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public RestTemplate createRestTemplate() {

		if (restTemplate == null) {
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
			messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
			messageConverters.add(new MappingJackson2HttpMessageConverter());

			restTemplate = new RestTemplate();
			restTemplate.setMessageConverters(messageConverters);
		}
		return restTemplate;
	}

	/**
	 * Call the REST service and return the result. Exceptions are handled here, but null is
	 * returned when that happens.
	 *
	 * the RestURL must have been set before this method can be succesfully called.
	 *
	 * @param methodUrl the url of the method (without the restUrl). Should start with '/'
	 * @param request the payload of the request
	 * @param responseClass the class of the response type
	 * @return the response or null on failure.
	 */
	protected <T extends RestResponse> T call(String methodUrl, Object request, Class<T> responseClass) {
		if (restUrl == null) {
			throw new AssertionError("BillingRootClient.call invoked, but restUrl not set, fix class " + this.getClass().getName()
			        + " to use the BillingRootClient(String,Logger) ctor");
		}

		String url = baseUrl + restUrl + methodUrl;
		try {
			long startedAt = System.currentTimeMillis();
			RestTemplate template = createRestTemplate();
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(request, getStandardTracingHeaders());
			T result = template.postForObject(url, httpEntity, responseClass);
			logResult(url, result, startedAt);
			return result;
		} catch (Exception e) {
			logger.info("Error calling rest url : " + url);
			logger.info(e.getMessage());
			throw new AssertionError("Failed to call JBilling, url='" + url + "', exception="+e.getMessage());
		}

	}

	protected MultiValueMap<String,String> getStandardTracingHeaders() {
	    MultiValueMap<String,String> params = new LinkedMultiValueMap<String,String>();
	    params.set("X-LOG-ID", UUID.randomUUID().toString());
	    params.set("X-LOG-COMPONENT","gherkin");
	    params.set("X-LOG-USER", "gherkintest");
	    return params;
	}

    private <T extends RestResponse> T call(String methodUrl, Class<T> responseClass, boolean verbose) {
		return call(methodUrl, null,responseClass, verbose);
	}


	private <T extends RestResponse> T call(String methodUrl, MultiValueMap<String, String> queryParams,  Class<T> responseClass, boolean verbose) {
		if (restUrl == null) {
			throw new AssertionError("BillingRootClient.call invoked, but restUrl not set, fix class " + this.getClass().getName()
			        + " to use the BillingRootClient(String,Logger) ctor");
		}
	
		String url = baseUrl + restUrl + methodUrl;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if( queryParams != null && !queryParams.isEmpty()){
			builder.queryParams(queryParams);
		}	
		
		try {
			long startedAt = System.currentTimeMillis();
			RestTemplate template = createRestTemplate();
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(getStandardTracingHeaders());
			ResponseEntity<T> response = template.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, responseClass);
			T result = response.getBody();
			if( verbose ) {
				logResult(url, result, startedAt);
			}
			return result;

		} catch (Exception e) {
			logger.info("Error calling rest url : " + url);
			logger.info(e.getMessage());
			throw new AssertionError("Failed to call JBilling, url='" + url + "', excpetion="+e.getMessage());
		}
	}
	
	public <T extends RestResponse> T callWithStringPayload(String methodUrl, String request, Class<T> responseClass) {

        String url = baseUrl + restUrl + methodUrl;
        try {

            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

            // Convertor for converting the outgoing message, this is a string.
            MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
            List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
            supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);    
            jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
            messageConverters.add(jsonConverter);

            // Convertor for converting the response, application/xml as usual
            Jaxb2RootElementHttpMessageConverter jaxbConverter = new Jaxb2RootElementHttpMessageConverter();
            supportedMediaTypes = new ArrayList<MediaType>();
            supportedMediaTypes.add(MediaType.APPLICATION_XML);

            jaxbConverter.setSupportedMediaTypes(supportedMediaTypes);
            messageConverters.add(jaxbConverter);

            // Now do the call.
            RestTemplate template = new RestTemplate();

            template.setMessageConverters(messageConverters);

            HttpEntity<Object> httpEntity = new HttpEntity<Object>(request, getStandardTracingHeaders());
            
            long startedAt = System.currentTimeMillis();
            T result = template.postForObject(url, httpEntity, responseClass);
            logResult(url, result, startedAt);
            return result;

        }
        catch (Exception e) {
            logger.info("Error calling rest url : " + url);
            logger.info(e.getMessage());
            throw new AssertionError("Failed to call JBilling, url='" + url + "', excpetion="+e.getMessage());
        }

	}
	
	public <T extends RestResponse> T callWithQueryParams(String methodUrl, MultiValueMap<String, String> queryParams, Class<T> responseClass) {
		return call(methodUrl, queryParams, responseClass, true);
	}
	
	public <T extends RestResponse> T call(String methodUrl, Class<T> responseClass) {
		return call(methodUrl, responseClass, true);
	}

	public <T extends RestResponse> T callSilently(String methodUrl, Class<T> responseClass) {
		return call(methodUrl, responseClass, false);
	}


	protected <T extends RestResponse> void logResult(String url, T result, long startedAt) {
		long duration = System.currentTimeMillis() - startedAt;
		String time = LocalDateTime.now().format(timeFormatter);
		if (result.getResult()) {
			String msg = result.getMsg();
			if (msg != null) {
				logger.info(time + ": Succesful call in " + duration + " ms to " + url + ", message is " + result.getMsg());
			} else {
				logger.info(time + ": Succesful call in " + duration + " ms to " + url);
			}
		} else {
			logger.info(time + ": FAILED call in  " + duration + " ms to " + url + ", message is " + result.getMsg());
		}
	}

}
