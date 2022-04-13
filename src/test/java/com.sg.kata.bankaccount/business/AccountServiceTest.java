package org.account.manager.business;

import org.account.manager.business.execption.BusinessException;
import org.account.manager.models.Account;
import org.account.manager.models.History;
import org.account.manager.tech.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.account.manager.models.Operation.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    // findAccount --
    @Test(expected = BusinessException.class)
    public void should_throw_BusinessException_when_calling_get_non_existing_account() {
        String accountNumber = "SG_123456";
        Mockito.when(accountRepository.findByNumber(accountNumber)).thenReturn(Optional.empty());
        accountService.findAccount(accountNumber);
    }

    // deposit --
    @Test(expected = BusinessException.class)
    public void should_throw_BusinessException_when_deposit_in_non_existing_account() {
        String accountNumber = "SG_123456";
        Mockito.when(accountRepository.findByNumber(accountNumber)).thenReturn(Optional.empty());
        accountService.deposit(accountNumber, 152f);
    }

    @Test
    public void should_deposit_in_valid_account() {
        String accountNumber = "SG_123456";
        Account account = new Account();
        account.setBalance(100f);
        Optional<Account> accountOp = Optional.of(account);
        Mockito.when(accountRepository.findByNumber(accountNumber)).thenReturn(accountOp);
        accountService.deposit(accountNumber, 101f);

        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getNumber()).startsWith("SG_");
        assertThat(argumentCaptor.getValue().getBalance()).isEqualTo(201f);
        assertThat(argumentCaptor.getValue().getHistory()).isNotEmpty();
        assertThat(argumentCaptor.getValue().getHistory().get(1).getNewBalance()).isEqualTo(201f);
        assertThat(argumentCaptor.getValue().getHistory().get(1).getPreviousBalance()).isEqualTo(100f);
        assertThat(argumentCaptor.getValue().getHistory().get(1).getAmount()).isEqualTo(101f);
        assertThat(argumentCaptor.getValue().getHistory().get(1).getOperation()).isEqualTo(DEPOSIT);
    }

    // withdraw --
    @Test(expected = BusinessException.class)
    public void should_throw_BusinessException_when_withdraw_from_non_existing_account() {
        String accountNumber = "SG_123456";
        Mockito.when(accountRepository.findByNumber(accountNumber)).thenReturn(Optional.empty());
        accountService.withdraw(accountNumber, 152f);
    }

    @Test
    public void should_withdraw_from_valid_account() {
        String accountNumber = "SG_123456";
        Account account = new Account();
        account.setBalance(100f);
        Optional<Account> accountOp = Optional.of(account);
        Mockito.when(accountRepository.findByNumber(accountNumber)).thenReturn(accountOp);
        accountService.withdraw(accountNumber, 30f);

        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getNumber()).startsWith("SG_");
        assertThat(argumentCaptor.getValue().getBalance()).isEqualTo(70f);
        assertThat(argumentCaptor.getValue().getHistory()).isNotEmpty();
        assertThat(argumentCaptor.getValue().getHistory().get(1).getNewBalance()).isEqualTo(70f);
        assertThat(argumentCaptor.getValue().getHistory().get(1).getPreviousBalance()).isEqualTo(100f);
        assertThat(argumentCaptor.getValue().getHistory().get(1).getAmount()).isEqualTo(30f);
        assertThat(argumentCaptor.getValue().getHistory().get(1).getOperation()).isEqualTo(WITHDRAW);
    }

    // retrieveHistory --
    @Test(expected = BusinessException.class)
    public void should_throw_BusinessException_when_retrieveHistory_of_non_existing_account() {
        String accountNumber = "SG_123456";
        Mockito.when(accountRepository.findByNumber(accountNumber)).thenReturn(Optional.empty());
        accountService.retrieveHistory(accountNumber);
    }

    @Test
    public void should_get_history_of_valid_account() {
        String accountNumber = "SG_123456";
        Account account = new Account();
        account.setBalance(100f);
        Optional<Account> accountOp = Optional.of(account);
        Mockito.when(accountRepository.findByNumber(accountNumber)).thenReturn(accountOp);
        List<History> histories = accountService.retrieveHistory(accountNumber);
        assertThat(histories).hasSize(1); // create

        assertThat(histories.get(0).getNewBalance()).isEqualTo(0f);
        assertThat(histories.get(0).getPreviousBalance()).isNull();
        assertThat(histories.get(0).getAmount()).isNull();
        assertThat(histories.get(0).getOperation()).isEqualTo(CREATE);
    }
}