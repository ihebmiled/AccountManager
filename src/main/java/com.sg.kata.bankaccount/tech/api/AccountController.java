package org.account.manager.tech.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.account.manager.business.AccountService;
import org.account.manager.models.Account;
import org.account.manager.tech.api.io.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0")
@Api(value = "Manages the account operations", tags = "Admin Account")
@AllArgsConstructor
public class AccountController {

	private final AccountService accountService;
	private final AccountMapper mapper;
	private final ParamChecker checker;

	@PutMapping("/account")
	@ApiOperation(value = "Resource to create an account")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)})
	public CreateAccountResponse createAccount() {
		return mapper.toCreateAccountResponse(accountService.create());
	}

	@GetMapping("/account/{accountNumber}")
	@ApiOperation(value = "Resource to get an account")
	public AccountResponse getAccount(@PathVariable String accountNumber) {
		Account account = accountService.findAccount(accountNumber);
		return mapper.toAccountResponse(account);
	}

	@PostMapping("/account/withdraw")
	@ApiOperation(value = "Resource to perform the withdraw operation")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)})
	public @ResponseBody
	void withdraw(@RequestBody OperationRequest operationRequest) {
		checker.checkParams(operationRequest);
		float amountFloat = Float.parseFloat(operationRequest.getAmount());
		accountService.withdraw(operationRequest.getAccountNumber(), amountFloat);
	}

	@PostMapping("/account/deposit")
	@ApiOperation(value = "Resource to perform the deposit operation")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)})
	public @ResponseBody
	void deposit(@RequestBody OperationRequest operationRequest) {
		checker.checkParams(operationRequest);
		float amountFloat = Float.parseFloat(operationRequest.getAmount());
		accountService.deposit(operationRequest.getAccountNumber(), amountFloat);
	}

	@GetMapping("/account/{accountNumber}/history")
	@ApiOperation(value = "Resource to retrieve transactions history of a bank account")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)})
	public HistoryResponseList retrieveHistory(@PathVariable String accountNumber) {
		return mapper.toHistoriesResponseList(accountService.retrieveHistory(accountNumber));
	}

}
