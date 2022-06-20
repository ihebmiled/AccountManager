package org.account.manager.tech.api.io;

import org.account.manager.models.Account;
import org.account.manager.models.History;
import org.account.manager.models.Operation;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.account.manager.models.Operation.DEPOSIT;
import static org.account.manager.models.Operation.WITHDRAW;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountMapperTest {
    private final AccountMapper accountMapper = new AccountMapper();

    @Test
    public void should_return_valid_CreateAccountResponse() {
        // Given --
        String accountNumber = "sg_123456";
        // When --
        CreateAccountResponse accResponse = accountMapper.toCreateAccountResponse(accountNumber);
        // Then --
        assertThat(accResponse.getAccountNumber()).isEqualTo(accountNumber);
    }

    @Test
    public void should_return_valid_AccountResponse() {
        // Given --
        Account account = new Account();
        account.setBalance(2000f);
        account.setNumber("sg_123457");
        // When --
        AccountResponse accountResponse = accountMapper.toAccountResponse(account);
        // Then --
        assertThat(accountResponse.getBalance()).isEqualTo(2000f);
        assertThat(accountResponse.getNumber()).isEqualTo("sg_123457");
    }

    @Test
    public void should_return_valid_historyResponses() {
        // Given --
        History histo1 = getHistory(DEPOSIT, 100f, 2000f, 2100f);
        History histo2 = getHistory(WITHDRAW, 300f, 2100f, 1800f);
        List<History> historyList = Arrays.asList(histo1, histo2);
        // When --
        List<HistoryResponse> historyResponses = accountMapper.toHistoriesResponseList(historyList).getHistory();
        // Then --
        assertThat(historyResponses).isNotNull();
        assertThat(historyResponses).isNotEmpty();

        assertThat(historyResponses.get(0).getOperation()).isEqualTo("DEPOSIT");
        assertThat(historyResponses.get(0).getAmount()).isEqualTo(100f);
        assertThat(historyResponses.get(0).getPreviousBalance()).isEqualTo(2000f);
        assertThat(historyResponses.get(0).getNewBalance()).isEqualTo(2100f);

        assertThat(historyResponses.get(1).getOperation()).isEqualTo("WITHDRAW");
        assertThat(historyResponses.get(1).getAmount()).isEqualTo(300f);
        assertThat(historyResponses.get(1).getPreviousBalance()).isEqualTo(2100f);
        assertThat(historyResponses.get(1).getNewBalance()).isEqualTo(1800f);
    }

    private History getHistory(Operation operation, Float amount, Float previousBalance, Float newBalance) {
        History history = new History();
        history.setOperation(operation);
        history.setAmount(amount);
        history.setPreviousBalance(previousBalance);
        history.setNewBalance(newBalance);
        return history;
    }
}