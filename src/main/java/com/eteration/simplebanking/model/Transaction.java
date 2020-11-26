package com.eteration.simplebanking.model;


import java.time.Instant;

// This class is a place holder you can change the complete implementation
public abstract class Transaction {
	private Instant date;
	private Double amount;
	private String type;

    public Transaction() {
        this.date = Instant.now();
        this.amount = 0.0;
    }

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

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                '}';
    }
}
