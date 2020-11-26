package com.eteration.simplebanking.services;


import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.DepositTransactionRepository;
import com.eteration.simplebanking.repository.WithdrawalTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// This class is a place holder you can change the complete implementation
@Service
public class AccountService {

    AccountRepository accountRepository;
    DepositTransactionRepository depositTransactionRepository;
    WithdrawalTransactionRepository withdrawalTransactionRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, DepositTransactionRepository depositTransactionRepository, WithdrawalTransactionRepository withdrawalTransactionRepository) {
        this.accountRepository = accountRepository;
        this.depositTransactionRepository = depositTransactionRepository;
        this.withdrawalTransactionRepository = withdrawalTransactionRepository;
    }

    public Account findAccount(String accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber);
        return account;
    }

    public TransactionStatus credit(String accountNumber, DepositTransaction transaction) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if(account != null){
//            account.post(transaction);
            transaction.apply(account);
            depositTransactionRepository.save(transaction);
            return new TransactionStatus("OK", transaction.getApprovalCode());
        }
        return null;
    }

    public TransactionStatus debit (String accountNumber, WithdrawalTransaction transaction) throws InsufficientBalanceException {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if(account != null){
//            account.post(transaction);
            transaction.apply(account);
            withdrawalTransactionRepository.save(transaction);
            return new TransactionStatus("OK", transaction.getApprovalCode());
        }
        return null;
    }
}
