package com.eteration.simplebanking.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class BillPaymentTransaction extends WithdrawalTransaction {

    @NotNull
    String payee;

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}
