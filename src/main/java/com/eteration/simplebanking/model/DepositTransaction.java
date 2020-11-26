package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation
public class DepositTransaction extends Transaction {

    public DepositTransaction() {
    }

    public DepositTransaction(Double amount) {
        super(amount);
    }

    public DepositTransaction(Integer amount) {
        super(amount.doubleValue());
    }

    public void apply(Account account) {
        account.deposit(this.getAmount());
        account.getTransactions().add(this);
    }
}
