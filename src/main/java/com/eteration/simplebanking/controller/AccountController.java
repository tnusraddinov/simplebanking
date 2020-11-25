package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

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
    public ResponseEntity credit(
            @PathVariable("accountNumber") String accountNumber,
            @RequestBody DepositTransaction depositTransaction
            ) {
        Account account = accountService.findAccount(accountNumber);
        if(account != null){
            account.post(depositTransaction);
            return ResponseEntity.ok(account);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "debit/{accountNumber}")
    public ResponseEntity debit(@PathVariable("accountNumber") String accountNumber) {
        return ResponseEntity.ok(accountNumber);
	}
}