package com.essent.testing.restassured.create_contract.helper;

import com.essent.testing.restassured.create_contract.constants.ContractStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ContractUtil {

    private static long WAIT_CONTRACT_ACTIVE = 220000;
	private static long STEP_UNTIL_CONTRACT_ACTIVE = 30000;

	public static String waitUntilStringFoundInResponse(Cookies cookie, String apiPath, String payload, ContractStatus findMe, String jsonPathFromResponse, int TIMEOUT) throws Exception {
	    String contractStatus = "";
	    int i = 0;

	    while (i < TIMEOUT) {
	    	Response response = RestAssured.given().cookies(cookie).contentType(ContentType.JSON).accept(ContentType.JSON)
	    			.when().body(payload).post(apiPath).then().statusCode(200).extract().response();

            contractStatus = new JsonPath(response.getBody().asString()).get(jsonPathFromResponse);

            if (StringUtils.isNotBlank(contractStatus) && contractStatus.contains(findMe.getContractStatus())) {
                break;
            } else {
                if(i == 0) { Thread.sleep(WAIT_CONTRACT_ACTIVE); }
                else { Thread.sleep(STEP_UNTIL_CONTRACT_ACTIVE); }
                ++i;
                if (i == TIMEOUT) { return contractStatus; }
            }
	    }

	    return contractStatus;
	}

	public static Properties loadProperties(String path) throws FileNotFoundException, IOException{

		Properties properties = new Properties();

		File file = new File(path);
		FileInputStream fileInput = new FileInputStream(file);

		properties.load(fileInput);
		fileInput.close();

		return properties;
	}

}
