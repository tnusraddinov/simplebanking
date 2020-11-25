package com.eteration.simplebanking.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.Instant;
import java.util.Date;

// This class is a place holder you can change the complete implementation
//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DepositTransaction.class, name = "DepositTransaction"),

        @JsonSubTypes.Type(value = WithdrawalTransaction.class, name = "WithdrawalTransaction") }
)
public abstract class Transaction {
	private Instant date;
	private Double amount;

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

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                '}';
    }
}
