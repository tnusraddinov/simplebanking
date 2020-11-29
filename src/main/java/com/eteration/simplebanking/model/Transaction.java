package com.eteration.simplebanking.model;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@MappedSuperclass
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private Long transactionId;

    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date date;

    @NotNull
    private Double amount;

    @NotNull
    private String type;

    @NotNull
    private String approvalCode;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

    public Transaction() {
        this.type = this.getClass().getSimpleName();
        this.date = new Date();
        this.amount = 0.0;
        this.approvalCode = UUID.randomUUID().toString();
    }

    public Transaction(Double amount) {
        this();
        this.amount = amount;
    }

    public Date getDate() {
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

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                '}';
    }
}
