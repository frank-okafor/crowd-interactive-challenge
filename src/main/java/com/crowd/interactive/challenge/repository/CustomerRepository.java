package com.crowd.interactive.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crowd.interactive.challenge.entity.BillingDetails;
import com.crowd.interactive.challenge.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByBillingDetails(BillingDetails billingDetails);

    @Query(value = "select count(*) from customer c where c.email_address = :email", nativeQuery = true)
    int checkIfEmailExists(@Param("email") String email);

}
