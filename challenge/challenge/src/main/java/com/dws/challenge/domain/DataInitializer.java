package com.dws.challenge.domain;

import com.dws.challenge.repository.AccountsRepositoryInMemory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer {


        private final AccountsRepositoryInMemory accountRepository;

        public DataInitializer(AccountsRepositoryInMemory accountRepository) {
            this.accountRepository = accountRepository;
        }

        @PostConstruct
        public void init() {
            // Create test accounts
            Account account1 = new Account("1", new BigDecimal("500.0")); // ID: 1, Balance: 1000
            Account account2 = new Account("2", new BigDecimal("200.0"));  // ID: 2, Balance: 500

            // Save accounts in the repository
            accountRepository.createAccount(account1);
            accountRepository.createAccount(account2);

        }
    }


