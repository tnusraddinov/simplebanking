package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
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
    public ResponseEntity credit(
            @PathVariable("accountNumber") String accountNumber,
            @RequestBody MyTr myTr
//                HttpRequest httpRequest
            ) {
        Account account = accountService.findAccount(accountNumber);
//        if(account != null){
//            account.post(depositTransaction);
//            return ResponseEntity.ok(new TransactionStatus("OK", UUID.randomUUID().toString()));
//        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "debit/{accountNumber}")
    public ResponseEntity debit(
            @PathVariable("accountNumber") String accountNumber,
            @RequestBody WithdrawalTransaction withdrawalTransaction
            ) throws InsufficientBalanceException {
        Account account = accountService.findAccount(accountNumber);
        if(account != null){
            account.post(withdrawalTransaction);
            return ResponseEntity.ok(new TransactionStatus("OK", UUID.randomUUID().toString()));
        }
        return ResponseEntity.notFound().build();
	}
}

class MyTr{
    private Double amount;

    public MyTr(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                ", amount=" + amount +
                '}';
    }
}