package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.time.Instant;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
@RequestMapping("account/v1")
@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "{accountNumber}")
    public ResponseEntity getAccount(@PathVariable("accountNumber") String accountNumber) {
        Account account = accountService.findAccount(accountNumber);
        if(account != null){
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "credit/{accountNumber}")
    public ResponseEntity credit( @PathVariable("accountNumber") String accountNumber,
            @RequestBody DepositTransaction depositTransaction
            ) {
        Account account = accountService.findAccount(accountNumber);
        if(account != null){
            account.post(depositTransaction);
            return ResponseEntity.ok(new TransactionStatus("OK", UUID.randomUUID().toString()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "debit/{accountNumber}")
    public ResponseEntity debit(
            @PathVariable("accountNumber") String accountNumber,
            @RequestBody WithdrawalTransaction withdrawalTransaction
            ) {
        Account account = accountService.findAccount(accountNumber);
        if(account != null){

            try {
                account.post(withdrawalTransaction);
            } catch (InsufficientBalanceException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

            return ResponseEntity.ok(new TransactionStatus("OK", UUID.randomUUID().toString()));
        }
        return ResponseEntity.notFound().build();
	}
}
