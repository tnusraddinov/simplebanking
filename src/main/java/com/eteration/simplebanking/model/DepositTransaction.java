package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation
public class DepositTransaction extends Transaction {

    public DepositTransaction(Double amount) {
        super(amount);
    }

    public DepositTransaction(Integer amount) {
        super(amount.doubleValue());
    }
}
