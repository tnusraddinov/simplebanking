package com.eteration.simplebanking.model;


import java.time.Instant;
import java.util.Date;

// This class is a place holder you can change the complete implementation
public abstract class Transaction {
	private Instant date;
	private Double amount;

    public Transaction(Double amount) {
        this.date = Instant.now();
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                '}';
    }
}
