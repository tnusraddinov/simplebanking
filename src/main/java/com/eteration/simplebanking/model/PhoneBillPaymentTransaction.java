package com.eteration.simplebanking.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "phone_bill_payment_transaction")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class PhoneBillPaymentTransaction extends BillPaymentTransaction{

    @NotNull
    String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void apply(Account account) throws InsufficientBalanceException {
        account.withdraw(this.getAmount());
        this.setAccount(account);
        account.getTransactions().add(this);
    }
}
