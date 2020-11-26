package com.eteration.simplebanking.model;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

// This class is a place holder you can change the complete implementation
@Entity
@Table(name = "deposit_transactions")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
        this.setAccount(account);
        account.getTransactions().add(this);
    }
}
