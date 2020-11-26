package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.WithdrawalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalTransactionRepository extends JpaRepository<WithdrawalTransaction, Long> {

    WithdrawalTransaction findByApprovalCode(String approvalCode);
}
