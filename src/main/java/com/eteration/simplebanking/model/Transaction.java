package com.eteration.simplebanking.model;


import java.time.Instant;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
public abstract class Transaction {
	private Instant date;
	private Double amount;
	private String type;
    private String approvalCode;

    public Transaction() {
        this.type = this.getClass().getSimpleName();
        this.date = Instant.now();
        this.amount = 0.0;
        this.approvalCode = UUID.randomUUID().toString();
    }

    public Transaction(Double amount) {
        this();
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
        return type;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                '}';
    }
}
