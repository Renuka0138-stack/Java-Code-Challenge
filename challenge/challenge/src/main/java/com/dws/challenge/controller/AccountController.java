package com.dws.challenge.controller;


import com.dws.challenge.domain.TransferRequest;
import com.dws.challenge.service.AccountsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("/accounts")
    @RequiredArgsConstructor
    public class AccountController {
        private final AccountsService accountService;

        @PostMapping("/transfer")
        public ResponseEntity<String> transfer(@RequestBody @Valid TransferRequest request) {
            accountService.transfer(request.getAccountFromId(), request.getAccountToId(), request.getAmount());
            return ResponseEntity.ok("Transfer Successful");
        }
    }

