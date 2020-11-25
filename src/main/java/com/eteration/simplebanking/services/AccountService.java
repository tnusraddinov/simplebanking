package com.eteration.simplebanking.services;


import com.eteration.simplebanking.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// This class is a place holder you can change the complete implementation
@Service
public class AccountService {

    List<Account> accounts = new ArrayList<>();

    public AccountService() {
        this.accounts.add(new Account("Kerem Karaca", "17892"));
    }

    public Account findAccount(String accountNumber){
        Account foundAccount = this.accounts
                .stream()
                .filter(account -> {
                    return account.getAccountNumber().equals(accountNumber);
                }).findFirst().orElse(null);
        return foundAccount;
    }
}
