package org.account.manager.bdd.account;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.account.manager.bdd.ApiHelper;
import org.account.manager.bdd.BDD;
import org.account.manager.tech.api.io.*;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@BDD
@Ignore
public class AccountSteps {

    @Autowired
    private ApiHelper apiHelper;

    private CreateAccountResponse createAccountResponse;
    private ErrorResponse errorResponse;

    @Before
    public void clearContext() {
        createAccountResponse = null;
        errorResponse = null;
    }

    @When("create a new account")
    public void creatingANewAccount() {
        this.createAccountResponse = apiHelper.create();
    }

    @Then("the account should be created")
    public void theAccountShouldBeCreated() {
        assertThat(this.createAccountResponse).isNotNull();
        assertThat(this.createAccountResponse.getAccountNumber()).startsWith("SG_");
    }

    @And("the account balance should be {int}")
    public void theAccountBalanceShouldBeBALANCE(int balance) {
        AccountResponse account = apiHelper.getAccount(this.createAccountResponse.getAccountNumber());
        assertThat(account.getBalance()).isEqualTo(Float.valueOf(balance));
    }

    @When("the client makes a deposit of amount {int}")
    public void theClientMakesADepositOfAmountAMOUNT(int amount) {
        apiHelper.deposit(this.createAccountResponse.getAccountNumber(), amount);
    }

    @When("the client makes a withdrawal of amount {int}")
    public void theClientMakesAWithdrawalOfAmountAMOUNT(int amount) {
        apiHelper.withdraw(this.createAccountResponse.getAccountNumber(), amount);
    }

    @And("the account History should contain exactly {string}")
    public void theAccountHistoryShouldContainExactlyHISTORY(String histoStr) {
        List<String> historyOperationsExpected = Arrays.asList(histoStr.split(","));
        HistoryResponseList accountHistory = apiHelper.getAccountHistory(this.createAccountResponse.getAccountNumber());
        List<String> historyOperationsActual = accountHistory.getHistory().stream().map(HistoryResponse::getOperation).collect(Collectors.toList());
        assertThat(historyOperationsExpected).containsAll(historyOperationsActual);
        assertThat(historyOperationsActual).containsAll(historyOperationsExpected);
    }

    @When("the client's deposit of amount {string} is refused with status {int}")
    public void theClientMakesABadDepositOfAmountAMOUNT(String amount, int httpStatus) {
        this.errorResponse = apiHelper.depositKo(httpStatus, this.createAccountResponse.getAccountNumber(), amount);
    }

    @When("the client's withdrawal of amount {string} is refused with status {int}")
    public void theClientMakesABadWithdrawalOfAmountAMOUNT(String amount, int httpStatus) {
        this.errorResponse = apiHelper.withdrawKo(httpStatus, this.createAccountResponse.getAccountNumber(), amount);
    }

    @Then("the system should return an error with error code {string}")
    public void theSystemShouldReturnAnErrorWithHttpCodeHTTP_CODEAndErrorCode(String errorCode) {
        assertThat(this.errorResponse.getCode()).isEqualTo(errorCode);
    }

}
