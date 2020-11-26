package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Long> {

    DepositTransaction findByApprovalCode(String approvalCode);
}
