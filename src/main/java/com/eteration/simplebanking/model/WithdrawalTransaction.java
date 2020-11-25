package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation
public class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction(Double amount) {
        super(amount);
    }

    public WithdrawalTransaction(Integer amount) {
        super(amount.doubleValue());
    }
}


