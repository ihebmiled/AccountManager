package org.account.manager.bdd;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.restassured.response.Response;
import org.account.manager.tech.api.io.*;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
public class ApiHelper {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final int port;

    public ApiHelper() {
        this.port = 55142;
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
                .registerModule(new Jdk8Module())
                .findAndRegisterModules();
    }

    public CreateAccountResponse create() {
        // call the ws
        Response response = given().when()
                .header("Content-Type", "application/json")
                .put("http://localhost:" + port + "/api/1.0/account");
        // assert the status
        response.then()
                .assertThat()
                .statusCode(200);
        // get the response body
        String json = response.body().asString();
        // build the response bean and return
        return readbean(json, CreateAccountResponse.class);
    }

    public void deposit(String accountNumber, int amount) {
        // call the ws
        OperationRequest request = new OperationRequest(accountNumber, String.valueOf(amount));
        Response response = given().when()
                .header("Content-Type", "application/json")
                .body(request)
                .post("http://localhost:" + port + "/api/1.0/account/deposit");
        // assert the status
        response.then()
                .assertThat()
                .statusCode(200);
    }

    public ErrorResponse depositKo(int httpStatus, String accountNumber, String amount) {
        // call the ws
        OperationRequest request = new OperationRequest(accountNumber, amount);
        Response response = given().when()
                .header("Content-Type", "application/json")
                .body(request)
                .post("http://localhost:" + port + "/api/1.0/account/deposit");
        // assert the status
        response.then()
                .assertThat()
                .statusCode(httpStatus);
        // get the response body
        String json = response.body().asString();
        // build the response bean and return
        return readbean(json, ErrorResponse.class);
    }

    public void withdraw(String accountNumber, int amount) {
        // call the ws
        OperationRequest request = new OperationRequest(accountNumber, String.valueOf(amount));
        Response response = given().when()
                .header("Content-Type", "application/json")
                .body(request)
                .post("http://localhost:" + port + "/api/1.0/account/withdraw");
        // assert the status
        response.then()
                .assertThat()
                .statusCode(200);
    }

    public ErrorResponse withdrawKo(int httpStatus, String accountNumber, String amount) {
        // call the ws
        OperationRequest request = new OperationRequest(accountNumber, amount);
        Response response = given().when()
                .header("Content-Type", "application/json")
                .body(request)
                .post("http://localhost:" + port + "/api/1.0/account/withdraw");
        // assert the status
        response.then()
                .assertThat()
                .statusCode(httpStatus);
        // get the response body
        String json = response.body().asString();
        // build the response bean and return
        return readbean(json, ErrorResponse.class);
    }

    public AccountResponse getAccount(String accountNumber) {
        // call the ws
        Response response = given().when()
                .header("Content-Type", "application/json")
                .get("http://localhost:" + port + "/api/1.0/account/" + accountNumber);
        // assert the status
        response.then()
                .assertThat()
                .statusCode(200);
        // get the response body
        String json = response.body().asString();
        // build the response bean and return
        return readbean(json, AccountResponse.class);
    }

    public HistoryResponseList getAccountHistory(String accountNumber) {
        // call the ws
        Response response = given().when()
                .header("Content-Type", "application/json")
                .get("http://localhost:" + port + "/api/1.0/account/" + accountNumber + "/history");
        // assert the status
        response.then()
                .assertThat()
                .statusCode(200);
        // get the response body
        String json = response.body().asString();
        // build the response bean and return
        return readbean(json, HistoryResponseList.class);
    }

    private <T> T readbean(String json, Class<T> clazz) {
        T response;
        try {
            response = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Unable to unserialize json=" + json + " to class " + clazz.getSimpleName(), e);
        }
        return response;
    }
}
