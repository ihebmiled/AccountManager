package org.account.manager.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account.manager.business.execption.BusinessException;
import org.account.manager.models.Account;
import org.account.manager.models.History;
import org.account.manager.tech.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.account.manager.business.execption.BusinessErrorCode.NOT_ENOUGH_FUNDS;
import static org.account.manager.business.execption.BusinessErrorCode.UNKNOWN_ACCOUNT;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public String create() {
        Account account = new Account();
        accountRepository.save(account);
        return account.getNumber();
    }

    public Account findAccount(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new BusinessException(UNKNOWN_ACCOUNT, "the account [" + accountNumber + "] does not exist"));
    }

    public void withdraw(String accountNumber, Long amount) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new BusinessException(UNKNOWN_ACCOUNT, "the account [" + accountNumber + "] does not exist"));

        checkBalance(amount, account);

        account.withdraw(amount);
        accountRepository.save(account);
    }

    public void deposit(String accountNumber, Long amount) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new BusinessException(UNKNOWN_ACCOUNT, "the account [" + accountNumber + "] does not exist"));

        account.deposit(amount);
        accountRepository.save(account);
    }

    public List<History> retrieveHistory(String accountNumber) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new BusinessException(UNKNOWN_ACCOUNT, "the account [" + accountNumber + "] does not exist"));
        return account.getHistory();
    }

    private void checkBalance(Long amount, Account account) {
        if (amount > account.getBalance()) {
            throw new BusinessException(NOT_ENOUGH_FUNDS, "the account [number: " + account.getNumber() +
                    ", balance: " + account.getBalance() + "] does not contain enough funds");
        }
    }
}
