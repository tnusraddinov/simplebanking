package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private Instant createDate;
    private String owner;
    private String accountNumber;
    private Double balance;
    private List<Transaction> transactions;

    public Account(String owner, String accountNumber) {
        this.createDate = Instant.now();
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(Double amount){
        this.balance += amount;
    }

    public void deposit(Integer amount){
        this.balance += amount.doubleValue();
    }

    public void withdraw(Double amount) throws InsufficientBalanceException {
        if(this.balance >= amount){
            this.balance -= amount;
        } else {
            throw new InsufficientBalanceException("InsufficientBalance");
        }
    }
    public void withdraw(Integer amount) throws InsufficientBalanceException {
        this.withdraw(amount.doubleValue());
    }

    public void post(DepositTransaction transaction){
        this.deposit(transaction.getAmount());
        this.transactions.add(transaction);
    }

    public void post(WithdrawalTransaction transaction) throws InsufficientBalanceException {
        this.withdraw(transaction.getAmount());
        this.transactions.add(transaction);
    }

}
