
package com.dws.challenge;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.InsufficientBalanceException;
import com.dws.challenge.repository.AccountsRepositoryInMemory;
import com.dws.challenge.service.AccountsService;
import com.dws.challenge.service.EmailNotificationService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Test
    void testTransferSuccess() {
        AccountsRepositoryInMemory accountRepository = new AccountsRepositoryInMemory();
        EmailNotificationService notificationService = mock(EmailNotificationService.class);
        AccountsService service = new AccountsService(accountRepository, notificationService);

        Account account1 = new Account("1", new BigDecimal("1000"));
        Account account2 = new Account("2", new BigDecimal("500"));
        accountRepository.createAccount(account1);
        accountRepository.createAccount(account2);

        service.transfer("1", "2", new BigDecimal("200"));

        assertEquals(new BigDecimal("800"), account1.getBalance());
        assertEquals(new BigDecimal("700"), account2.getBalance());

    }

    @Test
    void testTransferInsufficientBalance() {
        AccountsRepositoryInMemory accountRepository = new AccountsRepositoryInMemory();
        EmailNotificationService notificationService = mock(EmailNotificationService.class);
        AccountsService service = new AccountsService(accountRepository, notificationService);

        Account account1 = new Account("1", new BigDecimal("100"));
        Account account2 = new Account("2", new BigDecimal("500"));
        accountRepository.createAccount(account1);
        accountRepository.createAccount(account2);

        assertThrows(InsufficientBalanceException.class,
                () -> service.transfer("1", "2", new BigDecimal("200")));
    }
}

