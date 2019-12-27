package com.essent.testing.restassured.create_contract.helper;

import com.essent.automation.util.Sleeper;
import com.essent.testing.restassured.create_contract.constants.ContractStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

public class ContractUtil {

  public static String waitUntilStringFoundInResponse(
      Cookies cookie,
      String apiPath,
      String payload,
      ContractStatus expectedStatus,
      String jsonPathFromResponse,
      int maxAttempts)
      throws Exception {
    String contractStatus = "";

    int currentAttempt = 0;
    boolean found = false;

    while (!found && currentAttempt <= maxAttempts) {
      Response response =
          RestAssured.given()
              .cookies(cookie)
              .contentType(ContentType.JSON)
              .accept(ContentType.JSON)
              .when()
              .body(payload)
              .post(apiPath)
              .then()
              .statusCode(200)
              .extract()
              .response();

      contractStatus = new JsonPath(response.getBody().asString()).get(jsonPathFromResponse);
      found =
          StringUtils.isNotBlank(contractStatus)
              && contractStatus.contains(expectedStatus.getContractStatus());
      currentAttempt++;
      if (!found) Sleeper.sleepTightInSeconds(30);
    }

    return contractStatus;
  }

  public static Properties loadProperties(String path) throws FileNotFoundException, IOException {

    Properties properties = new Properties();

    File file = new File(path);
    FileInputStream fileInput = new FileInputStream(file);

    properties.load(fileInput);
    fileInput.close();

    return properties;
  }
}
