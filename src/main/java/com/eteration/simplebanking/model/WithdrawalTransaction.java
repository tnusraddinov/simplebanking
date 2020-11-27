package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "withdrawal_transaction")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction() {
        super();
    }

    public WithdrawalTransaction(Double amount) {
        super(amount);
    }

    public WithdrawalTransaction(Integer amount) {
        super(amount.doubleValue());
    }

    public void apply(Account account) throws InsufficientBalanceException {
        account.withdraw(this.getAmount());
        this.setAccount(account);
        account.getTransactions().add(this);
    }
}


