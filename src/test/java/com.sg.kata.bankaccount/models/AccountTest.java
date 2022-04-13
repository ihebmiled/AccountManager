package org.account.manager.models;

import org.junit.Test;

import static org.account.manager.models.Operation.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {
    @Test
    public void should_create_valid_Account() {
        // When --
        Account account = new Account();
        // Then --
        assertThat(account.getBalance()).isEqualTo(0f);
        assertThat(account.getNumber()).startsWith("SG_");
        assertThat(account.getHistory()).isNotEmpty();
        assertThat(account.getHistory().get(0).getOperation()).isEqualTo(CREATE);
        assertThat(account.getHistory().get(0).getAmount()).isNull();
        assertThat(account.getHistory().get(0).getPreviousBalance()).isNull();
        assertThat(account.getHistory().get(0).getNewBalance()).isEqualTo(0f);
    }

    @Test
    public void should_withdraw_from_valid_Account() {
        // When --
        Account account = new Account();
        account.setBalance(1000f);
        account.withdraw(300f);
        // Then --
        assertThat(account.getBalance()).isEqualTo(700f);
        assertThat(account.getHistory()).isNotEmpty();
        assertThat(account.getHistory().get(0).getOperation()).isEqualTo(CREATE);
        assertThat(account.getHistory().get(0).getAmount()).isNull();
        assertThat(account.getHistory().get(0).getPreviousBalance()).isNull();
        assertThat(account.getHistory().get(0).getNewBalance()).isEqualTo(0f);

        assertThat(account.getHistory().get(1).getOperation()).isEqualTo(WITHDRAW);
        assertThat(account.getHistory().get(1).getAmount()).isEqualTo(300f);
        assertThat(account.getHistory().get(1).getPreviousBalance()).isEqualTo(1000f);
        assertThat(account.getHistory().get(1).getNewBalance()).isEqualTo(700f);
    }

    @Test
    public void should_deposit_in_valid_Account() {
        // When --
        Account account = new Account();
        account.setBalance(1000f);
        account.deposit(300f);
        // Then --
        assertThat(account.getBalance()).isEqualTo(1300f);
        assertThat(account.getHistory()).isNotEmpty();
        assertThat(account.getHistory().get(0).getOperation()).isEqualTo(CREATE);
        assertThat(account.getHistory().get(0).getAmount()).isNull();
        assertThat(account.getHistory().get(0).getPreviousBalance()).isNull();
        assertThat(account.getHistory().get(0).getNewBalance()).isEqualTo(0f);

        assertThat(account.getHistory().get(1).getOperation()).isEqualTo(DEPOSIT);
        assertThat(account.getHistory().get(1).getAmount()).isEqualTo(300f);
        assertThat(account.getHistory().get(1).getPreviousBalance()).isEqualTo(1000f);
        assertThat(account.getHistory().get(1).getNewBalance()).isEqualTo(1300f);
    }
}