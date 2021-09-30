package com.crowd.interactive.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crowd.interactive.challenge.entity.BillingDetails;

@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {
    Optional<BillingDetails> findByAccountNumber(String accountNumber);

    @Query(value = "select count(*) from billing_details bd where bd.account_number = :accountNumber", nativeQuery = true)
    int checkIfAccountExists(@Param("accountNumber") String accountNumber);
}
