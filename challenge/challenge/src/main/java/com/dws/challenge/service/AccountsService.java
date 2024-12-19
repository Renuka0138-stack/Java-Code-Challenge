package com.dws.challenge.service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.InsufficientBalanceException;
import com.dws.challenge.repository.AccountsRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  private final NotificationService notificationService;

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }


  public synchronized void transfer(String fromId, String toId, BigDecimal amount) {
    if (fromId.equals(toId)) {
      throw new DuplicateAccountIdException("Cannot transfer to the same account");
    }

    Account fromAccount = accountsRepository.getAccount(fromId);
    Account toAccount = accountsRepository.getAccount(toId);

    if (fromAccount == null || toAccount == null) {
      throw new IllegalArgumentException("Invalid account IDs");
    }

    if (fromAccount.getBalance().compareTo(amount) < 0) {
      throw new InsufficientBalanceException("Insufficient balance in account " + fromId);
    }

    synchronized (fromAccount) {
      synchronized (toAccount) {
        fromAccount.debit(amount);
        toAccount.credit(amount);
      }
    }

    notificationService.notifyAboutTransfer(fromAccount, "Debited: " + amount);
    notificationService.notifyAboutTransfer(toAccount, "Credited: " + amount);
  }
}
