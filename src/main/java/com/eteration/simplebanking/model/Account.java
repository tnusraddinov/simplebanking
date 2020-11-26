package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long accountId;

    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    @Column(name = "create_date")
    private Date createDate;

    @NotNull
    private String owner;

    @NotNull
    @Column(name = "account_number")
    private String accountNumber;

    @NotNull
    private Double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Account() {
        this.createDate = new Date();
        this.owner = "";
        this.accountNumber = "";
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public Account(String owner, String accountNumber) {
        this();
        this.owner = owner;
        this.accountNumber = accountNumber;
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

    public Date getCreateDate() {
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
