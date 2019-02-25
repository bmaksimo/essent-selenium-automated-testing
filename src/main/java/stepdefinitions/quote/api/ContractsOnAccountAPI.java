package stepdefinitions.quote.api;

import org.apache.log4j.Logger;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import stepdefinitions.quote.api.model.ContractDetails;
import stepdefinitions.quote.api.model.ContractsOnAccount;

public class ContractsOnAccountAPI extends AbstractAPI {

    private final static Logger LOGGER = Logger.getLogger(ContractsOnAccountAPI.class);
    private static String ean = ConfigProvider.getProperty(ConfigKey.EAN_NUMBER);

    public ContractDetails getContractDetails(Cookies cookie, String recordId) throws JsonProcessingException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_CONTRACTS_ON_ACCOUNT_URL);
        String payload = createContractPayload(recordId);

        Response contractResponse =  helper.postRequest(STATUS_OK, cookie, payload, path);

        ContractDetails contractDetails = new ContractDetails();

        if (contractResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Contract created");
            String contractRecordId = contractResponse.jsonPath().getString("data.rows[0].id");
            contractDetails.setContractRecordId(contractRecordId);
            LOGGER.info("Contract record ID: " + contractRecordId);
            String contractNumber = contractResponse.jsonPath().getString("data.rows[0].cells[3].options.line1");
            contractDetails.setContractNumber(contractNumber);
            LOGGER.info("Contract number: " + contractNumber);
            String aosProductsId = contractResponse.jsonPath().getString("data.rows[0].cells[5].options.params.recordId");
            contractDetails.setAosProductsId(aosProductsId);
            LOGGER.info("Aos Products ID: " + aosProductsId);

        } else {
            LOGGER.error("Cannot create contract");
        }

        return contractDetails;

    }

    public boolean checkIfEanExists(Cookies cookie, String recordId) throws JsonProcessingException {
        RequestHelper helper = new RequestHelper();
        String path = ConfigProvider.getProperty(ConfigKey.CRM_BASE_URI) + ConfigProvider.getProperty(ConfigKey.CRM_CONTRACTED_EANS_ON_ACCOUNT_URL);
        String payload = createContractPayload(recordId);

        Response statusResponse =  helper.postRequest(STATUS_OK, cookie, payload, path);

        boolean eanExists = false;

        if (statusResponse.getStatusCode() == STATUS_OK) {
            LOGGER.info("Quotelines retrieved");
            eanExists = statusResponse.jsonPath().getString("data.rows[0].rowData.ean_c").contains(ean);
            LOGGER.info("EAN: " + ean + " exists in Quotelines: " + eanExists);
        } else {
            LOGGER.error("Cannot retrieve quotelines");
        }

        return eanExists;

    }

    private String createContractPayload(String recordId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        ContractsOnAccount contracts = new ContractsOnAccount();
        contracts.setRecordId(recordId);
        contracts.setPage(1);

        return mapper.writeValueAsString(contracts);
    }

}
