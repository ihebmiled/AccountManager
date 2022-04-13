package org.account.manager.tech.api.io;

import org.account.manager.models.Account;
import org.account.manager.models.History;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountMapper {
    public CreateAccountResponse toCreateAccountResponse(String accountNumber) {
        return new CreateAccountResponse(accountNumber);
    }

    public AccountResponse toAccountResponse(Account account) {
        return new AccountResponse(account.getNumber(), account.getBalance());
    }

    public HistoryResponseList toHistoriesResponseList(List<History> HistoryList) {
        return new HistoryResponseList(HistoryList.stream().map(this::toHistoryResponse).collect(Collectors.toList()));
    }

    private HistoryResponse toHistoryResponse(History history) {
        return new HistoryResponse(
                history.getAmount(),
                history.getOperation().name(),
                history.getDateTime(),
                history.getPreviousBalance(),
                history.getNewBalance());
    }
}
