package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.PhoneBillPaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBillPaymentTransactionRepository extends JpaRepository<PhoneBillPaymentTransaction, Long> {

}
